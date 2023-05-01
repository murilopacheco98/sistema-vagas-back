package com.growdev.GrowdevPeople.dto;

import com.growdev.GrowdevPeople.entities.Company;
import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CompanyDTO {
    private UUID uid;

    private String name;

    public CompanyDTO(Company company){
        this.uid = company.getId();
        this.name = company.getName();
    }
}
