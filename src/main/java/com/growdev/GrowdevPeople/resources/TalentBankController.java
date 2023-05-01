package com.growdev.GrowdevPeople.resources;

import com.growdev.GrowdevPeople.dto.auxiliares.CreateTalentAndAddToAJobDTO;
import com.growdev.GrowdevPeople.dto.auxiliares.CreateTalentBankDTO;
import com.growdev.GrowdevPeople.dto.TalentBankDTO;
import com.growdev.GrowdevPeople.services.TalentBankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/talent-bank")
public class TalentBankController {
    @Autowired
    TalentBankService talentBankService;

    @GetMapping("/get")
    public ResponseEntity<Page<TalentBankDTO>> getPageable(Pageable pageable) {
        Page<TalentBankDTO> talentBankDTOPage = talentBankService.getAllPaged(pageable);
        return new ResponseEntity<>(talentBankDTOPage, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<TalentBankDTO> findByEmail(@RequestParam String email) {
        TalentBankDTO talentBankDTO = talentBankService.findByEmail(email);
        return new ResponseEntity<>(talentBankDTO, HttpStatus.OK);
    }

    @GetMapping("/get/search")
    public ResponseEntity<Page<TalentBankDTO>> findBySearch(@RequestParam String name, Pageable pageable) {
        Page<TalentBankDTO> talentBankDTO = talentBankService.findBySearchName(name, pageable);
        return new ResponseEntity<>(talentBankDTO, HttpStatus.OK);
    }

    @GetMapping("find/byjob")
    public ResponseEntity<Page<TalentBankDTO>> findByJobUid(@RequestParam UUID uid, Pageable pageable) {
        Page<TalentBankDTO> talentBankDTO = talentBankService.getAllByJobUid(uid, pageable);
        return new ResponseEntity<>(talentBankDTO, HttpStatus.OK);
    }

//    @GetMapping("/filter")
//    public ResponseEntity<Page<TalentBankDTO>> getFilter(@RequestParam String cityName, @RequestParam String seniority,
//                                                         @RequestParam("keywords") List<String> keywords, Pageable pageable) {
//        FilterTalentBankDTO filterTalentBankDTO = new FilterTalentBankDTO(cityName, seniority, keywords);
//        Page<TalentBankDTO> talentBankDTOPage = talentBankService.getFilter(filterTalentBankDTO, pageable);
//        return new ResponseEntity<>(talentBankDTOPage, HttpStatus.OK);
//    }

    @PostMapping("/post")
    public ResponseEntity<TalentBankDTO> create(@Valid @RequestBody CreateTalentBankDTO createTalentBankDTO) throws IOException {
        TalentBankDTO talentBankDTOCreated = talentBankService.create(createTalentBankDTO);
        return new ResponseEntity<>(talentBankDTOCreated, HttpStatus.CREATED);
    }

    @PostMapping("/add-to-job")
    public ResponseEntity<TalentBankDTO> createTalentAndAddToJob(@RequestBody CreateTalentAndAddToAJobDTO createTalentAndAddToAJobDTO) throws IOException {
        TalentBankDTO talentBankDTOCreated = talentBankService.createAndAddToAJob(createTalentAndAddToAJobDTO);

        return new ResponseEntity<>(talentBankDTOCreated, HttpStatus.CREATED);
    }
}
