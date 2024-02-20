package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.dto.CreaContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.mapper.ContoCorrenteMapper;
import it.uninastudents.dietidealsservice.service.ContoCorrenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContoCorrenteController {

    private final ContoCorrenteService contoCorrenteService;
    private final ContoCorrenteMapper contoCorrenteMapper;

    @PostMapping("/utente/contoCorrente")
    public ResponseEntity<ContoCorrente> saveContoCorrente(@RequestBody @Valid CreaContoCorrente creaContoCorrente) {
        ContoCorrente contoCorrente = contoCorrenteService.salvaContoCorrente(contoCorrenteMapper.creaContoCorrenteToContoCorrente(creaContoCorrente));
        return ResponseEntity.created(URI.create("/utente/contoCorrente/%s".formatted(contoCorrente.getId().toString()))).body(contoCorrente);
    }

    @PutMapping("/contoCorrente")
    public ResponseEntity<ContoCorrente> modifyContoCorrente(@RequestBody @Valid ContoCorrente newContoCorrente) {
        ContoCorrente contoCorrente = contoCorrenteService.modificaContoCorrente(newContoCorrente);
        return ResponseEntity.ok().location(URI.create("contoCorrente/%s".formatted(contoCorrente.getId().toString()))).body(contoCorrente);
    }

    @GetMapping("/utente/contoCorrente")
    public ResponseEntity<ContoCorrente> getContoCorrente() {
        Optional<ContoCorrente> risultato = contoCorrenteService.findContoCorrenteByUtente();
        if (risultato.isPresent()) {
            ContoCorrente contoCorrente = risultato.get();
            return ResponseEntity.ok(contoCorrente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
