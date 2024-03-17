package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import it.uninastudents.dietidealsservice.repository.specs.OffertaSpecs;
import it.uninastudents.dietidealsservice.utils.NotificaUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OffertaService {

    private final OffertaRepository offertaRepository;
    private final AstaRepository astaRepository;
    private final UtenteService utenteService;
    private final NotificaService notificaService;
    private final Scheduler scheduler;

    public void modificaTriggerJobTermineAsta(String nomeJob, String gruppoJob, Trigger nuovoTrigger) throws SchedulerException {
        JobKey jobKey = new JobKey(nomeJob, gruppoJob);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

        if (jobDetail != null) {
            scheduler.deleteJob(jobKey);
            scheduler.scheduleJob(jobDetail, nuovoTrigger);
        } else {
            throw new SchedulerException("Job non trovato: " + nomeJob);
        }
    }

    public Offerta salvaOfferta(UUID idAsta, BigDecimal prezzo) throws SchedulerException {
        Utente utente = utenteService.getUtenteAutenticato();
        var asta = astaRepository.findById(idAsta).orElseThrow(() -> new IllegalArgumentException("ASTA NON TROVATA"));
        if (utente.equals(asta.getProprietario())) {
            throw new IllegalArgumentException("IMPOSSIBILE OFFRIRE AD UNA PROPRIA ASTA");
        }
        if (asta.getStato().equals(StatoAsta.TERMINATA)) {
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
        if (!asta.getTipo().equals(TipoAsta.SILENZIOSA)) {
            Optional<Offerta> offertaVincente = findOptionalOffertaVincenteByAsta(idAsta);
            gestisciOffertaVincente(offertaVincente, utente, nuovaOfferta, asta);
            nuovaOfferta.setStato(StatoOfferta.VINCENTE);
            if (asta.getTipo().equals(TipoAsta.INGLESE)) {
                modificaTriggerJobTermineAsta("termineAstaJob_" + asta.getId().toString(), "termineAsta",
                        TriggerBuilder.newTrigger()
                                .withIdentity("termineAstaTrigger_" + asta.getId().toString())
                                .startAt(java.util.Date.from(Instant.now().plus(asta.getIntervalloTempoOfferta(), ChronoUnit.MINUTES)))
                                .build());
            }
        } else {
            if (controlloOfferteUtenteAstaSilenziosa(utente.getId(), asta)) {
                throw new IllegalArgumentException("IMPOSSIBILE EFFETTUARE PIU' OFFERTE");
            }
        }
        asta.getOfferte().add(nuovaOfferta);
        utente.getOfferte().add(nuovaOfferta);
        return offertaRepository.save(nuovaOfferta);
    }

    public void gestisciOffertaVincente(Optional<Offerta> offertaVincente, Utente utente, Offerta nuovaOfferta, Asta asta) {
        if (offertaVincente.isPresent()) {
            if (utente.getId().equals(offertaVincente.get().getUtente().getId())) {
                throw new IllegalArgumentException("IMPOSSIBILE EFFETTUARE OFFERTE CONSECUTIVE");
            }
            if (!confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente.get(), asta)) {
                throw new IllegalArgumentException("PREZZO NON VALIDO");
            } else {
                offertaVincente.get().setStato(StatoOfferta.NON_VINCENTE);
                offertaRepository.save(offertaVincente.get());
                Notifica notifica = new Notifica();
                utente.getNotifiche().add(notifica);
                notifica.setAsta(asta);
                notifica.setUtente(utente);
                notifica.setContenuto(NotificaUtils.buildMessaggioOffertaSuperata(asta.getNome(), nuovaOfferta.getPrezzo()));
                notificaService.salvaNotifica(notifica, offertaVincente.get().getUtente().getId());
            }
        }
    }

    public boolean controlloOfferteUtenteAstaSilenziosa(UUID idUtente, Asta asta) {
        return findOffertaNonRifiutataByUtenteAstaSilenziosa(idUtente, asta).isPresent();
    }

    public Optional<Offerta> findOffertaNonRifiutataByUtenteAstaSilenziosa(UUID idUtente, Asta asta) {
        if (!asta.getTipo().equals(TipoAsta.SILENZIOSA)) {
            return Optional.empty();
        }
        var spec = OffertaSpecs.hasAsta(asta.getId()).and(OffertaSpecs.hasUtente(idUtente).and(OffertaSpecs.hasStato(StatoOfferta.NON_VINCENTE)));
        return offertaRepository.findOne(spec);
    }

    public List<Offerta> findOffertaByStato(UUID idAsta, StatoOfferta statoOfferta) {
        if (statoOfferta.equals(StatoOfferta.NON_VINCENTE)) {
            return findAllByAsta(idAsta);
        } else if (statoOfferta.equals(StatoOfferta.VINCENTE)) {
            return findListOffertaVincenteByAsta(idAsta);
        } else return Collections.emptyList();
    }

    public List<Offerta> findAllByAsta(UUID idAsta) {
        Optional<Asta> asta = astaRepository.findById(idAsta);
        if (asta.isPresent()) {
            var spec = OffertaSpecs.hasAsta(idAsta).and(OffertaSpecs.hasStato(StatoOfferta.NON_VINCENTE));
            return offertaRepository.findAll(spec);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Offerta> findListOffertaVincenteByAsta(UUID idAsta) {
        var spec = OffertaSpecs.hasAsta(idAsta).and(OffertaSpecs.hasStato(StatoOfferta.VINCENTE));
        return offertaRepository.findAll(spec);
    }

    public Optional<Offerta> findOptionalOffertaVincenteByAsta(UUID idAsta) {
        var spec = OffertaSpecs.hasAsta(idAsta).and(OffertaSpecs.hasStato(StatoOfferta.VINCENTE));
        return offertaRepository.findOne(spec);
    }

    public boolean confrontaPrezzoOffertaConPrezzoBaseAsta(BigDecimal prezzo, Asta asta) { //possibile metodo da documentazione
        if (asta.getTipo().equals(TipoAsta.INVERSA)) {
            return prezzo.compareTo(asta.getPrezzoBase()) <= 0;
        } else if (asta.getTipo().equals(TipoAsta.SILENZIOSA)) {
            return prezzo.compareTo(asta.getPrezzoBase()) >= 0;
        } else {
            return prezzo.compareTo(asta.getPrezzoBase().add(asta.getSogliaRialzo())) >= 0;
        }
    }

    public boolean confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(Offerta nuovaOfferta, Offerta offertaVincente, Asta asta) {
        if (asta.getTipo().equals(TipoAsta.INGLESE)) {
            return nuovaOfferta.getPrezzo().compareTo(offertaVincente.getPrezzo().add(asta.getSogliaRialzo())) >= 0;
        } else if (asta.getTipo().equals(TipoAsta.INVERSA)) {
            return nuovaOfferta.getPrezzo().compareTo(offertaVincente.getPrezzo()) <= 0;
        }
        return false;
    }

    public Offerta modificaStatoOfferta(UUID idOfferta, StatoOfferta stato) throws SchedulerException { //possibile test per documentazione
        var spec = OffertaSpecs.hasId(idOfferta);
        Optional<Offerta> offerta = offertaRepository.findOne(spec);
        if (offerta.isPresent()) {
            offerta.get().setStato(stato);
            if (stato.equals(StatoOfferta.VINCENTE) && offerta.get().getAsta().getTipo().equals(TipoAsta.SILENZIOSA)) {
                modificaTriggerJobTermineAsta("termineAstaJob_" + offerta.get().getAsta().getId().toString(), "termineAsta", TriggerBuilder.newTrigger()
                        .withIdentity("termineAstaTrigger_" + offerta.get().getAsta().getId().toString())
                        .startNow().build());
            }
            return offertaRepository.save(offerta.get());
        } else {
            return null;
        }
    }
}
