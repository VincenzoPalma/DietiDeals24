package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.dto.AstaRequest;
import it.uninastudents.dietidealsservice.model.mapper.OffertaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OffertaController {

    private final OffertaController offertaController;
    private final OffertaMapper mapper;

    @PostMapping("/asta/{id}/offerta")
    public ResponseEntity<AstaRequest> saveOfferta(@PathVariable UUID id, @RequestBody int prezzo) {
        //chiamata al service con id e prezzo (dto se necessario, a scelta)
        return new ResponseEntity.created(URI.create("/asta/%s/offerte/%s".formatted(id.toString(), /*id oggetto mappato o non*/))) //eventuale body;
    }
}
