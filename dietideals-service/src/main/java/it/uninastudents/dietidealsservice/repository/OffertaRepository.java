package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.id.IdRelazioneAstaUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OffertaRepository extends JpaRepository<Offerta, IdRelazioneAstaUtente> {

    Set<Offerta> findOffertaByAsta_IdAsta(Integer idAsta);

    @Query("SELECT o FROM Offerta o WHERE o.asta.idAsta = ?1 ORDER BY o.tempoCreazione DESC LIMIT 1")
    Offerta findUltimaOffertaByAsta_IdAsta(Integer idAsta);
}
