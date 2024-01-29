package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContoCorrenteService {

    private final ContoCorrenteRepository repository;

    public ContoCorrente salvaContoCorrente(ContoCorrente contoCorrente) {
        return repository.save(contoCorrente);
    }

}
