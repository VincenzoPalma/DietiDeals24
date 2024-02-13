package it.uninastudents.dietidealsservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ContoCorrenteDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{27}$")
    private String iban;

    @NotBlank
    private String nomeTitolare;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{11}$|^[a-zA-Z0-9]{8}$")
    private String codiceBicSwift;
}
