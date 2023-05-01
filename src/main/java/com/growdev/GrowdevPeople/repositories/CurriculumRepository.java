package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.entities.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, UUID> {
}
