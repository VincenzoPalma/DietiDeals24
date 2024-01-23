package it.uninastudents.dietidealsservice.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ContoCorrente", schema = "public", catalog = "ingswProva")
public class ContoCorrente {
    @Id
    @Column(name = "iban", nullable = false, length = 27)
    private String iban;

    @Column(name = "nomeTitolare", nullable = false, length = -1)
    private String nomeTitolare;

    @Column(name = "codiceBIC_SWIFT", nullable = false, length = 11)
    private String codiceBicSwift;

    @OneToOne@JoinColumn(name = "utente", nullable = false)
    private Utente utente;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNomeTitolare() {
        return nomeTitolare;
    }

    public void setNomeTitolare(String nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }

    public String getCodiceBicSwift() {
        return codiceBicSwift;
    }

    public void setCodiceBicSwift(String codiceBicSwift) {
        this.codiceBicSwift = codiceBicSwift;
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
        ContoCorrente that = (ContoCorrente) o;
        return Objects.equals(iban, that.iban) && Objects.equals(nomeTitolare, that.nomeTitolare) && Objects.equals(codiceBicSwift, that.codiceBicSwift) && Objects.equals(utente, that.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, nomeTitolare, codiceBicSwift, utente);
    }
}
