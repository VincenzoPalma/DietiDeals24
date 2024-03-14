package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.repository.NotificaRepository;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@SuppressWarnings("unchecked")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class NotificaServiceTest {
    @MockBean
    private NotificaRepository notificaRepositoryMock;

    @MockBean
    private UtenteService utenteServiceMock;

    @MockBean
    private UtenteRepository utenteRepositoryMock;

    @Autowired
    private NotificaService notificaService;

    @Test
    void salvaNotificaTest() {
        Utente utente = new Utente();
        UUID idUtente = UUID.randomUUID();
        utente.setId(idUtente);
        Notifica notifica = new Notifica();

        when(utenteRepositoryMock.findById(idUtente)).thenReturn(Optional.of(utente));
        when(notificaRepositoryMock.save(any(Notifica.class))).thenReturn(notifica);

        Notifica result = notificaService.salvaNotifica(notifica, idUtente);

        assertEquals(notifica, result);
        assertEquals(utente, result.getUtente());

        verify(utenteRepositoryMock, times(1)).findById(idUtente);
        verify(notificaRepositoryMock, times(1)).save(notifica);
    }

    @Test
    void salvaNotificaUtenteNonTrovatoTest() {
        Utente utente = new Utente();
        UUID idUtente = UUID.randomUUID();
        utente.setId(idUtente);
        Notifica notifica = new Notifica();

        when(utenteRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.empty());

        Notifica result = notificaService.salvaNotifica(notifica, idUtente);

        assertNull(result);

        verify(utenteRepositoryMock, times(1)).findById(any(UUID.class));
        verify(notificaRepositoryMock, times(0)).save(notifica);
    }

    @Test
    void cancellaNotificheTest() {
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        notificaService.cancellaNotificheUtente();

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(notificaRepositoryMock, times(1)).delete(any(Specification.class));
    }

    @Test
    void findAllNotificheUtenteTest() {
        Utente utente = new Utente();
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);

        Notifica notifica1 = new Notifica();
        notifica1.setUtente(utente);
        Notifica notifica2 = new Notifica();
        notifica2.setUtente(utente);
        List<Notifica> risultati = new ArrayList<>();
        risultati.add(notifica1);
        risultati.add(notifica2);

        when(notificaRepositoryMock.findAll(any(Specification.class))).thenReturn(risultati);

        List<Notifica> risultatiEffettivi = notificaService.findAllNotificheUtente();

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(notificaRepositoryMock, times(1)).findAll(any(Specification.class));
        assertEquals(risultati, risultatiEffettivi);
    }
}
