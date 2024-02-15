package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.specs.AstaSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AstaService {

    private final AstaRepository repository;
    private final UtenteService utenteService;

    public Asta salvaAsta(Asta asta) {
        asta.setStato(StatoAsta.ATTIVA);
        Utente utente = utenteService.getUtenteAutenticato();
        if (utente != null) {
            if (!asta.getTipo().equals(TipoAsta.INVERSA) && utente.getRuolo().equals(RuoloUtente.COMPRATORE)) {
                throw new IllegalArgumentException("UTENTE COMPRATORE NON PUO' CREARE L'ASTA");
            } else {
                asta.setProprietario(utente);
                utente.getAste().add(asta);
                return repository.save(asta);
            }
        }
        return null;
    }

    public Page<Asta> getAll(Pageable pageable, String nome, TipoAsta tipo, CategoriaAsta categoria) {
        var finalName = nome != null ? "%".concat(nome.toUpperCase()).concat("%") : null;
        var spec = AstaSpecs.hasNome(finalName).and(AstaSpecs.hasTipo(tipo)).and(AstaSpecs.hasCategoria(categoria));
        return repository.findAll(spec, pageable);
    }

    public Page<Asta> getAsteUtenteByStato(Pageable pageable, StatoAsta stato) {
        Utente utente = utenteService.getUtenteAutenticato();
        var spec = AstaSpecs.hasProprietario(utente.getId()).and(AstaSpecs.hasStato(stato));
        return repository.findAll(spec, pageable);
    }

    public Page<Asta> getAsteACuiUtenteHaPartecipato(Pageable pageable, boolean vinta) {
        if (vinta) {
            return getAsteVinteByUtente(pageable);
        } else {
            return getAstePartecipateByUtente(pageable);
        }
    }

    public Page<Asta> getAstePartecipateByUtente(Pageable pageable) {
        Utente utente = utenteService.getUtenteAutenticato();
        var spec = AstaSpecs.hasStato(StatoAsta.ATTIVA).and(AstaSpecs.hasOfferta(utente.getId()));
        return repository.findAll(spec, pageable);
    }

    public Page<Asta> getAsteVinteByUtente(Pageable pageable) {
        Utente utente = utenteService.getUtenteAutenticato();
        var spec = AstaSpecs.hasStato(StatoAsta.TERMINATA).and(AstaSpecs.hasOffertaVincente(utente.getId()));
        return repository.findAll(spec, pageable);
    }
}
