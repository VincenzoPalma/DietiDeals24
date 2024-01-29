package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OffertaRepository extends JpaRepository<Offerta, UUID> {

}
