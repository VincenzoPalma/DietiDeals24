package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.repository.NotificaRepository;
import it.uninastudents.dietidealsservice.repository.specs.NotificaSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificaService {

    private final NotificaRepository repository;

    public Notifica salvaNotifica(Notifica notifica) {
        return repository.save(notifica);
    }

    public void cancellaNotifica(UUID idNotifica) {
        repository.deleteById(idNotifica);
    }

    public void cancellaNotificheUtente(UUID idUtente) {
        var spec = NotificaSpecs.hasUtente(idUtente);
        repository.delete(spec);
    }

    public List<Notifica> findAllNotificheUtente(UUID idUtente) {
        var spec = NotificaSpecs.hasUtente(idUtente);
        return repository.findAll(spec);
    }
}
