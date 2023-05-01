package com.growdev.GrowdevPeople.repositories.user;

import com.growdev.GrowdevPeople.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByCheckerCode(String checkerCode);

    User findByLogin(String email);
}
