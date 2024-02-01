package it.uninastudents.dietidealsservice.model.dto;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreaOffertaRequest {
    @NotNull
    private Utente utente;

    @NotBlank
    private Asta asta;

    @NotNull
    @Positive
    private BigDecimal prezzo;

    @NotNull
    private boolean vincente;
}
