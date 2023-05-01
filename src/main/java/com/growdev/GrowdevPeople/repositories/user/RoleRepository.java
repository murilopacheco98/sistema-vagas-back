package com.growdev.GrowdevPeople.repositories.user;

import com.growdev.GrowdevPeople.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_roles WHERE tb_roles.name IN (:search)")
    List<Role> teste(List<String> search);

    @Query(value = "SELECT object FROM Role object WHERE object.name IN :search")
    List<Role> teste2(@Param("search") List<String> search);
}
