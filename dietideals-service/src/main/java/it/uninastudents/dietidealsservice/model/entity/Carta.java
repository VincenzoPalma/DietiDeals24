package it.uninastudents.dietidealsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "carta", uniqueConstraints ={
    @UniqueConstraint(columnNames = "numero")
})

public class Carta extends BaseEntity {

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String nomeTitolare;

    @Column(nullable = false, length = 3)
    private String codiceCvvCvc;

    @Column(nullable = false)
    private OffsetDateTime dataScadenza;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

}