package com.growdev.GrowdevPeople.repositories;

import com.growdev.GrowdevPeople.repositories.user.DataProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DataProfileRepositoryTest {
    @Autowired
    private DataProfileRepository repository;
    @Test
    public void createShouldAddDataProfile(){
        ///AAA
        //arrange
//        DataProfile dataProfile = new DataProfile("Samille", "55942002070", "sami@mail.com", "(51)98211-2685", Instant.now(), Instant.now());

        //action
//        DataProfile savedEntity = repository.save(dataProfile);
        //assert - essa classe métodos para as confirmações
        //assertEquals = valor de retorno e ação com o retorno
//        Assertions.assertEquals("Samille", dataProfile.getName());

    }
}