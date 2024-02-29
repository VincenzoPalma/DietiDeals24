package it.uninastudents.dietidealsservice.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import it.uninastudents.dietidealsservice.model.User;
import it.uninastudents.dietidealsservice.model.dto.DatiProfiloUtente;
import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import it.uninastudents.dietidealsservice.model.mapper.ContoCorrenteMapper;
import it.uninastudents.dietidealsservice.model.mapper.UtenteMapper;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import it.uninastudents.dietidealsservice.repository.specs.UtenteSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final ContoCorrenteRepository contoCorrenteRepository;
    private final ContoCorrenteMapper contoCorrenteMapper;
    private final UtenteMapper utenteMapper;

    public Utente findUtenteByIdAuth(String idAuth) {
        var spec = UtenteSpecs.hasIdAuth(idAuth);
        Optional<Utente> result = utenteRepository.findOne(spec);
        return result.orElse(null);
    }

    public Utente registraUtente(UtenteRegistrazione utenteRegistrazione, String idFireBase) {
        //inserimento utente nel db
        Utente utente = null;
        try {
        if (utenteRegistrazione.getContoCorrente() == null){
            utenteRegistrazione.setRuolo(RuoloUtente.COMPRATORE);
        } else {
            utenteRegistrazione.setRuolo(RuoloUtente.VENDITORE);
        }
        utente = utenteMapper.utenteRegistrazioneToUtente(utenteRegistrazione);
        utente.setContoCorrente(null);
            utente = utenteRepository.save(utente);
            ContoCorrente nuovoContoCorrente = contoCorrenteMapper.creaContoCorrenteToContoCorrente(utenteRegistrazione.getContoCorrente());
            if (nuovoContoCorrente != null && utente.getRuolo().equals(RuoloUtente.VENDITORE)) {
                nuovoContoCorrente.setUtente(utente);
                nuovoContoCorrente = contoCorrenteRepository.save(nuovoContoCorrente);
                utente.setContoCorrente(nuovoContoCorrente);
                utente = utenteRepository.save(utente);
            }
        //registrazione utente
        if (idFireBase == null) {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(utenteRegistrazione.getEmail())
                    .setPassword(utenteRegistrazione.getPassword())
                    .setDisplayName(utenteRegistrazione.getUsername());
            if (utenteRegistrazione.getUrlFotoProfilo() != null) {
                request.setPhotoUrl(utenteRegistrazione.getUrlFotoProfilo());
            }
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            utente.setIdAuth(userRecord.getUid());
        } else {
            utente.setIdAuth(idFireBase);
        }
            utente = utenteRepository.save(utente);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
        return utente;
    }

    public Utente getUtenteAutenticato() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUtenteByIdAuth(user.getUid());
    }

    public DatiProfiloUtente getDatiUtente(UUID idUtente) {
        if (idUtente == null) {
            return utenteMapper.utenteToDatiProfiloUtente(getUtenteAutenticato());
        } else {
            Optional<Utente> risultato = utenteRepository.findById(idUtente);
            if (risultato.isPresent()) {
                return utenteMapper.utenteToDatiProfiloUtente(risultato.get());
            } else {
                throw new IllegalArgumentException("Utente non trovato.");
            }
        }
    }

    public Utente modificaDatiUtente(DatiProfiloUtente datiProfiloUtente) {
        Utente utente = getUtenteAutenticato();
        utente.setDescrizione(Objects.requireNonNullElse(datiProfiloUtente.getDescrizione(), utente.getDescrizione()));
        utente.setFacebook(Objects.requireNonNullElse(datiProfiloUtente.getFacebook(), utente.getFacebook()));
        utente.setTwitter(Objects.requireNonNullElse(datiProfiloUtente.getTwitter(), utente.getTwitter()));
        utente.setInstagram(Objects.requireNonNullElse(datiProfiloUtente.getInstagram(), utente.getInstagram()));
        utente.setIndirizzo(Objects.requireNonNullElse(datiProfiloUtente.getIndirizzo(), utente.getIndirizzo()));
        utente.setSitoWeb(Objects.requireNonNullElse(datiProfiloUtente.getSitoWeb(), utente.getSitoWeb()));
        utente.setUrlFotoProfilo(Objects.requireNonNullElse(datiProfiloUtente.getUrlFotoProfilo(), utente.getUrlFotoProfilo()));
        return utenteRepository.save(utente);
    }

    public Utente getUtenteByEmail(String email){
        var spec = UtenteSpecs.hasEmail(email);
        Optional<Utente> result = utenteRepository.findOne(spec);
        return result.orElse(null);
    }

}
