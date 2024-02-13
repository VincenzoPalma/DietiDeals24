package it.uninastudents.dietidealsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "idAuth")
})
public class Utente extends BaseEntity {

    private String idAuth;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private RuoloUtente ruolo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataNascita;

    @Column(nullable = false)
    private String email;

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

    @JsonIgnore
    @OneToOne
    private ContoCorrente contoCorrente;

    @JsonIgnore
    @OneToMany
    private Set<Offerta> offerte;

    @JsonIgnore
    @OneToMany
    private Set<Asta> aste;

    @JsonIgnore
    @OneToMany
    private Set<Notifica> notifiche;

    @JsonIgnore
    @OneToMany
    private Set<Carta> carte;

}
