package it.uninastudents.dietidealsservice.model.id;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Utente;

import java.io.Serializable;
import java.util.Objects;

public class IdRelazioneAstaUtente implements Serializable {
    private Asta asta;
    private Utente utente;

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
        IdRelazioneAstaUtente that = (IdRelazioneAstaUtente) o;
        return Objects.equals(asta, that.asta) && Objects.equals(utente, that.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asta, utente);
    }
}
