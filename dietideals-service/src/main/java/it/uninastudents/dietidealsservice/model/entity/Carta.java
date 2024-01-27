package it.uninastudents.dietidealsservice.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Carta", schema = "public", catalog = "ingswProva")
public class Carta {
    @Id
    @Column(name = "numero", nullable = false, length = 16)
    private String numero;

    @Column(name = "nomeTitolare", nullable = false, length = -1)
    private String nomeTitolare;

    @Column(name = "codiceCVV_CVC", nullable = false, length = 3)
    private String codiceCvvCvc;

    @Column(name = "dataScadenza", nullable = false)
    private LocalDate dataScadenza;

    @ManyToOne
    @JoinColumn(name = "utente", nullable = false)
    private Utente utente;

    public Carta(String numero, String nomeTitolare, String codiceCvvCvc, LocalDate dataScadenza, Utente utente) {
        this.numero = numero;
        this.nomeTitolare = nomeTitolare;
        this.codiceCvvCvc = codiceCvvCvc;
        this.dataScadenza = dataScadenza;
        this.utente = utente;
    }

    public Carta() {

    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomeTitolare() {
        return nomeTitolare;
    }

    public void setNomeTitolare(String nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }

    public String getCodiceCvvCvc() {
        return codiceCvvCvc;
    }

    public void setCodiceCvvCvc(String codiceCvvCvc) {
        this.codiceCvvCvc = codiceCvvCvc;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
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
        Carta that = (Carta) o;
        return Objects.equals(codiceCvvCvc, that.codiceCvvCvc) && Objects.equals(numero, that.numero) && Objects.equals(nomeTitolare, that.nomeTitolare) && Objects.equals(dataScadenza, that.dataScadenza) && Objects.equals(utente, that.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, nomeTitolare, codiceCvvCvc, dataScadenza, utente);
    }
}
