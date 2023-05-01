package com.growdev.GrowdevPeople.resources;

import com.growdev.GrowdevPeople.services.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/curriculum")
public class CurriculumController {
    @Autowired
    CurriculumService curriculumService;

}
