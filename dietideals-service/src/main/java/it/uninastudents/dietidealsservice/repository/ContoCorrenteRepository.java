package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContoCorrenteRepository extends JpaRepository<ContoCorrente, UUID> {

}
