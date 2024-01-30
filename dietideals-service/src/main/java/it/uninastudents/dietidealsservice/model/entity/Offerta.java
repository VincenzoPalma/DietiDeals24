package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "offerta")
public class Offerta extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

    @NotBlank
    @ManyToOne
    @JoinColumn(nullable = false)
    private Asta asta;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal prezzo;

    @NotNull
    @Column(nullable = false)
    private boolean vincente;


}
