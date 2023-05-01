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
@Table(name = "tb_city")
public class City extends EntityBase {
    @NotNull
    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    private Integer codeIbge;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private State state;

//    @OneToMany(mappedBy = "city")
//    private List<Address> address = new ArrayList<>();
}
