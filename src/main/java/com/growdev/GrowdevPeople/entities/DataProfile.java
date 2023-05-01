package com.growdev.GrowdevPeople.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_data_profile")
public class DataProfile extends EntityBase {
    @NotBlank
    private String name;

    @CPF
    private String document;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Company company;
}
