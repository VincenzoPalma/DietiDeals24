package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.UtenteRequest;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
    Utente utenteDTOToUtente(UtenteRequest utenteRequest);

    UtenteRequest utenteToUtenteDTO(Utente utente);
}
