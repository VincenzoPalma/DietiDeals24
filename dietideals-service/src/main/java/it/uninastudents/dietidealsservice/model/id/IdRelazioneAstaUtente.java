package it.uninastudents.dietidealsservice.model.id;

import java.io.Serializable;
import java.util.Objects;

public class IdRelazioneAstaUtente implements Serializable {
    private String username;
    private int idAsta;

    public IdRelazioneAstaUtente(String username, int idAsta) {
        this.username = username;
        this.idAsta = idAsta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdAsta() {
        return idAsta;
    }

    public void setIdAsta(int idAsta) {
        this.idAsta = idAsta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdRelazioneAstaUtente idRelazioneAstaUtente = (IdRelazioneAstaUtente) o;
        return idAsta == idRelazioneAstaUtente.idAsta && Objects.equals(username, idRelazioneAstaUtente.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, idAsta);
    }
}
