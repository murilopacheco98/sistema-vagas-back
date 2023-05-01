package com.growdev.GrowdevPeople.services;

import com.growdev.GrowdevPeople.dto.JobDTO;
import com.growdev.GrowdevPeople.dto.auxiliares.FilterJobDTO;
import com.growdev.GrowdevPeople.entities.*;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.exceptions.exception.ResourceNotFoundException;
import com.growdev.GrowdevPeople.repositories.CompanyRepository;
import com.growdev.GrowdevPeople.repositories.KeywordRepository;
import com.growdev.GrowdevPeople.repositories.user.DataProfileRepository;
import com.growdev.GrowdevPeople.repositories.JobsRepository;
import com.growdev.GrowdevPeople.repositories.TalentBankRepository;
import com.growdev.GrowdevPeople.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
    private final JobsRepository jobsRepository;
    private final DataProfileRepository dataProfileRepository;
    private final TalentBankRepository talentBankRepository;
    private final KeywordRepository keywordRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public Page<JobDTO> getAllPageable(Pageable pageable) {
        Page<Job> jobsPage = jobsRepository.findAll(pageable);
        return jobsPage.map(JobDTO::new);
    }

    public Page<JobDTO> getAllByResponsibleEmail(String email, Pageable pageable) {
        Page<Job> jobsPage = jobsRepository.findByEmail(email, pageable);
        return jobsPage.map(JobDTO::new);
    }

    public Page<JobDTO> findBySearchTitle(String search, Pageable pageable) {
        Page<Job> jobsPage = jobsRepository.findBySearchTitle(search.toLowerCase(), pageable);
        return jobsPage.map(JobDTO::new);
    }

    public Page<JobDTO> findByTalenkBank(UUID uid, Pageable pageable) {
        List<Job> jobsPage = jobsRepository.findAll();
        List<Job> jobsFound = new ArrayList<>();

        for (Job job : jobsPage) {
            for (TalentBank talentBank : job.getTalentBank()) {
                if (Objects.equals(talentBank.getId(), uid)) {
                    jobsFound.add(job);
                }
            }
        }
        Page<Job> jobsPaged = new PageImpl<>(jobsFound, pageable, jobsFound.size());
        return jobsPaged.map(JobDTO::new);
    }

    public Page<JobDTO> filterJobs(FilterJobDTO filterJobDTO, Pageable pageable) {
        List<Job> jobsPage = jobsRepository.filterJob(filterJobDTO.getTitle().toLowerCase(),
                filterJobDTO.getWorkFormat().toLowerCase(), filterJobDTO.getSeniority().toLowerCase(),
                filterJobDTO.getStateName().toLowerCase());
        List<Job> jobList = new ArrayList<>();
        for (Job job : jobsPage) {
            if (job.getBudget() >= filterJobDTO.getMinSalary() | job.getBudget() == 0) {
                jobList.add(job);
            }
        }

        jobList = jobList.stream().distinct().collect(Collectors.toList());
        int start = Math.min((pageable.getPageSize() * pageable.getPageNumber()), jobList.size());
        int end = Math.min(pageable.getPageSize() * (pageable.getPageNumber() + 1), jobList.size());
        Page<Job> jobFiltered = new PageImpl<>(jobList.subList(start, end), pageable, jobList.size());

        return jobFiltered.map(JobDTO::new);
    }

    public JobDTO create(JobDTO jobDTO) {
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setShortDescription(jobDTO.getShortDescription());
        job.setShortDescription(jobDTO.getShortDescription());
        job.setMainRequirements(jobDTO.getMainRequirements());
        job.setDifferentials(jobDTO.getDifferentials());
        job.setSeniority(jobDTO.getSeniority());
        job.setCityName(jobDTO.getCityName());
        job.setStateName(jobDTO.getStateName());

        for (String keywordName : jobDTO.getKeywordsName()) {
            Keyword keyword = keywordRepository.findByName(keywordName);
            if (keyword == null) {
                throw new ResourceNotFoundException("Está tecnologia não está cadastrada: " + keywordName);
            }
            job.getKeyword().add(keyword);
        }
        Company company = companyRepository.findByName(jobDTO.getCompanyDTO().getName());
        if (company == null) {
            throw new ResourceNotFoundException("Está empresa não está cadastrada: " + company);
        }

        job.setCompany(company);
        job.setBudget(jobDTO.getBudget());
        job.setWorkFormat(jobDTO.getWorkFormat());
        String[] startDate = jobDTO.getExpectedStartDate().split("-");
        job.setExpectedStartDate(LocalDate.of(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[2])));
        job.setStatus(jobDTO.getStatus());

        User user = userRepository.findByLogin(jobDTO.getDataProfileDTO().getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Este usuário não foi encontrado.");
        }

        DataProfile dataProfile = dataProfileRepository.findByEmail(jobDTO.getDataProfileDTO().getEmail());
        if (dataProfile == null) {
            throw new ResourceNotFoundException("Este perfil não foi encontrado.");
        }
        job.setDataProfile(dataProfile);

        try {
            job = jobsRepository.save(job);
            System.out.println("Passei aqui 3");
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar esta vaga.");
        }
        try {
            return new JobDTO(job);
        } catch (Exception e) {
            throw new BadRequestException("não sei que porra aconteceu");
        }
    }

    public JobDTO addTalent(UUID job_uid, UUID talent_uid) {
        Job job = jobsRepository.findById(job_uid)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar este job."));
        TalentBank talentBank = talentBankRepository.findById(talent_uid)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar este talento."));
        for (TalentBank talentBankJob : job.getTalentBank()) {
            if (talentBank.getId() == talent_uid) {
                throw new DataIntegrityViolationException("Você já se canditatou nesta vaga.");
            }
        }
        job.getTalentBank().add(talentBank);
//        talentBank.getJobs().add(job);

        try {
            job = jobsRepository.save(job);
//            talentBank = talentBankRepository.save(talentBank);
            return new JobDTO(job, job.getTalentBank());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Não foi possível cadastrar o candidato na vaga");
        }
    }

    public Page<JobDTO> filterJobsJava(FilterJobDTO filterJobDTO, Pageable pageable) {
        List<Job> jobList = jobsRepository.findAll();
        if (!Objects.equals(filterJobDTO.getTitle(), "")) {
            jobList.removeIf(job -> !job.getTitle().matches("(.*)" + filterJobDTO.getTitle() + "(.*)"));
        }
        if (!Objects.equals(filterJobDTO.getWorkFormat(), "")) {
            jobList.removeIf(job -> !job.getCompany().getName().matches("(.*)" + filterJobDTO.getWorkFormat() + "(.*)"));
        }
        if (!Objects.equals(filterJobDTO.getStateName(), "")) {
            jobList.removeIf(job -> !job.getStateName().matches("(.*)" + filterJobDTO.getStateName() + "(.*)"));
        }
        if (!Objects.equals(filterJobDTO.getSeniority(), "")) {
            jobList.removeIf(job -> !job.getSeniority().matches("(.*)" + filterJobDTO.getSeniority() + "(.*)"));
        }

        Page<Job> jobPaged = new PageImpl<Job>(jobList, pageable, jobList.size());
        return jobPaged.map(JobDTO::new);
    }

    public JobDTO updateJob(JobDTO jobDTO) {
        Job job = jobsRepository.findById(jobDTO.getUid())
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setShortDescription(jobDTO.getShortDescription());
        job.setShortDescription(jobDTO.getShortDescription());
        job.setMainRequirements(jobDTO.getMainRequirements());
        job.setDifferentials(jobDTO.getDifferentials());
        job.setSeniority(jobDTO.getSeniority());
        job.setCityName(jobDTO.getCityName());
        job.setStateName(jobDTO.getStateName());

        for (String keywordName : jobDTO.getKeywordsName()) {
            Keyword keyword = keywordRepository.findByName(keywordName);
            if (keyword == null) {
                throw new ResourceNotFoundException("Está tecnologia não está cadastrada: " + keywordName);
            }
            job.getKeyword().add(keyword);
        }
        Company company = companyRepository.findByName(jobDTO.getCompanyDTO().getName());
        if (company == null) {
            throw new ResourceNotFoundException("Está empresa não está cadastrada: " + company);
        }

        job.setCompany(company);
        job.setBudget(jobDTO.getBudget());
        job.setWorkFormat(jobDTO.getWorkFormat());
        String[] startDate = jobDTO.getExpectedStartDate().split("-");
        job.setExpectedStartDate(LocalDate.of(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[2])));
        job.setStatus(jobDTO.getStatus());

        User user = userRepository.findByLogin(jobDTO.getDataProfileDTO().getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Este usuário não foi encontrado.");
        }

        DataProfile dataProfile = dataProfileRepository.findByEmail(jobDTO.getDataProfileDTO().getEmail());
        if (dataProfile == null) {
            throw new ResourceNotFoundException("Este perfil não foi encontrado.");
        }
        job.setDataProfile(dataProfile);

        try {
            job = jobsRepository.save(job);
            System.out.println("Passei aqui 3");
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar esta vaga.");
        }
        try {
            return new JobDTO(job);
        } catch (Exception e) {
            throw new BadRequestException("não sei que porra aconteceu");
        }
    }
}
