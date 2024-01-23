package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.id.IdRelazioneAstaUtente;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@IdClass(IdRelazioneAstaUtente.class)
@Table(name = "Offerta", schema = "public", catalog = "ingswProva")
public class Offerta {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_Asta", nullable = false)
    private Asta asta;

    @Id
    @ManyToOne
    @JoinColumn(name = "usernameUtente", nullable = false)
    private Utente utente;

    @Column(name = "prezzo", nullable = false)
    private BigDecimal prezzo;

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
        Offerta that = (Offerta) o;
        return asta == that.asta && Objects.equals(prezzo, that.prezzo) && Objects.equals(utente, that.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prezzo, utente, asta);
    }
}
