package com.growdev.GrowdevPeople.dto.address;

import com.growdev.GrowdevPeople.entities.address.City;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CityDTO {
    private UUID uid;
    private String name;
    private Integer codeIbge;
    private UUID stateUid;
    //    @OneToMany(mappedBy = "city")
    //    private List<Address> address = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;

    public CityDTO(City city) {
        this.uid = city.getId();
        this.name = city.getName();
        this.codeIbge = city.getCodeIbge();
        this.stateUid = city.getState().getId();
        this.createdAt = city.getCreatedAt();
        this.updatedAt = city.getUpdatedAt();
    }
}
