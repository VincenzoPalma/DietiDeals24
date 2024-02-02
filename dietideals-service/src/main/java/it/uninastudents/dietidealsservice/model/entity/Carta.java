package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "carta")
public class Carta extends BaseEntity {

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String nomeTitolare;

    @Column(nullable = false, length = 3)
    private String codiceCvvCvc;

    @Column(nullable = false)
    private OffsetDateTime dataScadenza;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

}