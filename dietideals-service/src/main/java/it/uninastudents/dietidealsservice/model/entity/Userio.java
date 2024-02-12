package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "userio", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class Userio extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4408418647685225829L;
    private String uid;
    private String name;
    private String email;
    private boolean isEmailVerified;

    @Column(nullable = false)
    private RuoloUtente ruolo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataNascita;

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