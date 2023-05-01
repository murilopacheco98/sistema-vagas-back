package com.growdev.GrowdevPeople.services.address;

import com.growdev.GrowdevPeople.dto.auxiliares.CreateTalentBankDTO;
import com.growdev.GrowdevPeople.entities.address.City;
import com.growdev.GrowdevPeople.entities.address.State;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.repositories.address.CityRepository;
import com.growdev.GrowdevPeople.repositories.address.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityService {
    @Autowired
    CityRepository cityRepository;
    @Autowired
    StateRepository stateRepository;

    public City create(CreateTalentBankDTO createTalentBankDTO, State state) {
        City city = new City();
        city.setName(createTalentBankDTO.getCityName());

//        State state = stateRepository.findById(state_uid)
//                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar este estado."));
        city.setState(state);
//        city.setCode_ibge();
        try {
            city = cityRepository.save(city);
            return city;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar está cidade.");
        }
    }
}
