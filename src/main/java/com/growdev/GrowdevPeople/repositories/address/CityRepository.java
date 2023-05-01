package com.growdev.GrowdevPeople.repositories.address;

import com.growdev.GrowdevPeople.entities.address.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
}
