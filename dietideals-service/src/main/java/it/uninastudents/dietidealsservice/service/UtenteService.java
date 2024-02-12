package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import it.uninastudents.dietidealsservice.repository.specs.UtenteSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository repository;

    public Utente findUtenteByEmail(String email)
    {
        var spec = UtenteSpecs.hasEmail(email);
        Optional<Utente> result = repository.findOne(spec);
        return result.orElse(null);
    }
}
