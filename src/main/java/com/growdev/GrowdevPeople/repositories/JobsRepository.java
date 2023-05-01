package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.entities.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobsRepository extends JpaRepository<Job, UUID> {

    @Query("SELECT object FROM Job object WHERE LOWER(object.title) LIKE %:search%")
    Page<Job> findBySearchTitle(String search, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM Job j INNER JOIN " +
            "DataProfile dt ON dt.id = j.dataProfile WHERE dataProfile.email = :email")
    Page<Job> findByEmail(String email, Pageable pageable);
//    @Query("SELECT object FROM Job object WHERE object.dataProfile.email = :email")
//    Page<Job> findByEmail(String email, Pageable pageable);

    @Query("SELECT object FROM Job object WHERE LOWER(object.title) LIKE %:title% " +
            "AND object.stateName LIKE %:state% AND LOWER(object.seniority) LIKE %:seniority% " +
            "AND LOWER(object.workFormat) LIKE %:workFormat%")
    List<Job> filterJob(String title, String workFormat, String seniority, String state);
}
