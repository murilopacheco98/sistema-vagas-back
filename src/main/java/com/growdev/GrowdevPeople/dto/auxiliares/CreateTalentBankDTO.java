package com.growdev.GrowdevPeople.dto.auxiliares;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateTalentBankDTO {
    private UUID userUid;
    private String candidateName;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String cpf;
    private String addressStreet;
    private Integer addressNumber;

    private String addressNeighborhood;
    private String addressZipCode;
    private String cityName;
    private String stateName;
    private boolean pcd;
    private boolean growdever;
    private String status;
    private String stateInitials;
    private String countryName;
    private String countryInitials;

    private String seniority;
    private List<String> keywordsName = new ArrayList<>();
    private String linkedin;
    private String github;
    private String otherLinks;
}
