package it.uninastudents.dietidealsservice.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Offerta", schema = "public", catalog = "ingswProva")
public class Offerta {
    @Id
    @Column(name = "UsernameUtente", nullable = false, length = -1)
    private String usernameUtente;

    //embedded
    @Column(name = "id_Asta", nullable = false)
    private int idAsta;


    @Column(name = "Prezzo", nullable = false)
    private Object prezzo;

    public Object getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Object prezzo) {
        this.prezzo = prezzo;
    }

    public String getUsernameUtente() {
        return usernameUtente;
    }

    public void setUsernameUtente(String usernameUtente) {
        this.usernameUtente = usernameUtente;
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
        Offerta that = (Offerta) o;
        return idAsta == that.idAsta && Objects.equals(prezzo, that.prezzo) && Objects.equals(usernameUtente, that.usernameUtente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prezzo, usernameUtente, idAsta);
    }
}
