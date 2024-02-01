package it.uninastudents.dietidealsservice.model.dto;

import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class CreaAstaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    @Size(max = 300)
    private String descrizione;

    private String urlFoto;

    @Future
    private OffsetDateTime dataScadenza;

    @Positive
    private BigDecimal sogliaRialzo;

    @Positive
    @Min(30)
    @Max(180)
    private Integer intervalloTempoOfferta;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoriaAsta categoria;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoAsta tipo;

    @NotNull
    private Utente proprietario;


}
