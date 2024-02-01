package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreaContoCorrenteRequest;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import org.mapstruct.Mapper;

@Mapper
public interface ContoCorrenteMapper {

    ContoCorrente contoCorrenteDTOToContoCorrente(CreaContoCorrenteRequest contoCorrenteRequest);
}
