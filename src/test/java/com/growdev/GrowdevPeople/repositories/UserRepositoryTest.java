package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.repositories.user.DataProfileRepository;
import com.growdev.GrowdevPeople.repositories.user.RoleRepository;
import com.growdev.GrowdevPeople.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DataProfileRepository dataProfileRepository;

    @Test
    public void createShouldAddNewUser(){
        ///AAA
        //arrange
//        Role role = new Role("ROLE_PARCEIRO", "role parceiro", 2, true, Instant.now(), Instant.now());
//        roleRepository.save(role);
//        DataProfile dataProfile = new DataProfile("Samille", "55942002070", "sami@mail.com", "(51)98211-2685", Instant.now(), Instant.now());
//        dataProfileRepository.save(dataProfile);
//        User user = new User("sami@mail.com", "1234", true, Instant.now(), Instant.now(), role);

        //action
//        User savedEntity = userRepository.save(user);

        //assert - essa classe métodos para as confirmações
        //assertEquals = valor de retorno e ação com o retorno
//        Assertions.assertEquals("sami@mail.com", user.getLogin());
    }
}