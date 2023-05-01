package com.growdev.GrowdevPeople.entities.address;

import com.growdev.GrowdevPeople.entities.EntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_address")
public class Address extends EntityBase {
    @NotNull
    @Column(columnDefinition = "VARCHAR(150)")
    private String street;

    @NotNull
    private Integer number;

    @NotNull
    @Column(columnDefinition = "VARCHAR(50)")
    private String neighborhood;

    @NotNull
    @Column(columnDefinition = "VARCHAR(50)")
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private City city;
}
