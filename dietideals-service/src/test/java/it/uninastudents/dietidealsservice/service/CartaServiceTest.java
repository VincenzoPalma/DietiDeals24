package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.CartaRepository;
import it.uninastudents.dietidealsservice.repository.specs.CartaSpecs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class CartaServiceTest {

    @MockBean
    private CartaRepository cartaRepositoryMock;

    @MockBean
    private UtenteService utenteServiceMock;

    @Autowired
    private CartaService cartaService;

//    @Test
//    void getAllByUtenteTest(){
//        Utente utente = new Utente();
//        utente.setId(UUID.randomUUID());
//        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
//
//        Carta carta1 = new Carta();
//        carta1.setUtente(utente);
//        Carta carta2 = new Carta();
//        carta2.setUtente(utente);
//        List<Carta> risultati = new ArrayList<>();
//        risultati.add(carta1);
//        risultati.add(carta2);
//
//        var spec = CartaSpecs.hasUtente(utente.getId());
//        when(cartaRepositoryMock.findAll(spec)).thenReturn(risultati);
//
//        List<Carta> risultatiEffettivi = cartaService.getAllByUtente();
//
//        assertEquals(2, risultatiEffettivi.size());
//        assertEquals(utente.getId(), risultatiEffettivi.get(0).getUtente().getId());
//        assertEquals(utente.getId(), risultatiEffettivi.get(1).getUtente().getId());
//    }

}
