package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.NotificaRequest;
import it.uninastudents.dietidealsservice.model.entity.Notifica;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificaMapper {

    Notifica notificaDTOToNotifica(NotificaRequest notificaRequest);

    NotificaRequest notificaToNotificaDTO(Notifica notifica);

}
