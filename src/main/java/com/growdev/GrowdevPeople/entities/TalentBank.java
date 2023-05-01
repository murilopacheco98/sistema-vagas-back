package com.growdev.GrowdevPeople.entities;

import com.growdev.GrowdevPeople.entities.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_talent_bank")
public class TalentBank extends EntityBase {
    @NotNull
    @Column(columnDefinition = "VARCHAR(100)")
    private String name;

    @OneToOne
    @JoinColumn(name = "curriculum_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Curriculum curriculum;

    @Column(columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(columnDefinition = "VARCHAR(11)")
    private String phoneNumber;

    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Address address;

    private boolean pcd;

    @NotNull
    @Column(columnDefinition = "VARCHAR(50)")
    private String status;

    @Column(columnDefinition = "VARCHAR(50)")
    private String huntingStatus;

    private boolean isGrowdever;

//    @ManyToMany(mappedBy = "talentBank")
    private Set<Job> job = new HashSet<>();

//    @OneToOne(mappedBy = "talentBank")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
