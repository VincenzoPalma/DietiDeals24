package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartaRepository extends JpaRepository<Carta, UUID> {


}
