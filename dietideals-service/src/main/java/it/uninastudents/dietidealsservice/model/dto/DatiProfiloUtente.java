package it.uninastudents.dietidealsservice.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode
@NoArgsConstructor
@Data
public class DatiProfiloUtente {

    @Length(min = 0, max = 300)
    private String descrizione;

    private String facebook;

    private String instagram;

    private String twitter;

    private String sitoWeb;

    private String indirizzo;

    private String urlFotoProfilo;

}
