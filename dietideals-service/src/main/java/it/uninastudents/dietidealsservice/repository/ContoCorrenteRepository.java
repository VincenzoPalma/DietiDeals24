package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContoCorrenteRepository extends JpaRepository<ContoCorrente, String> {

    ContoCorrente findContoCorrenteByUtenteUsername(String username);
}
