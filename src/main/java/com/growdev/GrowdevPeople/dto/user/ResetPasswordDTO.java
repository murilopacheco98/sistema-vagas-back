package com.growdev.GrowdevPeople.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResetPasswordDTO {
    private String resetPasswordToken;
    private String newPassword;
}
