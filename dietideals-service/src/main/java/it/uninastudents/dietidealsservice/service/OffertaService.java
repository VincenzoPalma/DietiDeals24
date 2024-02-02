package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import it.uninastudents.dietidealsservice.repository.specs.OffertaSpecs;
import jakarta.persistence.EntityManager;
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
    private final AstaRepository astaRepository;
    private final EntityManager entityManager;

    public Offerta salvaOfferta(Offerta offerta, UUID idAsta) {
        //prendere l'utente
        var asta = astaRepository.findById(idAsta).orElseThrow(() -> new IllegalArgumentException("ASTA NON TROVATA")); //gestione errori
        var nuovaOfferta = new Offerta();
        offerta.setAsta(asta); //entity manager non sa se l'asta esiste
        //set user

        //logica di business
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
