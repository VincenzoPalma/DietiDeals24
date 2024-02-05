package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
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

    public ContoCorrente salvaContoCorrente(UUID idUtente, ContoCorrente contoCorrente) {
        //recupero utente
        //set utente
        return repository.save(contoCorrente);
    }

    public ContoCorrente modificaContoCorrente(ContoCorrente contoCorrente) {
        return repository.save(contoCorrente);
    }

    public Optional<ContoCorrente> findContoCorrenteByUtente(UUID idUtente) {
        var spec = ContoCorrenteSpecs.hasUtente(idUtente);
        return repository.findOne(spec);
    }
}