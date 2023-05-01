package com.growdev.GrowdevPeople.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_keyword")
public class Keyword extends EntityBase {
    private String name;

//    @org.hibernate.annotations.ForeignKey(name = "none")
//    @ManyToMany(mappedBy = "keyword")
    private Set<Curriculum> curriculum = new HashSet<>();

//    @org.hibernate.annotations.ForeignKey(name = "none")
//    @ManyToMany(mappedBy = "keyword")
    private Set<Job> job = new HashSet<>();
}
