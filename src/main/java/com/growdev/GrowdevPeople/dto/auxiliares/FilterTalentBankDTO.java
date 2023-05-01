package com.growdev.GrowdevPeople.dto.auxiliares;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FilterTalentBankDTO {
//    private String name;
//    private String email;
//    private String cpf;
    private String cityName;
    private String seniority;
    private List<String> keywordName;
}
