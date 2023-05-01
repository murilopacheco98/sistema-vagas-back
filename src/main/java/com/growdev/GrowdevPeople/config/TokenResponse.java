package com.growdev.GrowdevPeople.config;

import com.growdev.GrowdevPeople.dto.user.UserDTO;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenResponse {
    private UUID uid;
    private UserDTO userDTO;
    private String token;
    private String timeToken;
    private String expirationDate;

    public TokenResponse(UserDTO userDTO, String token, String timeToken, String messageExpirationDate) {
        this.userDTO = userDTO;
        this.token = token;
        this.timeToken = timeToken;
        this.expirationDate = messageExpirationDate;
    }
}