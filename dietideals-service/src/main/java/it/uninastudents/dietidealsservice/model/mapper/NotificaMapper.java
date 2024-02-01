package it.uninastudents.dietidealsservice.model.mapper;

import it.uninastudents.dietidealsservice.model.dto.CreateNotificaRequest;
import it.uninastudents.dietidealsservice.model.entity.Notifica;
import org.mapstruct.Mapper;

@Mapper
public interface NotificaMapper {
    Notifica notificaDTOToNotifica(CreateNotificaRequest notificaRequest);
}
