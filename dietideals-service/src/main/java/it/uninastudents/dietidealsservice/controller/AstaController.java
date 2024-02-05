package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.dto.AstaDTO;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.model.mapper.AstaMapper;
import it.uninastudents.dietidealsservice.service.AstaService;
import it.uninastudents.dietidealsservice.utils.ControllerUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AstaController {

    private final AstaService astaService;
    private final AstaMapper mapper;

    @PostMapping("/utente/{idUtente}/aste")
    public ResponseEntity<Asta> saveAsta(@PathVariable UUID idUtente, @Valid @RequestBody AstaDTO astaDTO) {
        Asta asta = astaService.salvaAsta(idUtente, mapper.astaDTOToAsta(astaDTO));
        return ResponseEntity.created(URI.create("/utente/%s/aste/%s".formatted(idUtente.toString(), asta.getId().toString()))).body(asta);
    }

    @GetMapping("/aste")
    public ResponseEntity<Page<Asta>> getAste(@RequestParam(name = "page", defaultValue = "0") @Min(0) int page, @RequestParam(name = "size", defaultValue = "12") @Min(1) int size, @RequestParam String nome, @RequestParam TipoAsta tipo, @RequestParam CategoriaAsta categoria) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return new ResponseEntity<>(astaService.getAll(pageable, nome, tipo, categoria), HttpStatus.OK);
    }

    @GetMapping("/utente/{idUtente}/aste")
    public  ResponseEntity<Page<Asta>> getAsteUtente(@PathVariable UUID idUtente, @RequestParam(name = "page", defaultValue = "0") @Min(0) int page, @RequestParam(name = "size", defaultValue = "12") @Min(1) int size, @RequestParam StatoAsta stato) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return new ResponseEntity<>(astaService.getAsteUtenteByStato(pageable, idUtente, stato), HttpStatus.OK);
    }

    @GetMapping("/utente/{idUtente}/offerte/asta")
    public ResponseEntity<Page<Asta>> getAstePartecipate(@PathVariable UUID idUtente, @RequestParam(name = "page", defaultValue = "0") @Min(0) int page, @RequestParam(name = "size", defaultValue = "12") @Min(1) int size, @RequestParam boolean vinta) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return new ResponseEntity<>(astaService.getAsteACuiUtenteHaPartecipato(pageable, idUtente, vinta), HttpStatus.OK);
    }
}
