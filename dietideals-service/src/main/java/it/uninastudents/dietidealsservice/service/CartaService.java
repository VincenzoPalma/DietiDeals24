package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.repository.CartaRepository;
import it.uninastudents.dietidealsservice.repository.specs.CartaSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CartaService {

    private final CartaRepository repository;

    public Carta salvaCarta(Carta carta) {
        return repository.save(carta);
    }

    public void cancellaCarta(UUID idCarta) {
        repository.deleteById(idCarta);
    }

    public List<Carta> getAllByUtente(UUID idUtente) {
        var spec = CartaSpecs.hasUtente(idUtente);
        return repository.findAll(spec);
    }
}
