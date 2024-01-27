package it.uninastudents.dietidealsservice.model.entity;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Utente", schema = "public", catalog = "ingswProva")
public class Utente {
    @Id
    @Column(name = "username", nullable = false, length = -1)
    private String username;

    @Column(name = "tipo", nullable = false, length = -1)
    private String tipo = "Compratore";

    @Column(name = "nome", nullable = false, length = -1)
    private String nome;

    @Column(name = "cognome", nullable = false, length = -1)
    private String cognome;

    @Column(name = "email", nullable = false, length = -1)
    private String email;

    @Column(name = "password", nullable = false, length = -1)
    private String password;

    @Column(name = "descrizione", length = 300)
    private String descrizione;

    @Column(name = "facebook", length = -1)
    private String facebook;

    @Column(name = "instagram", length = -1)
    private String instagram;

    @Column(name = "twitter", length = -1)
    private String twitter;

    @Column(name = "sitoWeb", length = -1)
    private String sitoWeb;

    @Column(name = "indirizzo", length = -1)
    private String indirizzo;


    @Column(name = "partitaIVA", length = -1)
    private String partitaIva;

    @Column(name = "documentoIdentita")
    private byte[] documentoIdentita;

    @Column(name = "fotoProfilo")
    private byte[] fotoProfilo;

    @OneToOne
    @JoinColumn(name = "contoCorrente")
    private ContoCorrente contoCorrente;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getSitoWeb() {
        return sitoWeb;
    }

    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public byte[] getDocumentoIdentita() {
        return documentoIdentita;
    }

    public void setDocumentoIdentita(byte[] documentoIdentita) {
        this.documentoIdentita = documentoIdentita;
    }

    public byte[] getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(byte[] fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public ContoCorrente getContoCorrente() {
        return contoCorrente;
    }

    public void setContoCorrente(ContoCorrente contoCorrente) {
        this.contoCorrente = contoCorrente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente that = (Utente) o;
        return Objects.equals(tipo, that.tipo) && Objects.equals(username, that.username) && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(descrizione, that.descrizione) && Objects.equals(facebook, that.facebook) && Objects.equals(instagram, that.instagram) && Objects.equals(twitter, that.twitter) && Objects.equals(sitoWeb, that.sitoWeb) && Objects.equals(indirizzo, that.indirizzo) && Objects.equals(partitaIva, that.partitaIva) && Arrays.equals(documentoIdentita, that.documentoIdentita) && Arrays.equals(fotoProfilo, that.fotoProfilo) && Objects.equals(contoCorrente, that.contoCorrente);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tipo, username, nome, cognome, email, password, descrizione, facebook, instagram, twitter, sitoWeb, indirizzo, partitaIva, contoCorrente);
        result = 31 * result + Arrays.hashCode(documentoIdentita);
        result = 31 * result + Arrays.hashCode(fotoProfilo);
        return result;
    }
}
