package it.uninastudents.dietidealsservice.model.dto;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CreaUtenteRequest {

    @NotBlank
    private String username;

    @Enumerated(value = EnumType.STRING)
    private RuoloUtente ruolo;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @Email
    private String email;

    @NotBlank
    private String password;

    @Pattern(regexp = "^[0-9]{11}$")
    private String partitaIva;

    private String urlDocumentoIdentita;

    private String urlFotoProfilo;

    private ContoCorrente contoCorrente;

}
