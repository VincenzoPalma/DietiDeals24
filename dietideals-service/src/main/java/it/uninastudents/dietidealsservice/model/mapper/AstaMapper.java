package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreaAsta;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AstaMapper {

    Asta creaAstaToAsta(CreaAsta creaAsta);


}
