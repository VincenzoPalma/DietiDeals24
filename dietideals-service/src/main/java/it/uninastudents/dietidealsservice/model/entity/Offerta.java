package it.uninastudents.dietidealsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "offerta")
public class Offerta extends BaseEntity {

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Asta asta;

    @Column(nullable = false)
    private BigDecimal prezzo;

    @NotNull
    @Column(nullable = false)
    private StatoOfferta stato;


}
