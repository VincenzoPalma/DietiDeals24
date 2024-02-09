package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "utente", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class Utente extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private RuoloUtente ruolo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataNascita; //cancellare ogni utente e avviare il programma per aggiungere questo attributo al db

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
