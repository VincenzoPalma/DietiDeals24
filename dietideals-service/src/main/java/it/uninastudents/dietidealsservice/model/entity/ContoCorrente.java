package it.uninastudents.dietidealsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.uninastudents.dietidealsservice.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{27}$")
    @Column(nullable = false)
    private String iban;

    @NotBlank
    @Column(nullable = false)
    private String nomeTitolare;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{11}$|^[a-zA-Z0-9]{8}$")
    @Column(nullable = false)
    private String codiceBicSwift;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    private Utente utente;

}