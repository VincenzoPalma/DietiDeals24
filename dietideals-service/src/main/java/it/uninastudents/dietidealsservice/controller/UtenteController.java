package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.dto.DatiProfiloUtente;
import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import it.uninastudents.dietidealsservice.service.UtenteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final String urlDatiUtente = "/utente/%s/datiUtente";

    @PostMapping("/registrazione")
    public ResponseEntity<Utente> saveUtente(@RequestBody @Valid UtenteRegistrazione datiRegistrazione, @RequestParam(required = false) String idFirebase) {
        Utente utente = utenteService.registraUtente(datiRegistrazione, idFirebase);
        if (utente != null) {
            return ResponseEntity.created(URI.create("/registrazione/%s".formatted(utente.getId()))).body(utente);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/registrazione/esisteEmail/{email}")
    public ResponseEntity<Utente> getUtenteByEmail(@PathVariable @Email String email) {
        Utente utente = utenteService.getUtenteByEmail(email);
        return ResponseEntity.ok(utente);
    }

    @PutMapping("/utente/modificaDatiUtente")
    public ResponseEntity<Utente> modifyDatiUtente(@RequestBody @Valid DatiProfiloUtente datiProfiloUtente) {
        Utente utente = utenteService.modificaDatiUtente(datiProfiloUtente);
        return ResponseEntity.ok().location(URI.create(urlDatiUtente.formatted(utente.getId()))).body(utente);
    }

    @PutMapping("/utente/datiVenditore/partitaIva")
    public ResponseEntity<Utente> modifyPartitaIva(@RequestBody String partitaIva) {
        Utente utente = utenteService.modificaPartitaIva(partitaIva);
        return ResponseEntity.ok().location(URI.create(urlDatiUtente.formatted(utente.getId()))).body(utente);
    }

    @PutMapping("/utente/datiVenditore/documentoVenditore")
    public ResponseEntity<Utente> modifyDocumentoVenditore(@RequestBody String urlDocumento) {
        Utente utente = utenteService.modificaDocumentoVenditore(urlDocumento);
        return ResponseEntity.ok().location(URI.create(urlDatiUtente.formatted(utente.getId()))).body(utente);
    }

    @PutMapping("/utente/ruolo")
    public ResponseEntity<Utente> setUtenteVenditore() {
        Utente utente = utenteService.setUtenteVenditore();
        return ResponseEntity.ok().location(URI.create(urlDatiUtente.formatted(utente.getId()))).body(utente);
    }

    @GetMapping("/utente/datiUtente")
    public ResponseEntity<DatiProfiloUtente> getDatiUtente(@RequestParam(name = "idUtente", required = false) UUID idUtente) {
        try {
            return ResponseEntity.ok(utenteService.getDatiUtente(idUtente));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(200).build();
        }
    }

    @GetMapping("/utente/idUtente/{idAuth}")
    public ResponseEntity<Utente> getUtenteByIdAuth(@PathVariable(name = "idAuth") String idAuth) {
        return ResponseEntity.ok(utenteService.findUtenteByIdAuth(idAuth));
    }

    @GetMapping("/utente/ruolo")
    public ResponseEntity<RuoloUtente> getRuoloUtente() {
        return ResponseEntity.ok(utenteService.getRuoloUtente());
    }

    @GetMapping("/utente/partitaIva")
    public ResponseEntity<String> getPartitaIva() {
        return ResponseEntity.ok(utenteService.getPartitaIva());
    }
}
