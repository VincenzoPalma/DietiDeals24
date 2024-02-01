package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreaCartaRequest;
import it.uninastudents.dietidealsservice.model.entity.Carta;
import org.mapstruct.Mapper;

@Mapper
public interface CartaMapper {

    Carta cartaDTOToCarta(CreaCartaRequest creaCartaRequest);
}
