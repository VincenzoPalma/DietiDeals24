package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.dto.ContoCorrenteDTO;
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

    @PostMapping("/utente/{idUtente}/conto_corrente")
    public ResponseEntity<ContoCorrente> saveContoCorrente(@PathVariable UUID idUtente, @RequestBody @Valid ContoCorrenteDTO contoCorrenteDTO){
        ContoCorrente contoCorrente = contoCorrenteService.salvaContoCorrente(idUtente, contoCorrenteMapper.contoCorrenteDTOToContoCorrente(contoCorrenteDTO));
        return ResponseEntity.created(URI.create("/utente/%s/conto_corrente/%s".formatted(idUtente.toString(), contoCorrente.getId().toString()))).body(contoCorrente);
    }

    @PutMapping("/conto_corrente")
    public ResponseEntity<ContoCorrente> modifyContoCorrente(@RequestBody @Valid ContoCorrente newContoCorrente){
        ContoCorrente contoCorrente = contoCorrenteService.modificaContoCorrente(newContoCorrente);
        return ResponseEntity.ok().location(URI.create("conto_corrente/%s".formatted(contoCorrente.getId().toString()))).body(contoCorrente);
    }

    @GetMapping("/utente/{idUtente}/conto_corrente")
    public ResponseEntity<ContoCorrente> getContoCorrente(@PathVariable UUID idUtente){
        Optional<ContoCorrente> risultato = contoCorrenteService.findContoCorrenteByUtente(idUtente);
        if (risultato.isPresent()){
            ContoCorrente contoCorrente = risultato.get();
            return ResponseEntity.ok(contoCorrente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
