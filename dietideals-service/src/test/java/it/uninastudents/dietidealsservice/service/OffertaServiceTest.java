package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import it.uninastudents.dietidealsservice.utils.NotificaUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@SuppressWarnings("unchecked")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class OffertaServiceTest {

    @Autowired
    private OffertaService offertaService;

    @MockBean
    private OffertaRepository offertaRepositoryMock;

    @MockBean
    private Scheduler schedulerMock;

    @MockBean
    private AstaRepository astaRepositoryMock;

    @MockBean
    private UtenteService utenteServiceMock;

    @MockBean
    private NotificaService notificaServiceMock;

    @SpyBean
    private OffertaService offertaServiceSpy;

    @Test
    void gestisciOffertaVincenteTest() {
        Asta asta = new Asta();
        Offerta nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(BigDecimal.valueOf(100));
        Offerta offertaVincente = new Offerta();
        offertaVincente.setUtente(new Utente());
        offertaVincente.getUtente().setId(UUID.randomUUID());
        offertaVincente.setStato(StatoOfferta.NON_VINCENTE);
        Notifica notifica = new Notifica();
        notifica.setAsta(asta);
        notifica.setContenuto(NotificaUtils.buildMessaggioOffertaSuperata(asta.getNome(), nuovaOfferta.getPrezzo()));
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        utente.setNotifiche(new HashSet<>());

        doReturn(true).when(offertaServiceSpy).confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);
        when(offertaRepositoryMock.save(offertaVincente)).thenReturn(offertaVincente);
        when(notificaServiceMock.salvaNotifica(notifica, offertaVincente.getUtente().getId())).thenReturn(notifica);

        offertaServiceSpy.gestisciOffertaVincente(Optional.of(offertaVincente), utente, nuovaOfferta, asta);

        verify(offertaServiceSpy, times(1)).confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);
        verify(offertaRepositoryMock, times(1)).save(offertaVincente);
    }

    @Test
    void gestisciOffertaVincenteOfferteConsecutiveTest() {
        Asta asta = new Asta();
        Offerta nuovaOfferta = new Offerta();
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        Offerta offertaVincente = new Offerta();
        offertaVincente.setUtente(utente);
        Optional<Offerta> optionalOfferta = Optional.of(offertaVincente);

        assertThrows(IllegalArgumentException.class, () -> offertaService.gestisciOffertaVincente(optionalOfferta, utente, nuovaOfferta, asta));
    }

    @Test
    void gestisciOffertaVincentePrezzoNonValidoTest() {
        Asta asta = new Asta();
        Offerta nuovaOfferta = new Offerta();
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        Offerta offertaVincente = new Offerta();
        offertaVincente.setUtente(new Utente());
        offertaVincente.getUtente().setId(UUID.randomUUID());
        Optional<Offerta> optionalOfferta = Optional.of(offertaVincente);

        doReturn(false).when(offertaServiceSpy).confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertThrows(IllegalArgumentException.class, () -> offertaService.gestisciOffertaVincente(optionalOfferta, utente, nuovaOfferta, asta));

        verify(offertaServiceSpy, times(1)).confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

    }

    @Test
    void gestisciOffertaVincenteNonPresenteTest() {
        Asta asta = new Asta();
        Offerta nuovaOfferta = new Offerta();
        Utente utente = new Utente();
        Optional<Offerta> optionalOfferta = Optional.empty();

        offertaService.gestisciOffertaVincente(optionalOfferta, utente, nuovaOfferta, asta);

        verify(offertaRepositoryMock, never()).save(any(Offerta.class));
        verify(notificaServiceMock, never()).salvaNotifica(any(Notifica.class), any(UUID.class));
    }

    @Test
    void findOffertaNonRifiutataByUtenteAstaSilenziosaTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.SILENZIOSA);
        Offerta offerta = new Offerta();
        UUID idUtente = UUID.randomUUID();

        when(offertaRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(offerta));

        Optional<Offerta> risultato = offertaService.findOffertaNonRifiutataByUtenteAstaSilenziosa(idUtente, asta);

        verify(offertaRepositoryMock, times(1)).findOne(any(Specification.class));
        risultato.ifPresent(value -> assertEquals(offerta, value));
    }

    @Test
    void findOffertaNonRifiutataByUtenteAstaSilenziosaAstaNonSilenziosaTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE);  //qualsiasi asta non silenziosa vale per il test
        UUID idUtente = UUID.randomUUID();

        Optional<Offerta> risultato = offertaService.findOffertaNonRifiutataByUtenteAstaSilenziosa(idUtente, asta);

        assertEquals(Optional.empty(), risultato);
    }

    @Test
    void controlloOfferteUtenteAstaSilenziosaTest() {
        UUID idUtente = UUID.randomUUID();
        Asta asta = new Asta();
        doReturn(Optional.of(new Offerta())).when(offertaServiceSpy).findOffertaNonRifiutataByUtenteAstaSilenziosa(idUtente, asta);

        boolean risultato = offertaServiceSpy.controlloOfferteUtenteAstaSilenziosa(idUtente, asta);

        verify(offertaServiceSpy, times(1)).controlloOfferteUtenteAstaSilenziosa(idUtente, asta);
        assertTrue(risultato);

    }

    @Test
    void controlloOfferteUtenteAstaSilenziosaNonTrovataTest() {
        UUID idUtente = UUID.randomUUID();
        Asta asta = new Asta();
        doReturn(Optional.empty()).when(offertaServiceSpy).findOffertaNonRifiutataByUtenteAstaSilenziosa(idUtente, asta);

        boolean risultato = offertaServiceSpy.controlloOfferteUtenteAstaSilenziosa(idUtente, asta);

        verify(offertaServiceSpy, times(1)).controlloOfferteUtenteAstaSilenziosa(idUtente, asta);
        assertFalse(risultato);

    }

    @Test
    void findOffertaByStatoNonVincenteTest() {
        StatoOfferta statoOfferta = StatoOfferta.NON_VINCENTE;
        List<Offerta> listaOfferte = new ArrayList<>();
        UUID idAsta = UUID.randomUUID();
        doReturn(listaOfferte).when(offertaServiceSpy).findAllByAsta(idAsta);

        List<Offerta> risultati = offertaServiceSpy.findOffertaByStato(idAsta, statoOfferta);

        verify(offertaServiceSpy, times(1)).findAllByAsta(idAsta);
        assertEquals(listaOfferte, risultati);
    }

    @Test
    void findOffertaByStatoVincenteTest() {
        StatoOfferta statoOfferta = StatoOfferta.VINCENTE;
        List<Offerta> listaOfferte = new ArrayList<>();
        UUID idAsta = UUID.randomUUID();
        doReturn(listaOfferte).when(offertaServiceSpy).findListOffertaVincenteByAsta(idAsta);

        List<Offerta> risultati = offertaServiceSpy.findOffertaByStato(idAsta, statoOfferta);

        verify(offertaServiceSpy, times(1)).findListOffertaVincenteByAsta(idAsta);
        assertEquals(listaOfferte, risultati);
    }

    @Test
    void findOffertaByStatoNonValidoTest() {
        StatoOfferta statoOfferta = StatoOfferta.RIFIUTATA;
        List<Offerta> listaOfferte = Collections.emptyList();
        UUID idAsta = UUID.randomUUID();

        List<Offerta> risultati = offertaServiceSpy.findOffertaByStato(idAsta, statoOfferta);

        assertEquals(listaOfferte, risultati);
    }

    @Test
    void findAllByAstaTest() {
        UUID idAsta = UUID.randomUUID();
        Optional<Asta> asta = Optional.of(new Asta());
        asta.get().setId(idAsta);
        List<Offerta> listaOfferte = new ArrayList<>();

        when(astaRepositoryMock.findById(idAsta)).thenReturn(asta);
        when(offertaRepositoryMock.findAll(any(Specification.class))).thenReturn(listaOfferte);

        List<Offerta> risultati = offertaService.findAllByAsta(idAsta);

        verify(astaRepositoryMock, times(1)).findById(idAsta);
        verify(offertaRepositoryMock, times(1)).findAll(any(Specification.class));

        assertEquals(listaOfferte, risultati);
    }

    @Test
    void findAllByAstaNonTrovataTest() {
        UUID idAsta = UUID.randomUUID();
        List<Offerta> listaOfferte = Collections.emptyList();

        when(astaRepositoryMock.findById(idAsta)).thenReturn(Optional.empty());

        List<Offerta> risultati = offertaService.findAllByAsta(idAsta);

        verify(astaRepositoryMock, times(1)).findById(idAsta);
        assertEquals(listaOfferte, risultati);
    }

    @Test
    void findListOffertaVincenteByAstaTest() {
        List<Offerta> listaOfferte = new ArrayList<>();
        UUID idAsta = UUID.randomUUID();

        when(offertaRepositoryMock.findAll(any(Specification.class))).thenReturn(listaOfferte);

        List<Offerta> risultati = offertaService.findListOffertaVincenteByAsta(idAsta);

        verify(offertaRepositoryMock, times(1)).findAll(any(Specification.class));
        assertEquals(listaOfferte, risultati);
    }

    @Test
    void findOptionalOffertaVincenteByAstaTest() {
        Optional<Offerta> offerta = Optional.of(new Offerta());
        UUID idAsta = UUID.randomUUID();

        when(offertaRepositoryMock.findOne(any(Specification.class))).thenReturn(offerta);

        Optional<Offerta> risultato = offertaService.findOptionalOffertaVincenteByAsta(idAsta);

        verify(offertaRepositoryMock, times(1)).findOne(any(Specification.class));
        assertEquals(offerta, risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaInversaPrezzoMinoreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA);
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(100);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaInversaPrezzoUgualeTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA);
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(110);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaInversaPrezzoMaggioreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA);
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(120);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertFalse(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaSilenziosaPrezzoMinoreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.SILENZIOSA);
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(100);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertFalse(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaSilenziosaPrezzoUgualeTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.SILENZIOSA);
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(110);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaSilenziosaPrezzoMaggioreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.SILENZIOSA);
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(120);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaInglesePrezzoMinoreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE);
        asta.setSogliaRialzo(BigDecimal.valueOf(10));
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(100);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertFalse(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaInglesePrezzoUgualeTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE);
        asta.setSogliaRialzo(BigDecimal.valueOf(10));
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(120);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoOffertaConPrezzoBaseAstaInglesePrezzoMaggioreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE);
        asta.setSogliaRialzo(BigDecimal.valueOf(10));
        asta.setPrezzoBase(BigDecimal.valueOf(110));
        BigDecimal prezzo = BigDecimal.valueOf(130);

        boolean risultato = offertaService.confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoNuovaOffertaConPrezzoOffertaVincenteAstaInglesePrezzoMaggioreTest() {
        Asta asta = new Asta();
        asta.setSogliaRialzo(BigDecimal.valueOf(10));
        asta.setTipo(TipoAsta.INGLESE);
        Offerta nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(BigDecimal.valueOf(120));
        Offerta offertaVincente = new Offerta();
        offertaVincente.setPrezzo(BigDecimal.valueOf(100));

        boolean risultato = offertaService.confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoNuovaOffertaConPrezzoOffertaVincenteAstaInglesePrezzoUgualeTest() {
        Asta asta = new Asta();
        asta.setSogliaRialzo(BigDecimal.valueOf(10));
        asta.setTipo(TipoAsta.INGLESE);
        Offerta nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(BigDecimal.valueOf(110));
        Offerta offertaVincente = new Offerta();
        offertaVincente.setPrezzo(BigDecimal.valueOf(100));

        boolean risultato = offertaService.confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoNuovaOffertaConPrezzoOffertaVincenteAstaInglesePrezzoMinoreTest() {
        Asta asta = new Asta();
        asta.setSogliaRialzo(BigDecimal.valueOf(10));
        asta.setTipo(TipoAsta.INGLESE);
        Offerta nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(BigDecimal.valueOf(100));
        Offerta offertaVincente = new Offerta();
        offertaVincente.setPrezzo(BigDecimal.valueOf(100));

        boolean risultato = offertaService.confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertFalse(risultato);
    }

    @Test
    void confrontaPrezzoNuovaOffertaConPrezzoOffertaVincenteAstaInversaPrezzoMaggioreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA);
        Offerta nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(BigDecimal.valueOf(120));
        Offerta offertaVincente = new Offerta();
        offertaVincente.setPrezzo(BigDecimal.valueOf(100));

        boolean risultato = offertaService.confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertFalse(risultato);
    }

    @Test
    void confrontaPrezzoNuovaOffertaConPrezzoOffertaVincenteAstaInversaPrezzoUgualeTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA);
        Offerta nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(BigDecimal.valueOf(120));
        Offerta offertaVincente = new Offerta();
        offertaVincente.setPrezzo(BigDecimal.valueOf(120));

        boolean risultato = offertaService.confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoNuovaOffertaConPrezzoOffertaVincenteAstaInversaPrezzoMinoreTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INVERSA);
        Offerta nuovaOfferta = new Offerta();
        nuovaOfferta.setPrezzo(BigDecimal.valueOf(90));
        Offerta offertaVincente = new Offerta();
        offertaVincente.setPrezzo(BigDecimal.valueOf(100));

        boolean risultato = offertaService.confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertTrue(risultato);
    }

    @Test
    void confrontaPrezzoNuovaOffertaConPrezzoOffertaVincenteAstaSilenziosaTest() {
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.SILENZIOSA);
        Offerta nuovaOfferta = new Offerta();
        Offerta offertaVincente = new Offerta();

        boolean risultato = offertaService.confrontaPrezzoNuovaOffertaConPrezzoOffertaVincente(nuovaOfferta, offertaVincente, asta);

        assertFalse(risultato);
    }

    @Test
    void modificaStatoOffertaOffertaNonTrovataTest() throws SchedulerException {

        when(offertaRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.empty());

        Offerta risultato = offertaService.modificaStatoOfferta(any(UUID.class), any(StatoOfferta.class));

        verify(offertaRepositoryMock, times(1)).findOne(any(Specification.class));
        assertNull(risultato);
    }

    @Test
    void modificaStatoOffertaOffertaNonVincenteTest() throws SchedulerException {
        Offerta offerta = new Offerta();
        offerta.setId(UUID.randomUUID());
        StatoOfferta statoOfferta = StatoOfferta.NON_VINCENTE;
        offerta.setStato(statoOfferta);

        when(offertaRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(offerta));
        when(offertaRepositoryMock.save(offerta)).thenReturn(offerta);

        Offerta risultato = offertaService.modificaStatoOfferta(offerta.getId(), statoOfferta);

        verify(offertaRepositoryMock, times(1)).findOne(any(Specification.class));
        verify(offertaRepositoryMock, times(1)).save(offerta);

        assertEquals(offerta, risultato);
    }

    @Test
    void modificaStatoOffertaOffertaAstaNonSilenziosaTest() throws SchedulerException {
        Offerta offerta = new Offerta();
        offerta.setId(UUID.randomUUID());
        StatoOfferta statoOfferta = StatoOfferta.VINCENTE;
        offerta.setStato(statoOfferta);
        offerta.setAsta(new Asta());
        offerta.getAsta().setTipo(TipoAsta.INGLESE); //qualsiasi asta non silenziosa è valida per il test

        when(offertaRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(offerta));
        when(offertaRepositoryMock.save(offerta)).thenReturn(offerta);

        Offerta risultato = offertaService.modificaStatoOfferta(offerta.getId(), statoOfferta);

        verify(offertaRepositoryMock, times(1)).findOne(any(Specification.class));
        verify(offertaRepositoryMock, times(1)).save(offerta);

        assertEquals(offerta, risultato);
    }

    @Test
    void modificaStatoOffertaOffertaTest() throws SchedulerException {
        Offerta offerta = new Offerta();
        offerta.setId(UUID.randomUUID());
        StatoOfferta statoOfferta = StatoOfferta.VINCENTE;
        offerta.setStato(statoOfferta);
        offerta.setAsta(new Asta());
        offerta.getAsta().setTipo(TipoAsta.SILENZIOSA);
        offerta.getAsta().setId(UUID.randomUUID());

        String nuovoNomeJob = "termineAstaJob_" + offerta.getAsta().getId().toString();
        String nuovoGruppoJob = "termineAsta";
        Trigger nuovoTrigger = TriggerBuilder.newTrigger().withIdentity("termineAstaTrigger_" + offerta.getAsta().getId().toString()).startNow().build();

        when(offertaRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(offerta));
        when(offertaRepositoryMock.save(offerta)).thenReturn(offerta);
        doNothing().when(offertaServiceSpy).modificaTriggerJobTermineAsta(nuovoNomeJob, nuovoGruppoJob, nuovoTrigger);

        Offerta risultato = offertaServiceSpy.modificaStatoOfferta(offerta.getId(), statoOfferta);

        verify(offertaRepositoryMock, times(1)).findOne(any(Specification.class));
        verify(offertaRepositoryMock, times(1)).save(offerta);
        verify(offertaServiceSpy, times(1)).modificaTriggerJobTermineAsta(nuovoNomeJob, nuovoGruppoJob, nuovoTrigger);

        assertEquals(offerta, risultato);
    }

    @Test
    void modificaTriggerJobTermineAstaTest() throws SchedulerException {
        JobDetail jobDetailMock = mock(JobDetail.class);
        Trigger triggerMock = mock(Trigger.class);

        when(schedulerMock.getJobDetail(any(JobKey.class))).thenReturn(jobDetailMock);
        when(schedulerMock.deleteJob(any(JobKey.class))).thenReturn(true);
        when(schedulerMock.scheduleJob(any(JobDetail.class), eq(triggerMock))).thenReturn(null);

        offertaService.modificaTriggerJobTermineAsta("nomeJob", "gruppoJob", triggerMock);

        verify(schedulerMock, times(1)).getJobDetail(any(JobKey.class));
        verify(schedulerMock, times(1)).deleteJob(any(JobKey.class));
        verify(schedulerMock, times(1)).scheduleJob(any(JobDetail.class), eq(triggerMock));
    }

    @Test
    void modificaTriggerJobTermineAstaJobNonTrovatoTest() throws SchedulerException {
        Trigger triggerMock = mock(Trigger.class);

        when(schedulerMock.getJobDetail(any(JobKey.class))).thenReturn(null);

        assertThrows(SchedulerException.class, () -> offertaService.modificaTriggerJobTermineAsta("nomeJob", "gruppoJob", triggerMock));

        verify(schedulerMock, times(1)).getJobDetail(any(JobKey.class));
    }

    @Test
    void salvaOffertaAstaNonTrovataTest() {
        Utente utente = new Utente();
        UUID idAsta = UUID.randomUUID();
        BigDecimal prezzo = BigDecimal.valueOf(100);
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> offertaService.salvaOfferta(idAsta, prezzo));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
    }

    @Test
    void salvaOffertaProprietarioOfferenteTest() {
        Utente utente = new Utente();
        Asta asta = new Asta();
        asta.setStato(StatoAsta.ATTIVA);
        UUID idAsta = UUID.randomUUID();
        asta.setProprietario(utente);
        asta.setId(idAsta);
        BigDecimal prezzo = BigDecimal.valueOf(100);
        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(asta));

        assertThrows(IllegalArgumentException.class, () -> offertaService.salvaOfferta(idAsta, prezzo));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
    }

    @Test
    void salvaOffertaAstaTerminataTest() {
        Utente utente = new Utente();
        Asta asta = new Asta();
        asta.setStato(StatoAsta.TERMINATA);
        UUID idAsta = UUID.randomUUID();
        asta.setId(idAsta);
        BigDecimal prezzo = BigDecimal.valueOf(100);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(asta));

        assertThrows(IllegalArgumentException.class, () -> offertaService.salvaOfferta(idAsta, prezzo));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
    }

    @Test
    void salvaOffertaPrezzoNonValidoTest() {
        Utente utente = new Utente();
        Asta asta = new Asta();
        asta.setStato(StatoAsta.ATTIVA);
        UUID idAsta = UUID.randomUUID();
        asta.setId(idAsta);
        BigDecimal prezzo = BigDecimal.valueOf(100);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(asta));
        doReturn(false).when(offertaServiceSpy).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);

        assertThrows(IllegalArgumentException.class, () -> offertaServiceSpy.salvaOfferta(idAsta, prezzo));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(offertaServiceSpy, times(1)).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
    }

    @Test
    void salvaOffertaConsecutivaAstaSilenziosaTest() {
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        Asta asta = new Asta();
        asta.setStato(StatoAsta.ATTIVA);
        asta.setTipo(TipoAsta.SILENZIOSA);
        UUID idAsta = UUID.randomUUID();
        asta.setId(idAsta);
        BigDecimal prezzo = BigDecimal.valueOf(100);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(asta));
        doReturn(true).when(offertaServiceSpy).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        doReturn(true).when(offertaServiceSpy).controlloOfferteUtenteAstaSilenziosa(utente.getId(), asta);

        assertThrows(IllegalArgumentException.class, () -> offertaServiceSpy.salvaOfferta(idAsta, prezzo));

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(offertaServiceSpy, times(1)).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
        verify(offertaServiceSpy, times(1)).controlloOfferteUtenteAstaSilenziosa(utente.getId(), asta);

    }

    @Test
    void salvaOffertaAstaSilenziosaTest() throws SchedulerException {
        BigDecimal prezzo = BigDecimal.valueOf(100);
        Offerta offerta = new Offerta();
        offerta.setPrezzo(prezzo);
        offerta.setStato(StatoOfferta.NON_VINCENTE);
        Asta asta = new Asta();
        asta.setOfferte(new HashSet<>());
        asta.setStato(StatoAsta.ATTIVA);
        asta.setTipo(TipoAsta.SILENZIOSA);
        UUID idAsta = UUID.randomUUID();
        asta.setId(idAsta);
        offerta.setAsta(asta);
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        utente.setOfferte(new HashSet<>());
        offerta.setUtente(utente);

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(asta));
        doReturn(true).when(offertaServiceSpy).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        doReturn(false).when(offertaServiceSpy).controlloOfferteUtenteAstaSilenziosa(utente.getId(), asta);
        when(offertaRepositoryMock.save(any(Offerta.class))).thenReturn(offerta);

        Offerta risultato = offertaService.salvaOfferta(idAsta, prezzo);

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(offertaServiceSpy, times(1)).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
        verify(offertaServiceSpy, times(1)).controlloOfferteUtenteAstaSilenziosa(utente.getId(), asta);
        verify(offertaRepositoryMock, times(1)).save(any(Offerta.class));
        assertEquals(offerta, risultato);

    }

    @Test
    void salvaOffertaAstaInversaTest() throws SchedulerException {
        BigDecimal prezzo = BigDecimal.valueOf(100);
        Offerta offerta = new Offerta();
        offerta.setPrezzo(prezzo);
        offerta.setStato(StatoOfferta.VINCENTE);
        Asta asta = new Asta();
        asta.setOfferte(new HashSet<>());
        asta.setStato(StatoAsta.ATTIVA);
        asta.setTipo(TipoAsta.INVERSA);
        UUID idAsta = UUID.randomUUID();
        asta.setId(idAsta);
        offerta.setAsta(asta);
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        utente.setOfferte(new HashSet<>());
        offerta.setUtente(utente);
        Optional<Offerta> offertaVincente = Optional.of(new Offerta());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(asta));
        doReturn(offertaVincente).when(offertaServiceSpy).findOptionalOffertaVincenteByAsta(idAsta);
        doReturn(true).when(offertaServiceSpy).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        doNothing().when(offertaServiceSpy).gestisciOffertaVincente(any(Optional.class), any(Utente.class), any(Offerta.class), any(Asta.class));
        when(offertaRepositoryMock.save(any(Offerta.class))).thenReturn(offerta);

        Offerta risultato = offertaServiceSpy.salvaOfferta(idAsta, prezzo);

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(offertaServiceSpy, times(1)).findOptionalOffertaVincenteByAsta(idAsta);
        verify(offertaServiceSpy, times(1)).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
        verify(offertaServiceSpy, times(1)).gestisciOffertaVincente(any(Optional.class), any(Utente.class), any(Offerta.class), any(Asta.class));
        verify(offertaRepositoryMock, times(1)).save(any(Offerta.class));
        assertEquals(offerta, risultato);

    }

    @Test
    void salvaOffertaAstaIngleseTest() throws SchedulerException {
        BigDecimal prezzo = BigDecimal.valueOf(100);
        Offerta offerta = new Offerta();
        offerta.setPrezzo(prezzo);
        offerta.setStato(StatoOfferta.VINCENTE);
        Asta asta = new Asta();
        asta.setIntervalloTempoOfferta(30);
        asta.setOfferte(new HashSet<>());
        asta.setStato(StatoAsta.ATTIVA);
        asta.setTipo(TipoAsta.INGLESE);
        UUID idAsta = UUID.randomUUID();
        asta.setId(idAsta);
        offerta.setAsta(asta);
        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        utente.setOfferte(new HashSet<>());
        offerta.setUtente(utente);
        Optional<Offerta> offertaVincente = Optional.of(new Offerta());

        when(utenteServiceMock.getUtenteAutenticato()).thenReturn(utente);
        when(astaRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(asta));
        doReturn(offertaVincente).when(offertaServiceSpy).findOptionalOffertaVincenteByAsta(idAsta);
        doReturn(true).when(offertaServiceSpy).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        doNothing().when(offertaServiceSpy).modificaTriggerJobTermineAsta("termineAstaJob_" + asta.getId().toString(), "termineAsta",
                TriggerBuilder.newTrigger()
                        .withIdentity("termineAstaTrigger_" + asta.getId().toString())
                        .startAt(java.util.Date.from(Instant.now().plus(asta.getIntervalloTempoOfferta(), ChronoUnit.MINUTES)))
                        .build());
        doNothing().when(offertaServiceSpy).gestisciOffertaVincente(any(Optional.class), any(Utente.class), any(Offerta.class), any(Asta.class));
        when(offertaRepositoryMock.save(any(Offerta.class))).thenReturn(offerta);

        Offerta risultato = offertaServiceSpy.salvaOfferta(idAsta, prezzo);

        verify(utenteServiceMock, times(1)).getUtenteAutenticato();
        verify(offertaServiceSpy, times(1)).findOptionalOffertaVincenteByAsta(idAsta);
        verify(offertaServiceSpy, times(1)).confrontaPrezzoOffertaConPrezzoBaseAsta(prezzo, asta);
        verify(astaRepositoryMock, times(1)).findById(any(UUID.class));
        verify(offertaServiceSpy, times(1)).modificaTriggerJobTermineAsta("termineAstaJob_" + asta.getId().toString(), "termineAsta",
                TriggerBuilder.newTrigger()
                        .withIdentity("termineAstaTrigger_" + asta.getId().toString())
                        .startAt(java.util.Date.from(Instant.now().plus(asta.getIntervalloTempoOfferta(), ChronoUnit.MINUTES)))
                        .build());
        verify(offertaServiceSpy, times(1)).gestisciOffertaVincente(any(Optional.class), any(Utente.class), any(Offerta.class), any(Asta.class));
        verify(offertaRepositoryMock, times(1)).save(any(Offerta.class));
        assertEquals(offerta, risultato);

    }
}

