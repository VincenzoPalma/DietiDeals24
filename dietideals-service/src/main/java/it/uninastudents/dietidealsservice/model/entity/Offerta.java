package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.id.IdRelazioneAstaUtente;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@IdClass(IdRelazioneAstaUtente.class)
@Table(name = "Offerta", schema = "public", catalog = "ingswProva")
public class Offerta {
    @Id
    @ManyToOne
    @JoinColumn(name = "idAsta", nullable = false)
    private Asta asta;

    @Id
    @ManyToOne
    @JoinColumn(name = "usernameUtente", nullable = false)
    private Utente utente;

    @Column(name = "prezzo", nullable = false)
    private BigDecimal prezzo;

    @Column(name = "tempoCreazione", nullable = false)
    private LocalDateTime tempoCreazione;

    public LocalDateTime getTempoCreazione() {
        return tempoCreazione;
    }

    public void setTempoCreazione(LocalDateTime tempoCreazione) {
        this.tempoCreazione = tempoCreazione;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Asta getAsta() {
        return asta;
    }

    public void setAsta(Asta asta) {
        this.asta = asta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offerta offerta = (Offerta) o;
        return Objects.equals(asta, offerta.asta) && Objects.equals(utente, offerta.utente) && Objects.equals(prezzo, offerta.prezzo) && Objects.equals(tempoCreazione, offerta.tempoCreazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asta, utente, prezzo, tempoCreazione);
    }
}
