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

@RestController
@RequiredArgsConstructor
public class AstaController {

    private final AstaService astaService;
    private final AstaMapper mapper;

    @PostMapping("/utente/aste")
    public ResponseEntity<Asta> saveAsta(@Valid @RequestBody AstaDTO astaDTO) {
        Asta asta = astaService.salvaAsta(mapper.astaDTOToAsta(astaDTO));
        return ResponseEntity.created(URI.create("/utente/aste/%s".formatted(asta.getId().toString()))).body(asta);
    }

    @GetMapping("/aste")
    public ResponseEntity<Page<Asta>> getAste(@RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
                                              @RequestParam(name = "size", defaultValue = "12") @Min(1) int size,
                                              @RequestParam(name = "nome", required = false) String nome,
                                              @RequestParam(name = "tipo", required = false) TipoAsta tipo,
                                              @RequestParam(name = "categoria", required = false) CategoriaAsta categoria) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return ResponseEntity.ok(astaService.getAll(pageable, nome, tipo, categoria));
    }

    @GetMapping("/utente/aste")
    public ResponseEntity<Page<Asta>> getAsteUtente(@RequestParam(name = "page", defaultValue = "0") @Min(0) int page, @RequestParam(name = "size", defaultValue = "12") @Min(1) int size, @RequestParam(required = false, defaultValue = "ATTIVA") StatoAsta stato) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return new ResponseEntity<>(astaService.getAsteUtenteByStato(pageable, stato), HttpStatus.OK);
    }

    @GetMapping("/utente/offerte/asta")
    public ResponseEntity<Page<Asta>> getAstePartecipate(@RequestParam(name = "page", defaultValue = "0") @Min(0) int page, @RequestParam(name = "size", defaultValue = "12") @Min(1) int size, @RequestParam(name = "vinta", defaultValue = "false") boolean vinta) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return new ResponseEntity<>(astaService.getAsteACuiUtenteHaPartecipato(pageable, vinta), HttpStatus.OK);
    }
}
