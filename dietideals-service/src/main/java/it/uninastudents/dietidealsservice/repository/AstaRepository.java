package it.uninastudents.dietidealsservice.repository;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AstaRepository extends JpaRepository<Asta, Integer> {

    //da riguardare ordinamento
    @Query("SELECT a FROM Asta a WHERE a.stato = 'Attiva' ORDER BY CASE WHEN a.tipo = 'Inglese' THEN a.intervalloTempoOfferta ELSE a.dataScadenza END ASC")
    Set<Asta> findAstaAttivaOrderByDataScadenzaAsc();

    @Query("SELECT a FROM Asta a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :parolaChiave, '%')) ORDER BY CASE WHEN a.tipo = 'Inglese' THEN a.intervalloTempoOfferta ELSE a.dataScadenza END ASC")
    Set<Asta> trovaAstePerParolaChiaveByDataScadenzaAsc(@Param("parolaChiave") String parolaChiave);

    @Query("SELECT a FROM Asta a WHERE a.tipo = ?1 AND a.stato = 'Attiva' ORDER BY CASE WHEN a.tipo = 'Inglese' THEN a.intervalloTempoOfferta ELSE a.dataScadenza END ASC")
    Set<Asta> findAstaAttivaByTipoOrderByDataScadenzaAsc(String tipo);

    @Query("SELECT a FROM Asta a WHERE a.categoria = ?1 AND a.stato = 'Attiva' ORDER BY CASE WHEN a.tipo = 'Inglese' THEN a.intervalloTempoOfferta ELSE a.dataScadenza END ASC")
    Set<Asta> findAstaAttivaByCategoriaOrderByDataScadenzaAsc(String categoria);

    @Query("SELECT a FROM Asta a WHERE a.stato = 'Attiva' AND a.proprietario = ?1")
    Set<Asta> findAstaAttivaByProprietario(String username);

    @Query("SELECT a FROM Asta a WHERE a.stato = 'Terminata' AND a.proprietario = ?1")
    Set<Asta> findAstaTerminataByProprietario(String username);

    @Query("SELECT a FROM Asta a INNER JOIN Offerta o ON o.asta.idAsta = a.idAsta WHERE a.stato = 'Attiva' AND o.utente = ?1")
    Set<Asta> findAstaSeguitaByProprietario(String username);

    //da riguardare
    @Query("SELECT a FROM Asta a INNER JOIN Offerta o ON o.asta.idAsta = a.idAsta WHERE a.stato = 'Terminata' AND o.utente = ?1 AND (o.utente, o.asta) = (SELECT (utente, asta) FROM Offerta WHERE asta.idAsta = a.idAsta ORDER BY tempoCreazione DESC LIMIT 1)")
    Set<Asta> findAstaVintaByProprietario(String username);


}
