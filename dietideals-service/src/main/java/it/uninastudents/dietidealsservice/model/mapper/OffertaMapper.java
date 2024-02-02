package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.OffertaRequest;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OffertaMapper {

    Offerta offertaDTOToOfferta(OffertaRequest offertaRequest);

    OffertaRequest offertaToOffertaDTO(Offerta offerta);

    List<Offerta> listOffertaDTOToListOfferta(List<OffertaRequest> listOffertaRequest);

    List<OffertaRequest> listOffertaToListOffertaDTO(List<Offerta> listOfferta);
}
