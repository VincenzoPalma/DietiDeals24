package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.id.IdRelazioneAstaUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface NotificaRepository extends JpaRepository<Notifica, IdRelazioneAstaUtente> {

    @Query("SELECT n FROM Notifica n WHERE n.utente.username = ?1 ORDER BY n.data DESC")
    Set<Notifica> findNotificaByUtenteUsername(String username);

}
