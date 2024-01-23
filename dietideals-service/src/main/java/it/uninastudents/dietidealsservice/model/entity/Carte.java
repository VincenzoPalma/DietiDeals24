package it.uninastudents.dietidealsservice.model.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Carte", schema = "public", catalog = "ingswProva")
public class Carte {
    @Id
    @Column(name = "numero", nullable = false, length = 16)
    private String numero;

    @Column(name = "nomeTitolare", nullable = false, length = -1)
    private Object nomeTitolare;

    @Column(name = "codiceCVV_CVC", nullable = false)
    private short codiceCvvCvc;

    @Column(name = "dataScadenza", nullable = false)
    private Date dataScadenza;

    @ManyToOne @JoinColumn(name = "utente", nullable = false)
    private Utente utente;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Object getNomeTitolare() {
        return nomeTitolare;
    }

    public void setNomeTitolare(Object nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }

    public short getCodiceCvvCvc() {
        return codiceCvvCvc;
    }

    public void setCodiceCvvCvc(short codiceCvvCvc) {
        this.codiceCvvCvc = codiceCvvCvc;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
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
        Carte that = (Carte) o;
        return codiceCvvCvc == that.codiceCvvCvc && Objects.equals(numero, that.numero) && Objects.equals(nomeTitolare, that.nomeTitolare) && Objects.equals(dataScadenza, that.dataScadenza) && Objects.equals(utente, that.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, nomeTitolare, codiceCvvCvc, dataScadenza, utente);
    }
}
