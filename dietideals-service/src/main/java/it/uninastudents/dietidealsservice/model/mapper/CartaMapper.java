package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CartaRequest;
import it.uninastudents.dietidealsservice.model.entity.Carta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartaMapper {

    Carta cartaDTOToCarta(CartaRequest creaCartaRequest);

    CartaRequest cartaToCartaDTO(Carta carta);

    List<Carta> listCartaDTOToListCarta(List<CartaRequest> listCartaRequest);

    List<CartaRequest> listCartaToListCartaDTO(List<Carta> listCarta);
}
