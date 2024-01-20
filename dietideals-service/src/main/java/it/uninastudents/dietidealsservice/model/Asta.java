package it.uninastudents.dietidealsservice.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Asta", schema = "public", catalog = "ingswProva")
public class Asta {
    @Id@GeneratedValue//generato
    @Column(name = "id_Asta", nullable = false)
    private int idAsta;

    @Column(name = "nome", nullable = false, length = -1)
    private String nome;

    @Column(name = "descrizione", nullable = false, length = 300)
    private String descrizione;

    @Column(name = "foto", nullable = true)
    private byte[] foto;

    @Column(name = "dataScadenza", nullable = true)
    private Date dataScadenza;

    @Column(name = "sogliaRialzo", nullable = true)
    private Object sogliaRialzo;

    @Column(name = "intervalloTempoOfferto", nullable = true)
    private Object intervalloTempoOfferto;

    @Column(name = "categoria", nullable = false, length = -1)
    private String categoria;

    @Column(name = "tipo", nullable = false, length = -1)
    private String tipo;

    @Column(name = "stato", nullable = false, length = -1)
    private String stato;

    @Column(name = "proprietario", nullable = false, length = -1)
    private String proprietario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdAsta() {
        return idAsta;
    }

    public void setIdAsta(int idAsta) {
        this.idAsta = idAsta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Object getSogliaRialzo() {
        return sogliaRialzo;
    }

    public void setSogliaRialzo(Object sogliaRialzo) {
        this.sogliaRialzo = sogliaRialzo;
    }

    public Object getIntervalloTempoOfferto() {
        return intervalloTempoOfferto;
    }

    public void setIntervalloTempoOfferto(Object intervalloTempoOfferto) {
        this.intervalloTempoOfferto = intervalloTempoOfferto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asta that = (Asta) o;
        return idAsta == that.idAsta && Objects.equals(nome, that.nome) && Objects.equals(descrizione, that.descrizione) && Arrays.equals(foto, that.foto) && Objects.equals(dataScadenza, that.dataScadenza) && Objects.equals(sogliaRialzo, that.sogliaRialzo) && Objects.equals(intervalloTempoOfferto, that.intervalloTempoOfferto) && Objects.equals(categoria, that.categoria) && Objects.equals(tipo, that.tipo) && Objects.equals(stato, that.stato) && Objects.equals(proprietario, that.proprietario);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nome, idAsta, descrizione, dataScadenza, sogliaRialzo, intervalloTempoOfferto, categoria, tipo, stato, proprietario);
        result = 31 * result + Arrays.hashCode(foto);
        return result;
    }
}
