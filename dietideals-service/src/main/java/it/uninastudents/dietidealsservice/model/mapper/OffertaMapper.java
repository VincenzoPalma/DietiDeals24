package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.OffertaDTO;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface OffertaMapper {

    Offerta offertaDTOToOfferta(OffertaDTO offertaDTO);

    OffertaDTO offertaToOffertaDTO(Offerta offerta);

}
