package com.growdev.GrowdevPeople.dto.address;

import com.growdev.GrowdevPeople.entities.address.Country;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CountryDTO {
    private UUID uid;
    private String name;
    private String initials;
    private Integer m49;
    private Instant createdAt;
    private Instant updatedAt;

    public CountryDTO(Country country) {
        this.uid = country.getId();
        this.name = country.getName();
        this.initials = country.getInitials();
        this.m49 = country.getM49();
        this.createdAt = country.getCreatedAt();
        this.updatedAt = country.getUpdatedAt();
    }
}
