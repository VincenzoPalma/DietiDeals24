package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID>, JpaSpecificationExecutor<Utente> {

}
