package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.entities.TalentBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TalentBankRepository extends JpaRepository<TalentBank, UUID> {
//    @Query("SELECT object FROM TalentBank object WHERE object.address.city.name LIKE %:city% " +
//            "AND object.curriculum.seniority LIKE %:seniority% ")
//    Page<TalentBank> filterTalentBank(String city, String seniority, Pageable pageable);
    TalentBank findByEmail(String email);
    @Query("SELECT object FROM TalentBank object WHERE object.name LIKE %:search%")
    Page<TalentBank> findBySearchName(String search, Pageable pageable);
}

