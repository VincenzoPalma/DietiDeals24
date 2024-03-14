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
            if (utenteRegistrazione.getContoCorrente() == null) {
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
                Utente utente = risultato.get();
                return utenteMapper.utenteToDatiProfiloUtente(utente);
            } else {
                return null;
            }
        }
    }

    public Utente modificaDatiUtente(DatiProfiloUtente datiProfiloUtente) {
        Utente utente = getUtenteAutenticato();
        utente.setDescrizione(datiProfiloUtente.getDescrizione());
        utente.setFacebook(datiProfiloUtente.getFacebook());
        utente.setTwitter(datiProfiloUtente.getTwitter());
        utente.setInstagram(datiProfiloUtente.getInstagram());
        utente.setIndirizzo(datiProfiloUtente.getIndirizzo());
        utente.setSitoWeb(datiProfiloUtente.getSitoWeb());
        utente.setUrlFotoProfilo(datiProfiloUtente.getUrlFotoProfilo());
        return utenteRepository.save(utente);
    }

    public Utente getUtenteByEmail(String email) {
        var spec = UtenteSpecs.hasEmail(email);
        Optional<Utente> result = utenteRepository.findOne(spec);
        return result.orElse(null);
    }

    public RuoloUtente getRuoloUtente() {
        Utente utente = getUtenteAutenticato();
        var spec = UtenteSpecs.hasId(utente.getId());
        Optional<Utente> risultato = utenteRepository.findOne(spec);
        return risultato.map(Utente::getRuolo).orElse(null);
    }

    public Utente modificaPartitaIva(String partitaIva) {
        Utente utente = getUtenteAutenticato();
        utente.setPartitaIva(partitaIva.replace("\"", ""));
        return utenteRepository.save(utente);
    }

    public Utente modificaDocumentoVenditore(String urlDocumento) {
        Utente utente = getUtenteAutenticato();
        utente.setUrlDocumentoIdentita(urlDocumento.replace("\"", ""));
        return utenteRepository.save(utente);
    }

    public Utente setUtenteVenditore() {
        Utente utente = getUtenteAutenticato();
        utente.setRuolo(RuoloUtente.VENDITORE);
        return utenteRepository.save(utente);
    }

    public String getPartitaIva() {
        Utente utente = getUtenteAutenticato();
        var spec = UtenteSpecs.hasId(utente.getId());
        Optional<Utente> risultato = utenteRepository.findOne(spec);
        return risultato.map(Utente::getPartitaIva).orElse(null);
    }
}
