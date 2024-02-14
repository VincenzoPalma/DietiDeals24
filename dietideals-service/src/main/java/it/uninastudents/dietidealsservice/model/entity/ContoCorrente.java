package it.uninastudents.dietidealsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
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

    @JsonIgnore
    @ManyToOne
    private Utente utente;

}