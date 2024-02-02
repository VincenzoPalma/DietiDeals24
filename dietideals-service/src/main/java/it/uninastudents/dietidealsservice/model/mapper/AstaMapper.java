package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.AstaRequest;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AstaMapper {

    Asta astaDTOToAsta(AstaRequest astaRequest);

    AstaRequest astaToAstaDTO(Asta asta);

    Page<AstaRequest> pageAstaToPageAstaDTO(Page<Asta> pageAsta);

    Page<Asta> pageAstaDTOToPageAsta(Page<AstaRequest> pageAstaRequest);

}
