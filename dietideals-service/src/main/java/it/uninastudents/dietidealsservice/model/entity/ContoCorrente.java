package it.uninastudents.dietidealsservice.model.entity;

import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank
    @Pattern(regexp = "^[a-zA-z0-9]{27}$")
    @Column(nullable = false)
    private String iban;

    @NotBlank
    @Column(nullable = false)
    private String nomeTitolare;

    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$|^[0-9]{8}$")
    @Column(nullable = false)
    private String codiceBicSwift;

    @NotNull
    @OneToOne
    @JoinColumn(nullable = false)
    private Utente utente;

}