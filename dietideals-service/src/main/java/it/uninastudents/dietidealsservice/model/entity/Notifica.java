package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "notifica")
public class Notifica extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Asta asta;

    @NotBlank
    @Column(nullable = false)
    private String contenuto;

}
