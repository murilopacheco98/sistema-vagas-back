package com.growdev.GrowdevPeople.dto;

import com.growdev.GrowdevPeople.entities.DataProfile;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataProfileDTO {
//    private String document;
    private UUID uid;
    private String email;
    private String name;
//    private String phoneNumber;


    public DataProfileDTO(DataProfile dataProfile) {
        this.uid = dataProfile.getId();
        this.email = dataProfile.getEmail();
        this.name = dataProfile.getName();
    }
}
