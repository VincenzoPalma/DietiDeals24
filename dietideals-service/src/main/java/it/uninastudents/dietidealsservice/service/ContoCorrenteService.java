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

@Service
@Transactional
@RequiredArgsConstructor
public class ContoCorrenteService {

    private final ContoCorrenteRepository contoCorrenteRepository;
    private final UtenteRepository utenteRepository;
    private final UtenteService utenteService;

    public ContoCorrente salvaContoCorrente(ContoCorrente contoCorrente) {
        Utente utente = utenteService.getUtenteAutenticato();
        contoCorrente.setUtente(utente);
        utente.setContoCorrente(contoCorrente);
        utenteRepository.save(utente);
        return contoCorrenteRepository.save(contoCorrente);
    }

    public ContoCorrente modificaContoCorrente(ContoCorrente contoCorrente) {
        Utente utente = utenteService.getUtenteAutenticato();
        Optional<ContoCorrente> nuovoContoCorrente = contoCorrenteRepository.findById(contoCorrente.getId());
        if (nuovoContoCorrente.isPresent() && nuovoContoCorrente.get().getUtente().getId().equals(utente.getId())){
            return contoCorrenteRepository.save(contoCorrente);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Optional<ContoCorrente> findContoCorrenteByUtente() {
        Utente utente = utenteService.getUtenteAutenticato();
        var spec = ContoCorrenteSpecs.hasUtente(utente.getId());
        return contoCorrenteRepository.findOne(spec);
    }
}