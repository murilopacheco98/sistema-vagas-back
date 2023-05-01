package com.growdev.GrowdevPeople.dto.auxiliares;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTalentAndAddToAJobDTO extends CreateTalentBankDTO {
    private UUID job_uid;
}
