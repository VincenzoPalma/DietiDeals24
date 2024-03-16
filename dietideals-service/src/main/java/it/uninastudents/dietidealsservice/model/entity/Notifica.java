package it.uninastudents.dietidealsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "notifica")
public class Notifica extends BaseEntity {

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Asta asta;

    @Column(nullable = false)
    private String contenuto;

}
