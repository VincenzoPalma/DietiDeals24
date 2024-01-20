package it.uninastudents.dietidealsservice.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Notifica", schema = "public", catalog = "ingswProva")
public class Notifica {
    @Id
    @Column(name = "id_Asta", nullable = false)
    private int idAsta;
    @EmbeddedId
    @Column(name = "Username", nullable = false, length = -1)
    private String username;

    @Column(name = "Contenuto", nullable = false, length = -1)
    private String contenuto;

    @Column(name = "Data", nullable = false)
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

    public int getIdAsta() {
        return idAsta;
    }

    public void setIdAsta(int idAsta) {
        this.idAsta = idAsta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notifica that = (Notifica) o;
        return idAsta == that.idAsta && Objects.equals(contenuto, that.contenuto) && Objects.equals(data, that.data) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contenuto, data, idAsta, username);
    }
}
