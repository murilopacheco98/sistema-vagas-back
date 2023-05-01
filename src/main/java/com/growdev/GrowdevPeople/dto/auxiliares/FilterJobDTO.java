package com.growdev.GrowdevPeople.dto.auxiliares;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FilterJobDTO {
    private String title;
//    private String companyName;
    private String workFormat;
    private String stateName;
    private double minSalary;
    private String seniority;
//    private List<String> keywordName;
}
