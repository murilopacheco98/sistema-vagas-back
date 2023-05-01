package com.growdev.GrowdevPeople.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_job")
public class Job extends EntityBase {
    @NotNull
    @Column(columnDefinition = "VARCHAR(100)")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String mainRequirements;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String differentials;

    @NotNull
    @Column(columnDefinition = "VARCHAR(30)")
    private String seniority;

    @NotNull
    @Column(name = "city_name")
    private String cityName;

    @NotNull
    @Column(name = "state_name")
    private String stateName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Company company;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "tb_job_keyword",
            joinColumns = {@JoinColumn(name = "job_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "keyword_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))}
    )
    private Set<Keyword> keyword = new HashSet<>();

    @NotNull
    @Column(columnDefinition = "DECIMAL(8,2)")
    private double budget;

    @NotNull
    @Column(columnDefinition = "VARCHAR(30)")
    private String workFormat;

    @NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate expectedStartDate;

    @NotNull
    @Column(columnDefinition = "VARCHAR(50)")
    private String status;

    //    @JsonIgnore
    @ManyToOne
    @NotNull
    @JoinColumn(name = "responsible_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private DataProfile dataProfile;

    //    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "tb_job_talent",
            joinColumns = {@JoinColumn(name = "job_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
            )},
            inverseJoinColumns = {@JoinColumn(name = "talent_bank_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
            )}
    )
    private Set<TalentBank> talentBank = new HashSet<>();
}
