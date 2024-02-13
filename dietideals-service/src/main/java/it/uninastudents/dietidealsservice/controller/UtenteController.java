package it.uninastudents.dietidealsservice.controller;

import com.google.firebase.auth.FirebaseAuthException;
import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.mapper.UtenteMapper;
import it.uninastudents.dietidealsservice.service.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final UtenteMapper utenteMapper;

    @PostMapping("/registrazione")
    public ResponseEntity<Utente> inserisciUtente(@RequestBody @Valid UtenteRegistrazione datiRegistrazione) throws FirebaseAuthException {
        Utente utente = utenteService.registraUtente(datiRegistrazione);
        return ResponseEntity.created(URI.create("/registrazione/%s".formatted(utente.getId()))).body(utente);
    }

}
