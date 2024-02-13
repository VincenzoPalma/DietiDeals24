package it.uninastudents.dietidealsservice.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.mapper.ContoCorrenteMapper;
import it.uninastudents.dietidealsservice.model.mapper.UtenteMapper;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import it.uninastudents.dietidealsservice.repository.specs.UtenteSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final ContoCorrenteRepository contoCorrenteRepository;
    private final ContoCorrenteMapper contoCorrenteMapper;
    private final UtenteMapper utenteMapper;

    public Utente findUtenteByIdAuth(String idAuth)
    {
        var spec = UtenteSpecs.hasIdAuth(idAuth);
        Optional<Utente> result = utenteRepository.findOne(spec);
        return result.orElse(null);
    }

    public Utente registraUtente(UtenteRegistrazione utenteRegistrazione) throws FirebaseAuthException {
        //registrazione utente
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(utenteRegistrazione.getEmail())
                .setPassword(utenteRegistrazione.getPassword())
                .setDisplayName(utenteRegistrazione.getUsername());
        if (utenteRegistrazione.getUrlFotoProfilo() != null){
            request.setPhotoUrl(utenteRegistrazione.getUrlFotoProfilo());
        }
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        //inserimento utente nel db
        Utente utente = utenteMapper.utenteDTOToUtente(utenteRegistrazione);
        utente.setContoCorrente(null);
        utente.setIdAuth(userRecord.getUid());
        Utente nuovoUtente = utenteRepository.save(utente);
        ContoCorrente nuovoContoCorrente = contoCorrenteMapper.contoCorrenteDTOToContoCorrente(utenteRegistrazione.getContoCorrente());
        if (nuovoContoCorrente != null){
            nuovoContoCorrente = contoCorrenteRepository.save(nuovoContoCorrente);
            nuovoUtente.setContoCorrente(nuovoContoCorrente);
            nuovoUtente = utenteRepository.save(nuovoUtente);
        }
        return nuovoUtente;
    }
}
