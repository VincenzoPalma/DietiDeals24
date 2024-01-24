package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartaRepository extends JpaRepository<Carta, String> {

    Set<Carta> findCartaByUtenteUsername(String username);
}
