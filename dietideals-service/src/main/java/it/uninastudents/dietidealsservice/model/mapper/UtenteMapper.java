package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.DatiProfiloUtente;
import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtenteMapper {

    Utente utenteRegistrazioneToUtente(UtenteRegistrazione utenteRegistrazione);

    DatiProfiloUtente utenteToDatiProfiloUtente(Utente utente);

    Utente datiProfiloUtenteToUtente(DatiProfiloUtente datiProfiloUtente);
}
