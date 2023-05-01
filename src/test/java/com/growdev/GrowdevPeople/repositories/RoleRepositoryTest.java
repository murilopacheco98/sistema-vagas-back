package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.repositories.user.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void createShouldAddNweRole(){
//        Role role = new Role("ROLE_PARCEIRO", "role parceiro", 2, true, Instant.now(), Instant.now());

        //action
//        Role savedEntity = roleRepository.save(role);
        //assert - essa classe métodos para as confirmações
        //assertEquals = valor de retorno e ação com o retorno
//        Assertions.assertEquals("ROLE_PARCEIRO", role.getName());

    }

}