package com.growdev.GrowdevPeople.repositories.user;

import com.growdev.GrowdevPeople.entities.DataProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DataProfileRepository extends JpaRepository<DataProfile, UUID> {
    DataProfile findByEmail(String email);
}
