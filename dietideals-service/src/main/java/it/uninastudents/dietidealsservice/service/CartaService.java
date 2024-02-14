package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.CartaRepository;
import it.uninastudents.dietidealsservice.repository.specs.CartaSpecs;
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

    private final CartaRepository cartaRepository;
    private final UtenteService utenteService;

    public Carta salvaCarta(Carta carta) {
        Utente utente = utenteService.getUtenteAutenticato();
        carta.setUtente(utente);
        return cartaRepository.save(carta);
    }

    public void cancellaCarta(UUID idCarta) {
        Utente utenteAutenticato = utenteService.getUtenteAutenticato();
        Optional<Carta> carta = cartaRepository.findById(idCarta);
        if (carta.isPresent() && carta.get().getUtente().getId().equals(utenteAutenticato.getId())){
            cartaRepository.deleteById(idCarta);
        }
    }

    public List<Carta> getAllByUtente() {
        Utente utenteAutenticato = utenteService.getUtenteAutenticato();
        var spec = CartaSpecs.hasUtente(utenteAutenticato.getId());
        return cartaRepository.findAll(spec);
    }
}
