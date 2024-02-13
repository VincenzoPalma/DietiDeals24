package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import it.uninastudents.dietidealsservice.repository.specs.ContoCorrenteSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContoCorrenteService {

    private final ContoCorrenteRepository repository;
    private final UtenteRepository utenteRepository;
    private final UtenteService utenteService;

    public ContoCorrente salvaContoCorrente(ContoCorrente contoCorrente) {
        Utente utente = utenteService.getUtenteAutenticato();
        utente.setContoCorrente(contoCorrente);
        utenteRepository.save(utente);
        return repository.save(contoCorrente);
    }

    //non funziona
    public ContoCorrente modificaContoCorrente(ContoCorrente contoCorrente) {
        Utente utente = utenteService.getUtenteAutenticato();
        Optional<ContoCorrente> nuovoContoCorrente = repository.findById(contoCorrente.getId());
        if (nuovoContoCorrente.isPresent() && nuovoContoCorrente.get().getUtente().getId().equals(utente.getId())){
            return repository.save(contoCorrente);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Optional<ContoCorrente> findContoCorrenteByUtente() {
        Utente utente = utenteService.getUtenteAutenticato();
        var spec = ContoCorrenteSpecs.hasUtente(utente.getId());
        return repository.findOne(spec);
    }
}