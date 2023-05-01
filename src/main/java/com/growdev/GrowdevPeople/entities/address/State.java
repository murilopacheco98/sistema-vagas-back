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
@Table(name = "tb_state")
public class State extends EntityBase {
    @NotNull
    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    @NotNull
    @Column(columnDefinition = "VARCHAR(10)")
    private String initials;

    private Integer codeIbge;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Country country;

//    @OneToMany(mappedBy = "state")
//    private List<City> cityList = new ArrayList<>();
}
