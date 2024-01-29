package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "contoCorrente", uniqueConstraints = {
        @UniqueConstraint(columnNames = "iban"),
        @UniqueConstraint(columnNames = "codiceBicSwift")
})
public class ContoCorrente extends BaseEntity {

    @Column(nullable = false)
    private String iban;

    @Column(nullable = false)
    private String nomeTitolare;

    @Column(nullable = false)
    private String codiceBicSwift;

    @OneToOne
    @JoinColumn(nullable = false)
    private Utente utente;

}