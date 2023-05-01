package com.growdev.GrowdevPeople.dto;

import com.growdev.GrowdevPeople.entities.Role;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RoleDTO implements Serializable {
    private UUID uid;
    private String name;
    private String description;
    private Integer type;
    private Boolean enable;
    private Instant createdAt;
    private Instant updatedAt;

    public RoleDTO(Role role){
        this.uid = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
//        this.type = role.getType();
        this.enable = role.getEnable();
        this.createdAt = role.getCreatedAt();
        this.updatedAt = role.getUpdatedAt();
    }
}
