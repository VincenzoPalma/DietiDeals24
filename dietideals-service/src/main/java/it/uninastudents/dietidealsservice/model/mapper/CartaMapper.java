package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CartaDTO;
import it.uninastudents.dietidealsservice.model.entity.Carta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartaMapper {

    Carta cartaDTOToCarta(CartaDTO creaCartaDTO);

    CartaDTO cartaToCartaDTO(Carta carta);

    List<Carta> listCartaDTOToListCarta(List<CartaDTO> listCartaDTO);

    List<CartaDTO> listCartaToListCartaDTO(List<Carta> listCarta);
}
