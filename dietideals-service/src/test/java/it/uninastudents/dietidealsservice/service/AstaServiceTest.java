package it.uninastudents.dietidealsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.exceptions.UnauthorizedException;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.utils.ControllerUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SuppressWarnings("unchecked")
@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaServiceTest {

    @SpyBean
    AstaService astaServiceSpy;
    @MockBean
    private AstaRepository astaRepositoryMock;
    @MockBean
    private Scheduler schedulerMock;
    @MockBean
    private UtenteService utenteServiceMock;
    @Autowired
    private AstaService astaService;

    @Test
    void salvaAstaUtenteVenditoreTest() throws SchedulerException, JsonProcessingException {
        Utente utente = new Utente();
        utente.setRuolo(RuoloUtente.VENDITORE);

        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE); //qualsiasi tipo di asta è valido quando l'utente è di tipo venditore
        asta.setStato(StatoAsta.ATTIVA);

        asta.setProprietario(utente);
        utente.setAste(new HashSet<>());
        utente.getAste().add(asta);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.save(asta)).thenReturn(asta);
        doNothing().when(astaServiceSpy).schedulerScadenzaAsta(asta);

        Asta astaSalvata = astaServiceSpy.salvaAsta(asta);

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(astaServiceSpy, times(1)).schedulerScadenzaAsta(asta);
        verify(astaRepositoryMock, times(1)).save(astaSalvata);
        assertEquals(utente, astaSalvata.getProprietario());
        assertTrue(utente.getAste().contains(astaSalvata));
        assertEquals(asta, astaSalvata);
        assertEquals(StatoAsta.ATTIVA, astaSalvata.getStato());
    }

    @Test
    void salvaAstaUtenteCompratoreTest() throws SchedulerException, JsonProcessingException {
        Utente utente = new Utente();
        utente.setRuolo(RuoloUtente.COMPRATORE);

        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA); //solo le aste inverse sono valide per un utente compratore
        asta.setStato(StatoAsta.ATTIVA);

        asta.setProprietario(utente);
        utente.setAste(new HashSet<>());
        utente.getAste().add(asta);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.save(any(Asta.class))).thenReturn(asta);
        doNothing().when(astaServiceSpy).schedulerScadenzaAsta(asta);

        Asta astaSalvata = astaServiceSpy.salvaAsta(asta);

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(astaServiceSpy, times(1)).schedulerScadenzaAsta(asta);
        verify(astaRepositoryMock, times(1)).save(asta);
        assertEquals(utente, astaSalvata.getProprietario());
        assertTrue(utente.getAste().contains(astaSalvata));
        assertEquals(asta, astaSalvata);
        assertEquals(StatoAsta.ATTIVA, astaSalvata.getStato());
    }

    @Test
    void salvaAstaUtenteNonTrovatoTest() throws SchedulerException, JsonProcessingException {
        Asta asta = new Asta();

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(null);

        Asta astaSalvata = astaService.salvaAsta(asta);
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        assertNull(astaSalvata);
    }

    @Test
    void salvaAstaUtenteNonValidoTest() {
        Utente utente = new Utente();
        utente.setRuolo(RuoloUtente.COMPRATORE);

        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE); //qualsiasi asta diversa da inversa


        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);

        assertThrows(UnauthorizedException.class, () -> astaService.salvaAsta(asta));
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
    }

    @Test
    void getAllTest() {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());
        String stringaRicerca = "test";
        TipoAsta tipo = TipoAsta.INGLESE;
        CategoriaAsta categoria = CategoriaAsta.ELETTRONICA;

        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setCategoria(categoria);
            nuovaAsta.setStato(StatoAsta.ATTIVA);
            nuovaAsta.setTipo(tipo);
            nuovaAsta.setNome("prova " + stringaRicerca + " test");
            listaAste.add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, 5);

        when(astaRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(risultati);

        Page<Asta> risultatiEffettivi = astaService.getAll(pageable, stringaRicerca, tipo, categoria);

        verify(astaRepositoryMock, times(1)).findAll(any(Specification.class), any(Pageable.class));
        assertEquals(risultati, risultatiEffettivi);

    }

    @Test
    void getAllStringaRicercaNullTest() {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());
        TipoAsta tipo = TipoAsta.INGLESE;
        CategoriaAsta categoria = CategoriaAsta.ELETTRONICA;

        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setCategoria(categoria);
            nuovaAsta.setStato(StatoAsta.ATTIVA);
            nuovaAsta.setTipo(tipo);
            listaAste.add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, 5);

        when(astaRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(risultati);

        Page<Asta> risultatiEffettivi = astaService.getAll(pageable, null, tipo, categoria);

        verify(astaRepositoryMock, times(1)).findAll(any(Specification.class), any(Pageable.class));
        assertEquals(risultati, risultatiEffettivi);

    }

    @Test
    void getAsteUtenteByStatoTest() {
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        utente.setAste(new HashSet<>());
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);

        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());

        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setStato(StatoAsta.ATTIVA);
            listaAste.add(nuovaAsta);
            nuovaAsta.setProprietario(utente);
            utente.getAste().add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, 5);

        when(astaRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(risultati);

        Page<Asta> risultatiEffettivi = astaService.getAsteUtenteByStato(pageable, StatoAsta.ATTIVA);

        verify(astaRepositoryMock, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        assertEquals(risultati, risultatiEffettivi);

    }

    @Test
    void getAstePartecipateByUtenteTest() {
        Utente utente = new Utente();
        utente.setAste(new HashSet<>());
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);

        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());

        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setStato(StatoAsta.ATTIVA);
            listaAste.add(nuovaAsta);
            nuovaAsta.setProprietario(utente);
            utente.getAste().add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, 5);

        when(astaRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(risultati);

        Page<Asta> risultatiEffettivi = astaService.getAstePartecipateByUtente(pageable);

        verify(astaRepositoryMock, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        assertEquals(risultati, risultatiEffettivi);

    }

    @Test
    void getAsteVinteByUtenteTest() {
        Utente utente = new Utente();
        utente.setAste(new HashSet<>());
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);

        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());

        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setStato(StatoAsta.TERMINATA);
            listaAste.add(nuovaAsta);
            nuovaAsta.setProprietario(utente);
            utente.getAste().add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, 5);

        when(astaRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(risultati);

        Page<Asta> risultatiEffettivi = astaService.getAsteVinteByUtente(pageable);

        verify(astaRepositoryMock, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        assertEquals(risultati, risultatiEffettivi);

    }

    @Test
    void getAsteACuiUtenteHaPartecipatoTest() {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());

        doReturn(null).when(astaServiceSpy).getAsteVinteByUtente(pageable);
        doReturn(null).when(astaServiceSpy).getAstePartecipateByUtente(pageable);

        astaServiceSpy.getAsteACuiUtenteHaPartecipato(pageable, true);
        verify(astaServiceSpy, times(1)).getAsteVinteByUtente(pageable);

        astaServiceSpy.getAsteACuiUtenteHaPartecipato(pageable, false);
        verify(astaServiceSpy, times(1)).getAstePartecipateByUtente(pageable);
    }

    @Test
    void schedulerScadenzaAstaIngleseTest() throws SchedulerException, JsonProcessingException {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE);
        asta.setId(UUID.randomUUID());
        asta.setIntervalloTempoOfferta(30);
        Trigger triggerAsteInglese = TriggerBuilder.newTrigger()
                .withIdentity("termineAstaTrigger_" + asta.getId().toString())
                .startAt(java.util.Date.from(Instant.now().plus(asta.getIntervalloTempoOfferta(), ChronoUnit.MINUTES)))
                .build();

        astaService.schedulerScadenzaAsta(asta);

        verify(schedulerMock).scheduleJob(any(JobDetail.class), eq(triggerAsteInglese));
    }

    @Test
    void schedulerScadenzaAstaNonIngleseTest() throws SchedulerException, JsonProcessingException {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA); //valido anche per aste silenziose
        asta.setDataScadenza(OffsetDateTime.now().plusDays(5));
        asta.setId(UUID.randomUUID());
        Trigger triggerAsteInversaSilenziosa = TriggerBuilder.newTrigger()
                .withIdentity("termineAstaTrigger_" + asta.getId().toString())
                .startAt(java.util.Date.from(asta.getDataScadenza().toInstant()))
                .build();

        astaService.schedulerScadenzaAsta(asta);

        verify(schedulerMock).scheduleJob(any(JobDetail.class), eq(triggerAsteInversaSilenziosa));
    }
}
