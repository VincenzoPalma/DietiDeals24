package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.OffertaRequest;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface OffertaMapper {

    Offerta offertaDTOToOfferta(OffertaRequest offertaRequest);

    OffertaRequest offertaToOffertaDTO(Offerta offerta);

    Page<Offerta> listOffertaDTOToListOfferta(Page<OffertaRequest> listOffertaRequest);

    Page<OffertaRequest> listOffertaToListOffertaDTO(Page<Offerta> listOfferta);
}
