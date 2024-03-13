package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.CartaRepository;
import it.uninastudents.dietidealsservice.repository.specs.CartaSpecs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Autowired
    private CartaRepository cartaRepository;

    @Test
    void getAllByUtenteTest(){
        List<Carta> carte = cartaRepository.findAll();
        System.out.println(carte.size());

        Utente utente = new Utente();
        utente.setId(UUID.fromString("2c936c5f-9978-4fcb-8738-33bf6bcd9572")); //id utente esistente
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
        assertEquals(risultati.size(), risultatiEffettivi.size());
        for (Carta carta : risultatiEffettivi) {
            assertEquals(utente.getId(), carta.getUtente().getId());
        }
    }

    @Test
    void testSalvaCarta() {
        Utente utente = new Utente();
        Carta nuovaCarta = new Carta();
        nuovaCarta.setDataScadenza(OffsetDateTime.now().plusYears(3));
        utente.setCarte(new HashSet<>());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(cartaRepositoryMock.save(any(Carta.class))).thenReturn(nuovaCarta);

        Carta result = cartaService.salvaCarta(nuovaCarta);

        assertEquals(nuovaCarta, result);
        assertEquals(utente, result.getUtente());
        assertTrue(utente.getCarte().contains(result));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(cartaRepositoryMock, times(1)).save(nuovaCarta);
    }
}
