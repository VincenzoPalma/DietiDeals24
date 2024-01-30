package it.uninastudents.dietidealsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.annotation.Validated;

@SpringBootApplication
@EnableJpaRepositories
@Validated
public class DietidealsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietidealsServiceApplication.class, args);
    }

}
