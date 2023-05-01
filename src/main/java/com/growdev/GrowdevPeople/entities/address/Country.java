package com.growdev.GrowdevPeople.entities.address;

import com.growdev.GrowdevPeople.entities.EntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_country")
public class Country extends EntityBase {
    @NotNull
    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    @NotNull
    @Column(columnDefinition = "VARCHAR(10)")
    private String initials;

    private Integer m49;

//    @OneToMany(mappedBy = "country")
//    private List<State> stateList = new ArrayList<>();
}
