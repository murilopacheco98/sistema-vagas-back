package com.growdev.GrowdevPeople.services;

import com.growdev.GrowdevPeople.dto.CurriculumDTO;
import com.growdev.GrowdevPeople.entities.Curriculum;
import com.growdev.GrowdevPeople.entities.Keyword;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.repositories.CurriculumRepository;
import com.growdev.GrowdevPeople.repositories.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CurriculumService {
    @Autowired
    CurriculumRepository curriculumRepository;
    @Autowired
    KeywordRepository keywordRepository;

    public Page<CurriculumDTO> getAllPaged(Pageable pageable) {
        Page<Curriculum> curriculumPage = curriculumRepository.findAll(pageable);
        return curriculumPage.map(CurriculumDTO::new);
    }
    public CurriculumDTO create(CurriculumDTO curriculumDTO) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSeniority(curriculum.getSeniority());
        for (String tecnology : curriculumDTO.getTecnologyName()) {
            Keyword tecnologyFound = keywordRepository.findByName(tecnology);
            curriculum.getKeyword().add(tecnologyFound);
        }
        curriculum.setLinkedin(curriculum.getLinkedin());
        curriculum.setOtherLinks(curriculum.getOtherLinks());

        try {
            curriculumRepository.save(curriculum);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar este currículo.");
        }

        return new CurriculumDTO(curriculum);
    }
}
