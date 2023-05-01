package com.growdev.GrowdevPeople.services;

import com.growdev.GrowdevPeople.dto.CompanyDTO;
import com.growdev.GrowdevPeople.entities.Company;
import com.growdev.GrowdevPeople.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<CompanyDTO> getAll() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(CompanyDTO :: new).collect(Collectors.toList());
    }
    public Page<CompanyDTO> getAllPageable(Pageable pageable) {
        Page<Company> companyPage = companyRepository.findAll(pageable);
        return companyPage.map(CompanyDTO :: new);
    }

    public CompanyDTO create(CompanyDTO companyDTO) {
        Company company = new Company();
        System.out.println(companyDTO.getName());
        company.setName(companyDTO.getName());
        company = companyRepository.save(company);
        return new CompanyDTO(company);
    }
}
