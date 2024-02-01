package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreaUtenteRequest;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.mapstruct.Mapper;

@Mapper
public interface UtenteMapper {
    Utente utenteDTOToUtente(CreaUtenteRequest utenteRequest);
}
