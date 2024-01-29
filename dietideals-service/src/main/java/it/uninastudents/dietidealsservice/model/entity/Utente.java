package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "utente", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class Utente extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RuoloUtente ruolo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 300)
    private String descrizione;

    private String facebook;

    private String instagram;

    private String twitter;

    private String sitoWeb;

    private String indirizzo;

    private String partitaIva;

    private String urlDocumentoIdentita;

    private String urlFotoProfilo;

    @OneToOne
    private ContoCorrente contoCorrente;

    @OneToMany
    private Set<Offerta> offerte;

    @OneToMany
    private Set<Asta> aste;

    @OneToMany
    private Set<Notifica> notifiche;

    @OneToMany
    private Set<Carta> carte;

}
