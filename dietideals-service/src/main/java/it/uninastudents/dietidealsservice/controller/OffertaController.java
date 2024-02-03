package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.model.mapper.OffertaMapper;
import it.uninastudents.dietidealsservice.service.OffertaService;
import it.uninastudents.dietidealsservice.utils.ControllerUtils;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OffertaController {

    private final OffertaService offertaService;
    private final OffertaMapper mapper;

    @PostMapping("/aste/{idAsta}/offerte")
    public ResponseEntity<Offerta> saveOfferta(@PathVariable UUID idAsta, @RequestBody @Positive BigDecimal prezzo) {
        Offerta offerta = offertaService.salvaOfferta(idAsta, prezzo);
        //se riceve un errore dal service, ritorna qualcosa per indicare l'errore
        return ResponseEntity.created(URI.create("/asta/%s/offerte/%s".formatted(idAsta.toString(), offerta.getId()))).build(); //eventuale body;
    }

    @GetMapping("/aste/{id_asta}/offerte")
    public Page<Offerta> getOfferte(@PathVariable UUID id_asta, @RequestParam @Min(0) int page, @RequestParam @Min(1) int size, @RequestParam StatoOfferta statoOfferta) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").descending());
        return offertaService.findOffertaByStato(pageable, id_asta, statoOfferta);
    }
}
