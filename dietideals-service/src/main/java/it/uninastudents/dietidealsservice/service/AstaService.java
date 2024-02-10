package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.User;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.specs.AstaSpecs;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AstaService {

    private final AstaRepository repository;

    public Asta salvaAsta(Asta asta) {
        asta.setStato(StatoAsta.ATTIVA);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //modificare nell'utente proprio (security filter)
        //asta.setProprietario(utente);
        return repository.save(asta);
    }

    public Page<Asta> getAll(Pageable pageable, String nome, TipoAsta tipo, CategoriaAsta categoria) {
        var finalName = nome != null ? "%".concat(nome.toUpperCase()).concat("%") : null;
        var spec = AstaSpecs.hasNome(finalName).and(AstaSpecs.hasTipo(tipo)).and(AstaSpecs.hasCategoria(categoria));
        return repository.findAll(spec, pageable);
    }

    public Page<Asta> getAsteUtenteByStato(Pageable pageable, UUID idProprietario, StatoAsta stato) {
        var spec = AstaSpecs.hasProprietario(idProprietario).and(AstaSpecs.hasStato(stato));
        return repository.findAll(spec, pageable);
    }

    public Page<Asta> getAsteACuiUtenteHaPartecipato(Pageable pageable, UUID idUtente, boolean vinta){
        if (vinta){
            return getAsteVinteByUtente(pageable, idUtente);
        } else {
            return getAstePartecipateByUtente(pageable, idUtente, StatoAsta.ATTIVA);
        }
    }

    public Page<Asta> getAstePartecipateByUtente(Pageable pageable, UUID idProprietario, StatoAsta stato) {
        var spec = AstaSpecs.hasStato(stato).and(AstaSpecs.hasOfferta(idProprietario));
        return repository.findAll(spec, pageable);
    }

    public Page<Asta> getAsteVinteByUtente(Pageable pageable, UUID idProprietario) {
        var spec = AstaSpecs.hasStato(StatoAsta.TERMINATA).and(AstaSpecs.hasOffertaVincente(idProprietario));
        return repository.findAll(spec, pageable);
    }
}