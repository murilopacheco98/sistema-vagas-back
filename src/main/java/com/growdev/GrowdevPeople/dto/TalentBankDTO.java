package com.growdev.GrowdevPeople.dto;

import com.growdev.GrowdevPeople.dto.address.AddressDTO;
import com.growdev.GrowdevPeople.entities.TalentBank;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TalentBankDTO {
    private UUID uid;
    private String name;

    private CurriculumDTO curriculumDTO;
    private String email;
    private String phoneNumber;
    private boolean pcd;
    private LocalDate birthDate;
    private AddressDTO addressDTO;
    private String status;
    private String huntingStatus;
    private boolean isGrowdever;
    private Instant createdAt;
    private Instant updatedAt;

    public TalentBankDTO(TalentBank talentBank) {
        this.uid = talentBank.getId();
        this.name = talentBank.getName();
        this.curriculumDTO = new CurriculumDTO(talentBank.getCurriculum());
        this.email = talentBank.getEmail();
        this.phoneNumber = talentBank.getPhoneNumber();
        this.pcd = talentBank.isPcd();
        this.birthDate = talentBank.getBirthDate();
        this.addressDTO = new AddressDTO(talentBank.getAddress());
        this.status = talentBank.getStatus();
        this.huntingStatus = talentBank.getHuntingStatus();
        this.isGrowdever = talentBank.isGrowdever();
        this.createdAt = talentBank.getCreatedAt();
        this.updatedAt = talentBank.getUpdatedAt();
    }
}
