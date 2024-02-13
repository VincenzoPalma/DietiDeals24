package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.CartaRepository;
import it.uninastudents.dietidealsservice.repository.specs.CartaSpecs;
import it.uninastudents.dietidealsservice.repository.specs.UtenteSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CartaService {

    private final CartaRepository repository;
    private final UtenteService utenteService;

    public Carta salvaCarta(Carta carta, UUID idUtente) {
        //recupero utente
        //set utente
        return repository.save(carta);
    }

    public void cancellaCarta(UUID idCarta) {
        Utente utenteAutenticato = utenteService.getUtenteAutenticato();
        Carta carta = findCartaById(idCarta);
        if (carta != null && carta.getUtente().getId().equals(utenteAutenticato.getId())){
            repository.deleteById(idCarta);
        }
    }

    public Carta findCartaById(UUID id)
    {
        var spec = CartaSpecs.hasId(id);
        Optional<Carta> result = repository.findOne(spec);
        return result.orElse(null);
    }

    public List<Carta> getAllByUtente() {
        Utente utenteAutenticato = utenteService.getUtenteAutenticato();
        var spec = CartaSpecs.hasUtente(utenteAutenticato.getId());
        return repository.findAll(spec);
    }
}
