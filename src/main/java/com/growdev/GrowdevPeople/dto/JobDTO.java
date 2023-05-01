package com.growdev.GrowdevPeople.dto;

import com.growdev.GrowdevPeople.entities.Job;
import com.growdev.GrowdevPeople.entities.TalentBank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JobDTO {
    private UUID uid;
    @NotNull
    private String title;
    private String description;
    private String shortDescription;
    @NotNull
    private String mainRequirements;
    @NotNull
    private String differentials;
    @NotNull
    private String seniority;
    private List<String> keywordsName = new ArrayList<>();
    private double budget;
    @NotNull
    private String workFormat;
    @NotNull
    private String expectedStartDate;
    @NotNull
    private String status;
    @NotNull
    private CompanyDTO companyDTO;
    private String cityName;
    private String stateName;
    private Integer numberParticipants;
    private DataProfileDTO dataProfileDTO;
    private Instant createdAt;
    private Instant updatedAt;

    private List<TalentBankDTO> talentBankDTOList = new ArrayList<>();
    public JobDTO(Job job) {
        this.uid = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.mainRequirements = job.getMainRequirements();
        this.differentials = job.getDifferentials();
        this.seniority = job.getSeniority();
        job.getKeyword().forEach(tecnology -> this.keywordsName.add(tecnology.getName()));
        this.companyDTO = new CompanyDTO(job.getCompany());
        this.cityName = job.getCityName();
        this.budget = job.getBudget();
        this.workFormat = job.getWorkFormat();
        this.expectedStartDate = String.valueOf(job.getExpectedStartDate());
        this.status = job.getStatus();
        this.createdAt = job.getCreatedAt();
        this.updatedAt = job.getUpdatedAt();
        this.dataProfileDTO = new DataProfileDTO(job.getDataProfile());
        this.stateName = job.getStateName();
        this.numberParticipants = job.getTalentBank().size();
        this.shortDescription = job.getShortDescription();
    }

    public JobDTO(Job job, Set<TalentBank> talentBanks) {
        this(job);
        talentBanks.forEach(talentBank -> this.talentBankDTOList.add(new TalentBankDTO(talentBank)));
    }
}
