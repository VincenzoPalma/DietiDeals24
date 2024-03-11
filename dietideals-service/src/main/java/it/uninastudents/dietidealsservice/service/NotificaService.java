package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.NotificaRepository;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import it.uninastudents.dietidealsservice.repository.specs.NotificaSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificaService {

    private final UtenteService utenteService;
    private final NotificaRepository notificaRepository;
    private final UtenteRepository utenteRepository;

    public Notifica salvaNotifica(Notifica notifica, UUID idUtente) {
        Optional<Utente> utente = utenteRepository.findById(idUtente);
        if (utente.isPresent()) {
            notifica.setUtente(utente.get());
            return notificaRepository.save(notifica);
        }
        return null;
    }

    public void cancellaNotificheUtente() {
        Utente utente = utenteService.getUtenteAutenticato();
        var spec = NotificaSpecs.hasUtente(utente.getId());
        notificaRepository.delete(spec);
    }

    public List<Notifica> findAllNotificheUtente() {
        Utente utente = utenteService.getUtenteAutenticato();
        var spec = NotificaSpecs.hasUtente(utente.getId());
        return notificaRepository.findAll(spec);
    }
}
