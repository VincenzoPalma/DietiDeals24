package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.CartaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@SuppressWarnings("unchecked")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class CartaServiceTest {

    @MockBean
    private CartaRepository cartaRepositoryMock;

    @MockBean
    private UtenteService utenteServiceMock;

    @Autowired
    private CartaService cartaService;


    @Test
    void getAllByUtenteTest() {
        Utente utente = new Utente();
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);

        Carta carta1 = new Carta();
        carta1.setUtente(utente);
        Carta carta2 = new Carta();
        carta2.setUtente(utente);
        List<Carta> risultati = new ArrayList<>();
        risultati.add(carta1);
        risultati.add(carta2);

        when(cartaRepositoryMock.findAll(any(Specification.class))).thenReturn(risultati);

        List<Carta> risultatiEffettivi = cartaService.getAllByUtente();

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(cartaRepositoryMock, times(1)).findAll(any(Specification.class));
        assertEquals(risultati, risultatiEffettivi);
    }

    @Test
    void salvaCartaTest() {
        Utente utente = new Utente();
        Carta nuovaCarta = new Carta();
        utente.setCarte(new HashSet<>());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(cartaRepositoryMock.save(nuovaCarta)).thenReturn(nuovaCarta);

        Carta result = cartaService.salvaCarta(nuovaCarta);

        assertEquals(nuovaCarta, result);
        assertEquals(utente, result.getUtente());
        assertTrue(utente.getCarte().contains(result));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(cartaRepositoryMock, times(1)).save(nuovaCarta);
    }

    @Test
    void cancellaCartaTest() {
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        Carta carta = new Carta();
        UUID idCarta = UUID.randomUUID();
        utente.setCarte(new HashSet<>());
        utente.getCarte().add(carta);
        carta.setUtente(utente);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(cartaRepositoryMock.findById(idCarta)).thenReturn(Optional.of(carta));
        cartaService.cancellaCarta(idCarta);

        verify(cartaRepositoryMock, times(1)).findById(idCarta);
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(cartaRepositoryMock, times(1)).deleteById(idCarta);
    }

    @Test
    void cancellaCartaNonTrovataTest() {
        Utente utente = new Utente();
        UUID idCarta = UUID.randomUUID();

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(cartaRepositoryMock.findById(idCarta)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> cartaService.cancellaCarta(idCarta));
        verify(cartaRepositoryMock, times(1)).findById(idCarta);
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
    }

    @Test
    void cancellaCartaUtenteNonValidoTest() {
        Utente utenteProprietario = new Utente();
        utenteProprietario.setId(UUID.randomUUID());
        Carta carta = new Carta();
        UUID idCarta = UUID.randomUUID();
        utenteProprietario.setCarte(new HashSet<>());
        utenteProprietario.getCarte().add(carta);
        carta.setUtente(utenteProprietario);

        Utente utenteAutenticato = new Utente();
        utenteAutenticato.setId(UUID.randomUUID());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utenteAutenticato);
        when(cartaRepositoryMock.findById(idCarta)).thenReturn(Optional.of(carta));

        assertThrows(IllegalArgumentException.class, () -> cartaService.cancellaCarta(idCarta));
        verify(cartaRepositoryMock, times(1)).findById(idCarta);
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
    }
}
