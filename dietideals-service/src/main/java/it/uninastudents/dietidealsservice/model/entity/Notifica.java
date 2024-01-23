package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.id.IdRelazioneAstaUtente;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(IdRelazioneAstaUtente.class)
@Table(name = "Notifica", schema = "public", catalog = "ingswProva")
public class Notifica {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_Asta", nullable = false)
    private Asta asta;

    @Id
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Utente utente;

    @Column(name = "contenuto", nullable = false, length = -1)
    private String contenuto;

    @Column(name = "data", nullable = false)
    private Timestamp data;

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Asta getAsta() {
        return asta;
    }

    public void setAsta(Asta asta) {
        this.asta = asta;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notifica that = (Notifica) o;
        return asta == that.asta && Objects.equals(contenuto, that.contenuto) && Objects.equals(data, that.data) && Objects.equals(utente, that.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contenuto, data, asta, utente);
    }
}
