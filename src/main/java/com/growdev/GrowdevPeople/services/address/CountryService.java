package com.growdev.GrowdevPeople.services.address;

import com.growdev.GrowdevPeople.repositories.address.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryService {
    @Autowired
    CountryRepository countryRepository;

//    public Country create(CreateTalentBankDTO createTalentBankDTO) {
//        Country country = new Country();
//        country.setName(createTalentBankDTO.getCountryName());
//        country.setInitials(createTalentBankDTO.getCountryInitials());
////        country.setM49(createTalentBankDTO.);
//        try {
//            country = countryRepository.save(country);
//            return country;
//        } catch (Exception e) {
//            throw new BadRequestException("Não é possível salver este país.");
//        }
//    }
}
