package com.growdev.GrowdevPeople.dto.user;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CreateUserDTO {
    private String name;
    private String email;
    private String password;
    private String checkerCode;
    private String phoneNumber;
    private String roleName;
    private UUID companyUid;
}