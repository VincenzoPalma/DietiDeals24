package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import it.uninastudents.dietidealsservice.repository.specs.OffertaSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OffertaService {

    private final OffertaRepository repository;
    private final AstaRepository astaRepository;
    private final UtenteService utenteService;

    public Offerta salvaOfferta(UUID idAsta, BigDecimal prezzo) {
        Utente utente = utenteService.getUtenteAutenticato();
        var asta = astaRepository.findById(idAsta).orElseThrow(() -> new IllegalArgumentException("ASTA NON TROVATA")); //gestione errori
        if (asta.getStato().equals(StatoAsta.TERMINATA)){
            throw new IllegalArgumentException("ASTA TERMINATA, IMPOSSIBILE CREARE OFFERTA");
        }
        if (!confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta)) {
            throw new IllegalArgumentException("PREZZO NON VALIDO");
        }
        var nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(prezzo);
        nuovaOfferta.setStato(StatoOfferta.NON_VINCENTE);
        nuovaOfferta.setAsta(asta);
        nuovaOfferta.setUtente(utente);
        if (!asta.getTipo().equals(TipoAsta.SILENZIOSA)){
            Optional<Offerta> offertaVincente = findOptionalOffertaVincenteByAsta(idAsta);
            if (offertaVincente.isPresent()) {
                if (!confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente.get(), asta)) {
                    throw new IllegalArgumentException("PREZZO NON VALIDO");
                } else {
                    offertaVincente.get().setStato(StatoOfferta.NON_VINCENTE);
                    repository.save(offertaVincente.get());
                }
            }
            nuovaOfferta.setStato(StatoOfferta.VINCENTE);
        }
        //manda la notifica
        return repository.save(nuovaOfferta);
    }

    public Page<Offerta> findOffertaByStato(Pageable pageable, UUID idAsta, StatoOfferta statoOfferta) {
        if (statoOfferta.equals(StatoOfferta.NON_VINCENTE)) {
            return findAllByAsta(pageable, idAsta);
        } else if (statoOfferta.equals(StatoOfferta.VINCENTE)) {
            return findPageOffertaVincenteByAsta(pageable, idAsta);
        } else return Page.empty();
    }

    public Page<Offerta> findAllByAsta(Pageable pageable, UUID idAsta) {
        var spec = OffertaSpecs.hasAsta(idAsta).and(OffertaSpecs.hasStato(StatoOfferta.NON_VINCENTE));
        return repository.findAll(spec, pageable);
    }

    public Page<Offerta> findPageOffertaVincenteByAsta(Pageable pageable, UUID idAsta) {
        var spec = OffertaSpecs.hasAsta(idAsta).and(OffertaSpecs.hasStato(StatoOfferta.VINCENTE));
        return repository.findAll(spec, pageable);
    }

    public Optional<Offerta> findOptionalOffertaVincenteByAsta(UUID idAsta) {
        var spec = OffertaSpecs.hasAsta(idAsta).and(OffertaSpecs.hasStato(StatoOfferta.VINCENTE));
        return repository.findOne(spec);
    }

    private boolean confrontaPrezzoOffertaConPrezzoBaseAsta(BigDecimal prezzo, Asta asta) {
        if (asta.getTipo().equals(TipoAsta.INVERSA)) {
            return prezzo.compareTo(asta.getPrezzoBase()) <= 0;
        } else if (asta.getTipo().equals(TipoAsta.SILENZIOSA)) {
            return prezzo.compareTo(asta.getPrezzoBase()) >= 0;
        } else {
            return prezzo.compareTo(asta.getPrezzoBase().add(asta.getSogliaRialzo())) >= 0;
        }
    }

    private boolean confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(Offerta nuovaOfferta, Offerta offertaVincente, Asta asta) {
        if (asta.getTipo().equals(TipoAsta.INGLESE)) {
            return nuovaOfferta.getPrezzo().compareTo(offertaVincente.getPrezzo().add(asta.getSogliaRialzo())) >= 0;
        } else if (asta.getTipo().equals(TipoAsta.INVERSA)) {
            return nuovaOfferta.getPrezzo().compareTo(offertaVincente.getPrezzo()) <= 0;
        }
        return false;
    }
}
