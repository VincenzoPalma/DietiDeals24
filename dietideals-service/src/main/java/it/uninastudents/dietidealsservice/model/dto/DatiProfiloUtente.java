package it.uninastudents.dietidealsservice.model.dto;

import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode
@NoArgsConstructor
@Data
public class DatiProfiloUtente {

    @NotBlank
    private String username;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private RuoloUtente ruolo;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @Length(min = 0, max = 300)
    private String descrizione;

    private String facebook;

    private String instagram;

    private String twitter;

    private String sitoWeb;

    private String indirizzo;

    @Pattern(regexp = "^[0-9]{11}$")
    private String partitaIva;

    private String urlDocumentoIdentita;

    private String urlFotoProfilo;

}
