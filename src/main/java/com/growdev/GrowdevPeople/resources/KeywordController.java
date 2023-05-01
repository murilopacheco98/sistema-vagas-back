package com.growdev.GrowdevPeople.resources;

import com.growdev.GrowdevPeople.dto.KeywordDTO;
import com.growdev.GrowdevPeople.services.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/keyword")
public class KeywordController {
    @Autowired
    KeywordService keywordService;

    @GetMapping("/get/paged")
    public ResponseEntity<Page<KeywordDTO>> getAllPaged(Pageable pageable){
        Page<KeywordDTO> keywordDTOPage = keywordService.getAllPaged(pageable);
        return ResponseEntity.ok().body(keywordDTOPage);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<KeywordDTO>> getAll(){
        List<KeywordDTO> keywordDTOList = keywordService.getAll();
        return ResponseEntity.ok().body(keywordDTOList);
    }


    @PostMapping("/post")
    public ResponseEntity<KeywordDTO> create(@RequestBody KeywordDTO keywordDTO) {
        KeywordDTO keywordDTOCreated = keywordService.create(keywordDTO);
        return ResponseEntity.ok().body(keywordDTOCreated);
    }
}
