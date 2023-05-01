package com.growdev.GrowdevPeople.resources;

import com.growdev.GrowdevPeople.dto.CompanyDTO;
import com.growdev.GrowdevPeople.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("/get/all")
    public ResponseEntity<List<CompanyDTO>> getAll() {
        List<CompanyDTO> companyDTOList = companyService.getAll();
        return new ResponseEntity<>(companyDTOList, HttpStatus.OK);
    }

    @GetMapping("/get/paged")
    public ResponseEntity<Page<CompanyDTO>> getAllPageable(Pageable pageable) {
        Page<CompanyDTO> companyDTOPage = companyService.getAllPageable(pageable);
        return new ResponseEntity<>(companyDTOPage, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<CompanyDTO> create(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO companyDTOCreated = companyService.create(companyDTO);
        return new ResponseEntity<>(companyDTOCreated, HttpStatus.OK);
    }
}
