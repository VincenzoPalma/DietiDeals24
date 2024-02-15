package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.dto.CartaDTO;
import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.mapper.CartaMapper;
import it.uninastudents.dietidealsservice.service.CartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartaController {

    private final CartaService cartaService;
    private final CartaMapper cartaMapper;

    @PostMapping("/utente/carte")
    public ResponseEntity<Carta> saveCarta(@RequestBody @Valid CartaDTO cartaDTO) {
        Carta carta = cartaService.salvaCarta(cartaMapper.cartaDTOToCarta(cartaDTO));
        return ResponseEntity.created(URI.create("utente/carte/%s".formatted(carta.getId().toString()))).body(carta);
    }

    @DeleteMapping("/carte/{idCarta}")
    public ResponseEntity<Void> deleteCarta(@PathVariable UUID idCarta) {
        cartaService.cancellaCarta(idCarta);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/utente/carte")
    public ResponseEntity<List<Carta>> getCarteUtente() {
        return new ResponseEntity<>(cartaService.getAllByUtente(), HttpStatus.OK);
    }
}

