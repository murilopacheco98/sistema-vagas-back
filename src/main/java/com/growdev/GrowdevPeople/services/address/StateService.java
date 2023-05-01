package com.growdev.GrowdevPeople.services.address;

import com.growdev.GrowdevPeople.dto.auxiliares.CreateTalentBankDTO;
import com.growdev.GrowdevPeople.entities.address.Country;
import com.growdev.GrowdevPeople.entities.address.State;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.repositories.address.CountryRepository;
import com.growdev.GrowdevPeople.repositories.address.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StateService {
    @Autowired
    StateRepository stateRepository;
    @Autowired
    CountryRepository countryRepository;

    public State create(CreateTalentBankDTO createTalentBankDTO, Country country) {
        State state = new State();
        state.setName(createTalentBankDTO.getStateName());

//        state.setInitials(createTalentBankDTO.getStateInitials());
//        state.setCountry(country);
        System.out.println("Passei aqui.");
        try {
            state = stateRepository.save(state);
            return state;
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este estado.");
        }
    }
}
