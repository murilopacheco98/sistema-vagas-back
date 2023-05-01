package com.growdev.GrowdevPeople.dto.address;

import com.growdev.GrowdevPeople.entities.address.Address;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AddressDTO {
    private UUID uid;
    private String street;
    private Integer number;
    private String neighborhood;
    private String zipCode;
    private String cityName;
    private String stateName;
    private Instant createdAt;
    private Instant updatedAt;

    public AddressDTO(Address address) {
        this.uid = address.getId();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.neighborhood = address.getNeighborhood();
        this.zipCode = address.getZipCode();
        this.cityName = address.getCity().getName();
        this.stateName = address.getCity().getState().getName();
        this.createdAt = address.getCreatedAt();
        this.updatedAt = address.getUpdatedAt();
    }
}
