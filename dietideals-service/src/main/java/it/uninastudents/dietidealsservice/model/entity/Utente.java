package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Column(nullable = false)
    private String username;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RuoloUtente ruolo;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String cognome;

    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Size(max = 300)
    @Column(length = 300)
    private String descrizione;

    private String facebook;

    private String instagram;

    private String twitter;

    private String sitoWeb;

    private String indirizzo;

    @Pattern(regexp = "^[0-9]{11}$")
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
