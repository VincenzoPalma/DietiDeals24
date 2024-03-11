package it.uninastudents.dietidealsservice.model.dto;

import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class UtenteRegistrazione {

    @NotBlank
    private String username;

    @Enumerated(value = EnumType.STRING)
    private RuoloUtente ruolo;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @Past
    private LocalDate dataNascita;

    @Email
    private String email;

    private String password;

    @Pattern(regexp = "^\\d{11}$|^$")
    private String partitaIva;

    private String urlDocumentoIdentita;

    private String urlFotoProfilo;

    @Valid
    private CreaContoCorrente contoCorrente;

}
