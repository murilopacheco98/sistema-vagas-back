package com.growdev.GrowdevPeople.dto.auxiliares;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class JobTalent {
    private UUID jobUid;
    private UUID talentUid;
}
