package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.service.OffertaService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OffertaController {

    private final OffertaService offertaService;

    @PostMapping("/aste/{idAsta}/offerte")
    public ResponseEntity<Offerta> saveOfferta(@PathVariable UUID idAsta, @RequestBody @Positive BigDecimal prezzo) throws SchedulerException {
        Offerta offerta = offertaService.salvaOfferta(idAsta, prezzo);
        return ResponseEntity.created(URI.create("/asta/%s/offerte/%s".formatted(idAsta.toString(), offerta.getId().toString()))).body(offerta);
    }

    @GetMapping("/aste/{idAsta}/offerte")
    public ResponseEntity<List<Offerta>> getOfferte(@PathVariable UUID idAsta, @RequestParam StatoOfferta statoOfferta) {
        return new ResponseEntity<>(offertaService.findOffertaByStato(idAsta, statoOfferta), HttpStatus.OK);
    }

    @PutMapping("/aste/{idOfferta}")
    public ResponseEntity<Offerta> modificaStatoOfferta(@PathVariable UUID idOfferta, @RequestParam StatoOfferta statoOfferta) throws SchedulerException {
        Offerta offertaModificata = offertaService.modificaStatoOfferta(idOfferta, statoOfferta);
        return ResponseEntity.created(URI.create("/asta/%s".formatted(idOfferta.toString()))).body(offertaModificata);
    }

}
