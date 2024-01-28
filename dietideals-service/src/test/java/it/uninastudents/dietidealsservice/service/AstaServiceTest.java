package it.uninastudents.dietidealsservice.service;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaServiceTest {
    @Autowired
    private AstaService astaService;


}
