package com.growdev.GrowdevPeople.dto.user;

import com.growdev.GrowdevPeople.dto.CompanyDTO;
import com.growdev.GrowdevPeople.entities.User;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private UUID userUid;
    private UUID dataProfileUid;
//    private DataProfileD
    private String email;
    private Boolean enable;
    private String name;
    private String document;
    private String phoneNumber;
    private CompanyDTO companyDTO;
    private String roleName;

    public UserDTO(User user) {
        this.userUid = user.getId();
        this.enable = user.getEnable();
        this.email = user.getLogin();
        this.dataProfileUid = user.getDataProfile().getId();
        this.name = user.getDataProfile().getName();
        this.document = user.getDataProfile().getDocument();
        this.phoneNumber = user.getDataProfile().getPhoneNumber();
        if (user.getDataProfile().getCompany() != null) {
            this.companyDTO = new CompanyDTO(user.getDataProfile().getCompany());
        }
        this.roleName = user.getRole().getName();
    }
}
