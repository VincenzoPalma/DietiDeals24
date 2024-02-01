package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreaAstaRequest;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import org.mapstruct.Mapper;

@Mapper
public interface AstaMapper {

    Asta astaDTOToAsta(CreaAstaRequest astaRequest);

}
