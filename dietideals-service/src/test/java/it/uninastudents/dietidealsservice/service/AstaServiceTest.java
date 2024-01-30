package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.repository.AstaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaServiceTest {

    @MockBean
    private AstaRepository astaRepository;

    @InjectMocks
    private AstaService astaService;

    @Test
    void getAllTest(){

    }
}
