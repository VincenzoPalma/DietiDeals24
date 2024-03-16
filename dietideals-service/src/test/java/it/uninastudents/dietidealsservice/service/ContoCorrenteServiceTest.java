package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@SuppressWarnings("unchecked")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class ContoCorrenteServiceTest {

    @Autowired
    private ContoCorrenteService contoCorrenteService;

    @MockBean
    private ContoCorrenteRepository contoCorrenteRepositoryMock;

    @MockBean
    private UtenteService utenteServiceMock;

    @Test
    void salvaContoCorrenteTest() {
        Utente utente = new Utente();
        ContoCorrente nuovoContoCorrente = new ContoCorrente();
        utente.setCarte(new HashSet<>());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(contoCorrenteRepositoryMock.save(nuovoContoCorrente)).thenReturn(nuovoContoCorrente);

        ContoCorrente result = contoCorrenteService.salvaContoCorrente(nuovoContoCorrente);

        assertEquals(nuovoContoCorrente, result);
        assertEquals(utente, result.getUtente());
        assertEquals(utente.getContoCorrente(), result);

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(contoCorrenteRepositoryMock, times(1)).save(nuovoContoCorrente);
    }

    @Test
    void findContoCorrenteByUtenteTest() {
        Utente utente = new Utente();
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);

        ContoCorrente contoCorrente = new ContoCorrente();

        when(contoCorrenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(contoCorrente));

        Optional<ContoCorrente> risultatoEffettivo = contoCorrenteService.findContoCorrenteByUtente();

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(contoCorrenteRepositoryMock, times(1)).findOne(any(Specification.class));
        risultatoEffettivo.ifPresent(corrente -> assertEquals(contoCorrente, corrente));
    }

    @Test
    void modificaContoCorrenteTest() {
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());

        ContoCorrente contoCorrente = new ContoCorrente();
        UUID idContoCorrente = UUID.randomUUID();
        contoCorrente.setId(idContoCorrente);

        utente.setContoCorrente(contoCorrente);
        contoCorrente.setUtente(utente);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(contoCorrenteRepositoryMock.findById(idContoCorrente)).thenReturn(Optional.of(contoCorrente));
        when(contoCorrenteRepositoryMock.save(any(ContoCorrente.class))).thenReturn(contoCorrente);

        ContoCorrente contoCorrenteModificato = contoCorrenteService.modificaContoCorrente(contoCorrente);

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(contoCorrenteRepositoryMock, times(1)).findById(idContoCorrente);
        verify(contoCorrenteRepositoryMock, times(1)).save(contoCorrente);
        assertEquals(utente, contoCorrenteModificato.getUtente());
        assertEquals(contoCorrente, contoCorrenteModificato);

    }

    @Test
    void modificaContoCorrenteNonTrovatoTest() {
        Utente utente = new Utente();

        ContoCorrente contoCorrente = new ContoCorrente();
        UUID idContoCorrente = UUID.randomUUID();
        contoCorrente.setId(idContoCorrente);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(contoCorrenteRepositoryMock.findById(idContoCorrente)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> contoCorrenteService.modificaContoCorrente(contoCorrente));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(contoCorrenteRepositoryMock, times(1)).findById(idContoCorrente);

    }

    @Test
    void modificaContoCorrenteUtenteNonValidoTest() {
        Utente utenteProprietario = new Utente();
        utenteProprietario.setId(UUID.randomUUID());

        ContoCorrente contoCorrente = new ContoCorrente();
        UUID idContoCorrente = UUID.randomUUID();
        contoCorrente.setId(idContoCorrente);

        utenteProprietario.setContoCorrente(contoCorrente);
        contoCorrente.setUtente(utenteProprietario);

        Utente utenteAutenticato = new Utente();
        utenteAutenticato.setId(UUID.randomUUID());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utenteAutenticato);
        when(contoCorrenteRepositoryMock.findById(idContoCorrente)).thenReturn(Optional.of(contoCorrente));

        assertThrows(IllegalArgumentException.class, () -> contoCorrenteService.modificaContoCorrente(contoCorrente));
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(contoCorrenteRepositoryMock, times(1)).findById(idContoCorrente);

    }
}
