package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Company findByName(String name);
}
