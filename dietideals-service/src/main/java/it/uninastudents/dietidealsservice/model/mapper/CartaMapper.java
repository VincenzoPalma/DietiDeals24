package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreaCarta;
import it.uninastudents.dietidealsservice.model.entity.Carta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartaMapper {

    Carta creaCartaToCarta(CreaCarta creaCreaCarta);

}
