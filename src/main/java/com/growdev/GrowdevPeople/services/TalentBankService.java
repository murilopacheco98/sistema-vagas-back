package com.growdev.GrowdevPeople.services;

import com.growdev.GrowdevPeople.dto.CurriculumDTO;
import com.growdev.GrowdevPeople.dto.address.AddressDTO;
import com.growdev.GrowdevPeople.dto.auxiliares.CreateTalentAndAddToAJobDTO;
import com.growdev.GrowdevPeople.dto.auxiliares.CreateTalentBankDTO;
import com.growdev.GrowdevPeople.dto.TalentBankDTO;
import com.growdev.GrowdevPeople.entities.*;
import com.growdev.GrowdevPeople.entities.address.Address;
import com.growdev.GrowdevPeople.entities.address.City;
import com.growdev.GrowdevPeople.entities.address.Country;
import com.growdev.GrowdevPeople.entities.address.State;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.exceptions.exception.ResourceNotFoundException;
import com.growdev.GrowdevPeople.repositories.CurriculumRepository;
import com.growdev.GrowdevPeople.repositories.JobsRepository;
import com.growdev.GrowdevPeople.repositories.KeywordRepository;
import com.growdev.GrowdevPeople.repositories.TalentBankRepository;
import com.growdev.GrowdevPeople.repositories.address.AddressRepository;
import com.growdev.GrowdevPeople.repositories.address.CityRepository;
import com.growdev.GrowdevPeople.repositories.address.CountryRepository;
import com.growdev.GrowdevPeople.repositories.address.StateRepository;
import com.growdev.GrowdevPeople.repositories.user.UserRepository;
import com.growdev.GrowdevPeople.util.RemoveAccentuateCharacters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TalentBankService {
    private final TalentBankRepository talentBankRepository;
    private final JobsRepository jobsRepository;
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final CurriculumRepository curriculumRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final KeywordRepository keywordRepository;
    private final RemoveAccentuateCharacters removeAccentuateCharacters;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<TalentBankDTO> getAllPaged(Pageable pageable) {
        Page<TalentBank> talentBankPage = talentBankRepository.findAll(pageable);
        return talentBankPage.map(TalentBankDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<TalentBankDTO> getAllByJobUid(UUID uid, Pageable pageable) {
        Job job = jobsRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));
        List<TalentBank> talentBankList = new ArrayList<>(job.getTalentBank());

        Page<TalentBank> talentBankPage = new PageImpl<>(talentBankList, pageable, talentBankList.size());
        return talentBankPage.map(TalentBankDTO::new);
    }

    @Transactional(readOnly = true)
    public TalentBankDTO findByEmail(String email) {
        TalentBank talentBank = talentBankRepository.findByEmail(email);
        if (talentBank == null) throw new ResourceNotFoundException("Perfil não encontrado.");

        return new TalentBankDTO(talentBank);
    }

    @Transactional(readOnly = true)
    public Page<TalentBankDTO> findBySearchName(String search, Pageable pageable) {
        Page<TalentBank> talentBankPage = talentBankRepository.findBySearchName(search, pageable);
        return talentBankPage.map(TalentBankDTO::new);
    }

//    @Transactional(readOnly = true)
//    public Page<TalentBankDTO> getFilter(FilterTalentBankDTO filterTalentBankDTO, Pageable pageable) {
//        Page<TalentBank> talentBankPage = talentBankRepository.filterTalentBank(filterTalentBankDTO.getCityName(),
//                filterTalentBankDTO.getSeniority(), pageable);
//        List<TalentBank> talentBankList = new ArrayList<>();
//        for (TalentBank talentBank : talentBankPage) {
//            for (Keyword keyword : talentBank.getCurriculum().getKeywords()) {
//                if (filterTalentBankDTO.getKeywordName().size() > 0) {
//                    if (filterTalentBankDTO.getKeywordName().contains(keyword.getName())) {
//                        talentBankList.add(talentBank);
//                    }
//                } else {
//                    talentBankList.add(talentBank);
//                }
//            }
//        }
//        talentBankList = talentBankList.stream().distinct().collect(Collectors.toList());
//        Page<TalentBank> talentBankFiltered = new PageImpl<>(talentBankList, pageable, talentBankList.size());
//        return talentBankFiltered.map(TalentBankDTO::new);
//    }

    public TalentBankDTO addJob(UUID job_uid, UUID talent_uid) {
        TalentBank talentBank = talentBankRepository.findById(talent_uid)
                .orElseThrow(() -> new ResourceNotFoundException("Este talento não existe."));
        Job job = jobsRepository.findById(job_uid)
                .orElseThrow(() -> new ResourceNotFoundException("Este talento não existe."));
        talentBank.getJob().add(job);

        try {
            talentBank = talentBankRepository.save(talentBank);
            return new TalentBankDTO(talentBank);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível cadastrar este job.");
        }
    }

    public TalentBankDTO create(CreateTalentBankDTO createTalentBankDTO) throws IOException {
        Country country = new Country();
        country.setName(createTalentBankDTO.getCountryName());
        country.setInitials(createTalentBankDTO.getCountryInitials());
        try {
            country = countryRepository.save(country);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço.");
        }

        State state = new State();
        state.setName(createTalentBankDTO.getStateName());
        state.setInitials(createTalentBankDTO.getStateInitials());
        state.setCountry(country);
        try {
            state = stateRepository.save(state);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }

        City city = new City();
        city.setName(createTalentBankDTO.getCityName().toLowerCase());
        city.setState(state);
        try {
            city = cityRepository.save(city);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }

        Address address = new Address();
        address.setStreet(createTalentBankDTO.getAddressStreet());
        address.setNumber(createTalentBankDTO.getAddressNumber());
        address.setNeighborhood(createTalentBankDTO.getAddressNeighborhood());
        address.setZipCode(createTalentBankDTO.getAddressZipCode());
        address.setCity(city);
        try {
            address = addressRepository.save(address);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }

        Curriculum curriculum = new Curriculum();
        curriculum.setSeniority(createTalentBankDTO.getSeniority().toLowerCase());
        for (String tecnology : createTalentBankDTO.getKeywordsName()) {
            Keyword tecnologyFound = keywordRepository.findByName(tecnology);
            curriculum.getKeyword().add(tecnologyFound);
        }
        curriculum.setLinkedin(createTalentBankDTO.getLinkedin());
        curriculum.setGithub(createTalentBankDTO.getGithub());
        curriculum.setOtherLinks(createTalentBankDTO.getOtherLinks());
        try {
            curriculum = curriculumRepository.save(curriculum);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível criar este currículo.");
        }

        TalentBank talentBank = new TalentBank();
        talentBank.setName(createTalentBankDTO.getCandidateName());
        talentBank.setEmail(createTalentBankDTO.getEmail());
        talentBank.setCurriculum(curriculum);
        if (createTalentBankDTO.getPhoneNumber() != null) {
            String phoneNumber = createTalentBankDTO.getPhoneNumber().replaceAll("\\D", "");
            talentBank.setPhoneNumber(phoneNumber);
        }
        talentBank.setPcd(createTalentBankDTO.isPcd());
        talentBank.setBirthDate(createTalentBankDTO.getBirthDate());
        talentBank.setAddress(address);
        talentBank.setStatus("Disponível");
        talentBank.setGrowdever(createTalentBankDTO.isGrowdever());

        User user = userRepository.findById(createTalentBankDTO.getUserUid())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        talentBank.setUser(user);
        try {
            talentBank = talentBankRepository.save(talentBank);
            return new TalentBankDTO(talentBank);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este talento.");
        }
    }

    public TalentBankDTO createAndAddToAJob(CreateTalentAndAddToAJobDTO createTalentAndAddToAJobDTO) throws IOException {
        Country country = new Country();
        country.setName(createTalentAndAddToAJobDTO.getCountryName());
        country.setInitials(createTalentAndAddToAJobDTO.getCountryInitials());
        try {
            country = countryRepository.save(country);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }

        State state = new State();
        state.setName(createTalentAndAddToAJobDTO.getStateName());
        state.setInitials(createTalentAndAddToAJobDTO.getStateInitials());
        state.setCountry(country);
        try {
            state = stateRepository.save(state);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }

        City city = new City();
        city.setName(createTalentAndAddToAJobDTO.getCityName().toLowerCase());
        city.setState(state);
        try {
            city = cityRepository.save(city);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }

        Address address = new Address();
        address.setStreet(createTalentAndAddToAJobDTO.getAddressStreet());
        address.setNumber(createTalentAndAddToAJobDTO.getAddressNumber());
        address.setNeighborhood(createTalentAndAddToAJobDTO.getAddressNeighborhood());
        address.setZipCode(createTalentAndAddToAJobDTO.getAddressZipCode());
        address.setCity(city);
        try {
            address = addressRepository.save(address);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }

        Curriculum curriculum = new Curriculum();
        curriculum.setSeniority(createTalentAndAddToAJobDTO.getSeniority().toLowerCase());
        for (String tecnology : createTalentAndAddToAJobDTO.getKeywordsName()) {
            Keyword tecnologyFound = keywordRepository.findByName(tecnology);
            curriculum.getKeyword().add(tecnologyFound);
        }
        curriculum.setLinkedin(createTalentAndAddToAJobDTO.getLinkedin());
        curriculum.setGithub(createTalentAndAddToAJobDTO.getGithub());
        curriculum.setOtherLinks(createTalentAndAddToAJobDTO.getOtherLinks());
        try {
            curriculum = curriculumRepository.save(curriculum);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível criar este currículo.");
        }

        TalentBank talentBank = new TalentBank();
        talentBank.setName(createTalentAndAddToAJobDTO.getCandidateName());
        talentBank.setEmail(createTalentAndAddToAJobDTO.getEmail());
        talentBank.setCurriculum(curriculum);
        talentBank.setPhoneNumber(createTalentAndAddToAJobDTO.getPhoneNumber());
//        talentBank.setPcd(false);
        talentBank.setPcd(createTalentAndAddToAJobDTO.isPcd());
        talentBank.setBirthDate(createTalentAndAddToAJobDTO.getBirthDate());
        talentBank.setAddress(address);
        talentBank.setStatus(createTalentAndAddToAJobDTO.getStatus());
//        talentBank.setGrowdever();

        Job job = jobsRepository.findById(createTalentAndAddToAJobDTO.getJob_uid())
                .orElseThrow(() -> new ResourceNotFoundException("Job não encontrado."));
        talentBank.getJob().add(job);

        try {
            talentBank = talentBankRepository.save(talentBank);
            job.getTalentBank().add(talentBank);
            jobsRepository.save(job);
            TalentBankDTO talentBankDTO = new TalentBankDTO(talentBank);
            talentBankDTO.setAddressDTO(new AddressDTO(talentBank.getAddress()));
            talentBankDTO.setCurriculumDTO(new CurriculumDTO(talentBank.getCurriculum()));
            return talentBankDTO;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este talento.");
        }
    }
}
