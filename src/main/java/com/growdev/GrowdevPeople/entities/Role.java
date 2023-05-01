package com.growdev.GrowdevPeople.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_role")
public class Role extends EntityBase {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Boolean enable;

//    @JsonIgnore
//    @OneToMany(mappedBy = "role")
//    private List<User> userList = new ArrayList<>();
}
