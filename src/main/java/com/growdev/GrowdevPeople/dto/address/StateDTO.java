package com.growdev.GrowdevPeople.dto.address;

import com.growdev.GrowdevPeople.entities.address.State;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StateDTO {
    private UUID uid;
    private String name;
    private String initials;
    private Integer codeIbge;
    private UUID countryUid;
    //    @OneToMany(mappedBy = "state")
//    private List<City> cityList = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;

    public StateDTO(State state) {
        this.uid = state.getId();
        this.name = state.getName();
        this.initials = state.getInitials();
        this.codeIbge = state.getCodeIbge();
        this.countryUid = state.getCountry().getId();
        this.createdAt = state.getCreatedAt();
        this.updatedAt = state.getUpdatedAt();
    }
}
