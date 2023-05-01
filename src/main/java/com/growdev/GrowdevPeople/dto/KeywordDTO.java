package com.growdev.GrowdevPeople.dto;

import com.growdev.GrowdevPeople.entities.Keyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeywordDTO {
    private UUID uid;
    private String name;

    public KeywordDTO(Keyword keyword) {
        this.uid = keyword.getId();
        this.name = keyword.getName();
    }
}
