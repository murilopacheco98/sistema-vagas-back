package com.growdev.GrowdevPeople.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_curriculum")
public class Curriculum extends EntityBase {
    private String seniority;

//    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "tb_curriculum_keyword",
            joinColumns = {@JoinColumn(name = "curriculum_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
            )},
            inverseJoinColumns = {@JoinColumn(name = "keyword_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
            )}
    )
    private Set<Keyword> keyword = new HashSet<>();

    private String linkedin;

    private String github;

    private String otherLinks;
}
