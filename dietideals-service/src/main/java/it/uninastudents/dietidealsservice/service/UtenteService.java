package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository repository;

    public Utente salvaUtente(Utente utente) {
        return repository.save(utente);
    }

    public void cancellaUtente(UUID idUtente) {
        repository.deleteById(idUtente);
    }
}
