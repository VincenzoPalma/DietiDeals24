package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.repository.NotificaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificaService {

    private final NotificaRepository repository;

    public Notifica salvaNotifica(Notifica notifica) {
        return repository.save(notifica);
    }


}
