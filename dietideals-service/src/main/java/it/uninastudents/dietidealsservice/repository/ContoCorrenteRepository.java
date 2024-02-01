package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ContoCorrenteRepository extends JpaRepository<ContoCorrente, UUID>, JpaSpecificationExecutor<ContoCorrente> {

}
