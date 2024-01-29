package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "carta")
public class Carta extends BaseEntity {

    @NotBlank
    @Pattern(regexp = "^[0-9]{16}$")
    @Column(nullable = false)
    private String numero;

    @NotBlank
    @Column(nullable = false)
    private String nomeTitolare;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3}$")
    @Column(nullable = false, length = 3)
    private String codiceCvvCvc;

    @Future
    @Column(nullable = false)
    private OffsetDateTime dataScadenza;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

}
