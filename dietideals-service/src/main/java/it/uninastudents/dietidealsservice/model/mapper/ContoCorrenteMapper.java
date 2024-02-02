package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.ContoCorrenteRequest;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContoCorrenteMapper {

    ContoCorrente contoCorrenteDTOToContoCorrente(ContoCorrenteRequest contoCorrenteRequest);

    ContoCorrenteRequest contoCorrenteToContoCorrenteDTO(ContoCorrente contoCorrente);
}
