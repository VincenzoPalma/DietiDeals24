package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreaOffertaRequest;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import org.mapstruct.Mapper;

@Mapper
public interface OffertaMapper {
    Offerta offertaDTOToOfferta(CreaOffertaRequest offertaRequest);
}
