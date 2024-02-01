package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.dto.CreaAstaRequest;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.model.mapper.AstaMapper;
import it.uninastudents.dietidealsservice.service.AstaService;
import it.uninastudents.dietidealsservice.utils.ControllerUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final AstaMapper mapper;

    @PostMapping("/asta/inserimento")
    public ResponseEntity<Asta> saveAsta(@Valid @RequestBody CreaAstaRequest astaRequest) {
        Asta asta = mapper.astaDTOToAsta(astaRequest);
        asta = astaService.salvaAsta(asta);
        return new ResponseEntity<>(asta, HttpStatus.CREATED);
    }

    @GetMapping("/asta/ricerca")
    public Page<Asta> getAste(@RequestParam int page, @RequestParam int size, @RequestParam String nome, @RequestParam TipoAsta tipo, @RequestParam CategoriaAsta categoria) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return astaService.getAll(pageable, nome, tipo, categoria);
    }

    @GetMapping("/asta/mie_aste/aste_utente")
    public Page<Asta> getAsteUtente(@RequestParam int page, @RequestParam int size, @RequestParam UUID idUtente, @RequestParam StatoAsta stato) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return astaService.getAsteUtenteByStato(pageable, idUtente, stato);
    }

    @GetMapping("/asta/mie_aste/aste_partecipate")
    public Page<Asta> getAstePartecipate(@RequestParam int page, @RequestParam int size, @RequestParam UUID idUtente, @RequestParam StatoAsta stato) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return astaService.getAstePartecipateByUtente(pageable, idUtente, stato);
    }

    @GetMapping("/asta/mie_aste/aste_vinte")
    public Page<Asta> getAstePartecipate(@RequestParam int page, @RequestParam int size, @RequestParam UUID idUtente) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return astaService.getAsteVinteByUtente(pageable, idUtente);
    }
}
