package it.uninastudents.dietidealsservice.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@Data
public class CartaDTO {

    @NotBlank
    @Pattern(regexp = "^[0-9]{16}$")
    private String numero;

    @NotBlank
    private String nomeTitolare;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3}$")
    private String codiceCvvCvc;

    @Future
    private OffsetDateTime dataScadenza;
}
