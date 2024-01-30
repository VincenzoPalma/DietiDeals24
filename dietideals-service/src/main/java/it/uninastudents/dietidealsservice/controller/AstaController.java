package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.service.AstaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AstaController {

    private final AstaService astaService;

    @PostMapping("/asta/inserimento")
    public ResponseEntity<Asta> saveAsta(@Valid @RequestBody Asta asta){//passare parametri singolarmente o un oggetto che li ingloba, validazioni su ipotetica classe CreaAsta
        Asta nuovaAsta = astaService.salvaAsta(asta);
        return new ResponseEntity<>(nuovaAsta, HttpStatus.CREATED);
    }

    @GetMapping("/asta/ricerca")
    public Page<Asta> getAste(@RequestParam int page, @RequestParam int size, @RequestParam String nome, @RequestParam TipoAsta tipo, @RequestParam CategoriaAsta categoria){
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationDate").ascending());
        return astaService.getAll(pageable, nome, tipo, categoria);
    }

    @GetMapping("/asta/mieaste/asteutente")
    public Page<Asta> getAsteUtente(@RequestParam int page, @RequestParam int size, @RequestParam UUID idUtente, @RequestParam StatoAsta stato){
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationDate").ascending());
        return astaService.getAsteUtenteByStato(pageable, idUtente, stato);
    }

    @GetMapping("/asta/mieaste/astepartecipate")
    public Page<Asta> getAstePartecipate(@RequestParam int page, @RequestParam int size, @RequestParam UUID idUtente, @RequestParam StatoAsta stato){
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationDate").ascending());
        return astaService.getAstePartecipateUtente(pageable, idUtente, stato);
    }
}
