package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.ContoCorrenteDTO;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContoCorrenteMapper {

    ContoCorrente contoCorrenteDTOToContoCorrente(ContoCorrenteDTO contoCorrenteDTO);

    ContoCorrenteDTO contoCorrenteToContoCorrenteDTO(ContoCorrente contoCorrente);
}
