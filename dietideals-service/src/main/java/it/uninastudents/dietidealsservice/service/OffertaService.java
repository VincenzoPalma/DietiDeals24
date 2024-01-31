package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import it.uninastudents.dietidealsservice.repository.specs.OffertaSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OffertaService {

    private final OffertaRepository repository;

    public Offerta salvaOfferta(Offerta offerta) {
        return repository.save(offerta);
    }

    public void cancellaOfferta(UUID idOfferta) {
        repository.deleteById(idOfferta);
    }

    public List<Offerta> findAllByAsta(UUID idAsta) {
        var spec = OffertaSpecs.hasAsta(idAsta);
        return repository.findAll(spec);
    }

    public Optional<Offerta> findOffertaVincenteByAsta(UUID idAsta) {
        var spec = OffertaSpecs.hasAsta(idAsta).and(OffertaSpecs.isVincente());
        return repository.findOne(spec);
    }

}
