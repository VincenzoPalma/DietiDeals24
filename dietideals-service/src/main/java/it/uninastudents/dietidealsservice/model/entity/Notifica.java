package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "notifica")
public class Notifica extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Asta asta;

    @Column(nullable = false)
    private String contenuto;

}
