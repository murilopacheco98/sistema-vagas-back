package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.entities.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, UUID> {
    Keyword findByName(String keyword);
}
