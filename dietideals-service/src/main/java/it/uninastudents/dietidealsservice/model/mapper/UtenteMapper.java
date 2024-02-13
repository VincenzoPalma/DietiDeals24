package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
    Utente utenteDTOToUtente(UtenteRegistrazione utenteRegistrazione);

    UtenteRegistrazione utenteToUtenteDTO(Utente utente);
}
