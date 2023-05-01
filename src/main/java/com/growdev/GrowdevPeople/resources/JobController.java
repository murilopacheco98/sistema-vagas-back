package com.growdev.GrowdevPeople.resources;

import com.growdev.GrowdevPeople.dto.JobDTO;
import com.growdev.GrowdevPeople.dto.TalentBankDTO;
import com.growdev.GrowdevPeople.dto.auxiliares.FilterJobDTO;
import com.growdev.GrowdevPeople.dto.auxiliares.JobTalent;
import com.growdev.GrowdevPeople.services.JobService;
import com.growdev.GrowdevPeople.services.TalentBankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    JobService jobService;
    @Autowired
    TalentBankService talentBankService;

    @GetMapping("/get")
    public ResponseEntity<Page<JobDTO>> getAllPageable(Pageable pageable) {
        Page<JobDTO> jobDTOPage = jobService.getAllPageable(pageable);
        return new ResponseEntity<>(jobDTOPage, HttpStatus.OK);
    }

    @GetMapping("/get/search")
    public ResponseEntity<Page<JobDTO>> findBySearch(@RequestParam String title, Pageable pageable) {
        Page<JobDTO> JobDTO = jobService.findBySearchTitle(title, pageable);
        return new ResponseEntity<>(JobDTO, HttpStatus.OK);
    }

//    @GetMapping("/find/responsible")
//    public ResponseEntity<Page<JobDTO>> getAllByResponsibleEmail(@RequestParam String email, Pageable pageable) {
//        Page<JobDTO> jobDTOPage = jobService.getAllByResponsibleEmail(email, pageable);
//        return new ResponseEntity<>(jobDTOPage, HttpStatus.OK);
//    }

    @GetMapping("/find/talent-bank")
    public ResponseEntity<Page<JobDTO>> findByTalentBank(@RequestParam UUID uid, Pageable pageable) {
        Page<JobDTO> jobDTOPage = jobService.findByTalenkBank(uid, pageable);
        return new ResponseEntity<>(jobDTOPage, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<JobDTO>> getFilter(@RequestParam String title, @RequestParam String workFormat,
                                                  @RequestParam String stateName, @RequestParam Double minSalary,
                                                  @RequestParam("seniority") String seniority, Pageable pageable) {
        FilterJobDTO filterJobDTO = new FilterJobDTO(title, workFormat, stateName, minSalary, seniority);
        Page<JobDTO> jobDTOPage = jobService.filterJobs(filterJobDTO, pageable);
        return new ResponseEntity<>(jobDTOPage, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<JobDTO> create(@Valid @RequestBody JobDTO jobDTO) {
        JobDTO jobDTOCreated = jobService.create(jobDTO);
        return new ResponseEntity<>(jobDTOCreated, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<JobDTO> update(@Valid @RequestBody JobDTO jobDTO) {
        JobDTO jobDTOCreated = jobService.updateJob(jobDTO);
        return new ResponseEntity<>(jobDTOCreated, HttpStatus.OK);
    }

    @PostMapping("/add/talent")
    public ResponseEntity<?> addTalentToAJob(@Valid @RequestBody JobTalent jobTalent) {
        TalentBankDTO talentBankDTO = talentBankService.addJob(jobTalent.getJobUid(), jobTalent.getTalentUid());
        JobDTO jobDTO = jobService.addTalent(jobTalent.getJobUid(), jobTalent.getTalentUid());
        return new ResponseEntity<>("Usuário candidatado com sucesso.", HttpStatus.OK);
    }
}
