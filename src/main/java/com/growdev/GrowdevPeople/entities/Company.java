package com.growdev.GrowdevPeople.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_company")
public class Company extends EntityBase {
    @NotBlank
    private String name;

//    @org.hibernate.annotations.ForeignKey(name = "none")
//    @OneToMany(mappedBy = "company")
//    private List<DataProfile> dataProfileList = new ArrayList<>();

//    @org.hibernate.annotations.ForeignKey(name = "none")
//    @OneToMany(mappedBy = "company")
//    private List<Job> jobs = new ArrayList<>();
}
