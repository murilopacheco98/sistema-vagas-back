package com.growdev.GrowdevPeople.services.address;

import com.growdev.GrowdevPeople.dto.auxiliares.CreateTalentBankDTO;
import com.growdev.GrowdevPeople.entities.address.Address;
import com.growdev.GrowdevPeople.entities.address.City;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.repositories.address.AddressRepository;
import com.growdev.GrowdevPeople.repositories.address.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CityRepository cityRepository;

    public Address create(CreateTalentBankDTO createTalentBankDTO, City city) {
        Address address = new Address();
        address.setStreet(createTalentBankDTO.getAddressStreet());
        address.setNumber(createTalentBankDTO.getAddressNumber());
        address.setNeighborhood(createTalentBankDTO.getAddressNeighborhood());
        address.setZipCode(createTalentBankDTO.getAddressZipCode());

//        City city = cityRepository.findById(city_uid)
//                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar está cidade."));

        address.setCity(city);
        try {
            address = addressRepository.save(address);
            return address;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este endereço");
        }
    }
}
