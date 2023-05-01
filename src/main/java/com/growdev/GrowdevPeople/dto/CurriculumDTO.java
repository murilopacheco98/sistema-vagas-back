package com.growdev.GrowdevPeople.dto;

import com.growdev.GrowdevPeople.entities.Curriculum;
import lombok.*;

import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CurriculumDTO {
    private UUID uid;
    private String seniority;
    private List<String> tecnologyName = new ArrayList<>();
    private String linkedin;
    private String github;
    private String otherLinks;
    private Instant createdAt;
    private Instant updatedAt;

    public CurriculumDTO(Curriculum curriculum) {
        this.uid = curriculum.getId();
        this.seniority = curriculum.getSeniority();
        curriculum.getKeyword().forEach(keyword -> this.tecnologyName.add(keyword.getName()));
        this.linkedin = curriculum.getLinkedin();
        this.github = curriculum.getGithub();
        this.otherLinks = curriculum.getOtherLinks();
        this.createdAt = curriculum.getCreatedAt();
        this.updatedAt = curriculum.getUpdatedAt();
    }
}
