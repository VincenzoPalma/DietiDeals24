package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.AstaDTO;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AstaMapper {

    Asta astaDTOToAsta(AstaDTO astaDTO);

    AstaDTO astaToAstaDTO(Asta asta);


}
