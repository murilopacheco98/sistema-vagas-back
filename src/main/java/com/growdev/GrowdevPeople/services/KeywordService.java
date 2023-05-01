package com.growdev.GrowdevPeople.services;

import com.growdev.GrowdevPeople.dto.KeywordDTO;
import com.growdev.GrowdevPeople.entities.Keyword;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.repositories.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeywordService {
    @Autowired
    KeywordRepository keywordRepository;

    public List<KeywordDTO> getAll() {
        List<Keyword> keywordList = keywordRepository.findAll();
        return keywordList.stream().map(KeywordDTO::new).collect(Collectors.toList());
    }
    public Page<KeywordDTO> getAllPaged(Pageable pageable) {
        Page<Keyword> tecnologyPage = keywordRepository.findAll(pageable);
        return tecnologyPage.map(KeywordDTO::new);
    }

    public KeywordDTO create(KeywordDTO keywordDTO) {
        Keyword keyword = new Keyword();
        keyword.setName(keywordDTO.getName());

        try {
            keyword = keywordRepository.save(keyword);
            return new KeywordDTO(keyword);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível criar está tecnologia");
        }
    }
}
