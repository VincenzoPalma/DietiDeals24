package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.repository.AstaRepository;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaServiceTest {

    @MockBean
    private AstaRepository astaRepository;

    @Autowired
    private AstaService astaService;


}
