package it.uninastudents.dietidealsservice.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import it.uninastudents.dietidealsservice.service.NotificaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class TermineAstaJobTest {

    @MockBean
    private AstaRepository astaRepositoryMock;

    @MockBean
    private OffertaRepository offertaRepositoryMock;

    @MockBean
    private NotificaService notificaServiceMock;


    @Test
    void executeAstaSenzaOfferteTest() throws Exception {
        // Crea i mock
        JobExecutionContext contextMock = mock(JobExecutionContext.class);
        JobDetail jobDetailMock = mock(JobDetail.class);
        JobDataMap jobDataMapMock = mock(JobDataMap.class);
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        Asta asta = new Asta();
        asta.setProprietario(new Utente());
        UUID idProprietario = UUID.randomUUID();
        asta.getProprietario().setId(idProprietario);
        asta.setStato(StatoAsta.TERMINATA);
        List<Offerta> listaOfferte = Collections.emptyList();
        Notifica notifica = new Notifica();


        // Configura i mock
        when(contextMock.getJobDetail()).thenReturn(jobDetailMock);
        when(jobDetailMock.getJobDataMap()).thenReturn(jobDataMapMock);
        when(jobDataMapMock.getString("asta")).thenReturn("astaJson");
        when(objectMapperMock.readValue("astaJson", Asta.class)).thenReturn(asta);
        when(astaRepositoryMock.save(asta)).thenReturn(asta);
        when(offertaRepositoryMock.findAll(any(Specification.class))).thenReturn(listaOfferte);
        when(notificaServiceMock.salvaNotifica(notifica, idProprietario)).thenReturn(notifica);

        TermineAstaJob termineAstaJob = new TermineAstaJob(objectMapperMock, offertaRepositoryMock, astaRepositoryMock, notificaServiceMock);
        termineAstaJob.execute(contextMock);


        // Verifica che i metodi siano stati chiamati sui mock
        verify(offertaRepositoryMock, times(1)).findAll(any(Specification.class));
        verify(notificaServiceMock, times(1)).salvaNotifica(any(Notifica.class), any(UUID.class));
        verify(astaRepositoryMock, times(1)).save(asta);
        verify(contextMock, times(1)).getJobDetail();
        verify(jobDetailMock, times(1)).getJobDataMap();
        verify(jobDataMapMock, times(1)).getString("asta");
        verify(objectMapperMock, times(1)).readValue("astaJson", Asta.class);
    }

    @Test
    void executeAstaNonSilenziosaConOfferteTest() throws Exception {
        // Crea i mock
        JobExecutionContext contextMock = mock(JobExecutionContext.class);
        JobDetail jobDetailMock = mock(JobDetail.class);
        JobDataMap jobDataMapMock = mock(JobDataMap.class);
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.INGLESE);
        asta.setProprietario(new Utente());
        UUID idProprietario = UUID.randomUUID();
        asta.getProprietario().setId(idProprietario);
        asta.setStato(StatoAsta.TERMINATA);
        List<Offerta> listaOfferte = new ArrayList<>();
        Offerta offertaVincente = new Offerta();
        offertaVincente.setUtente(new Utente());
        offertaVincente.setStato(StatoOfferta.VINCENTE);
        offertaVincente.setPrezzo(BigDecimal.valueOf(100));
        Offerta offertaNonVincente = new Offerta();
        offertaNonVincente.setUtente(new Utente());
        offertaNonVincente.setPrezzo(BigDecimal.valueOf(90));
        offertaNonVincente.setStato(StatoOfferta.NON_VINCENTE);
        listaOfferte.add(offertaVincente);
        listaOfferte.add(offertaNonVincente);
        Notifica notifica = new Notifica();
        ArgumentCaptor<Notifica> notificaCaptor = ArgumentCaptor.forClass(Notifica.class);
        ArgumentCaptor<UUID> uuidCaptor = ArgumentCaptor.forClass(UUID.class);


        // Configura i mock
        when(contextMock.getJobDetail()).thenReturn(jobDetailMock);
        when(jobDetailMock.getJobDataMap()).thenReturn(jobDataMapMock);
        when(jobDataMapMock.getString("asta")).thenReturn("astaJson");
        when(objectMapperMock.readValue("astaJson", Asta.class)).thenReturn(asta);
        when(astaRepositoryMock.save(asta)).thenReturn(asta);
        when(offertaRepositoryMock.findAll(any(Specification.class))).thenReturn(listaOfferte);
        when(notificaServiceMock.salvaNotifica(any(Notifica.class), any(UUID.class))).thenReturn(notifica);

        TermineAstaJob termineAstaJob = new TermineAstaJob(objectMapperMock, offertaRepositoryMock, astaRepositoryMock, notificaServiceMock);
        termineAstaJob.execute(contextMock);


        // Verifica che i metodi siano stati chiamati sui mock
        verify(offertaRepositoryMock, times(1)).findAll(any(Specification.class));
        verify(notificaServiceMock, times(listaOfferte.size() + 1)).salvaNotifica(notificaCaptor.capture(), uuidCaptor.capture());
        verify(astaRepositoryMock, times(1)).save(asta);
        verify(contextMock, times(1)).getJobDetail();
        verify(jobDetailMock, times(1)).getJobDataMap();
        verify(jobDataMapMock, times(1)).getString("asta");
        verify(objectMapperMock, times(1)).readValue("astaJson", Asta.class);
    }

    @Test
    void executeAstaSilenziosaConOfferteTest() throws Exception {
        // Crea i mock
        JobExecutionContext contextMock = mock(JobExecutionContext.class);
        JobDetail jobDetailMock = mock(JobDetail.class);
        JobDataMap jobDataMapMock = mock(JobDataMap.class);
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        Asta asta = new Asta();
        asta.setTipo(TipoAsta.SILENZIOSA);
        asta.setProprietario(new Utente());
        UUID idProprietario = UUID.randomUUID();
        asta.getProprietario().setId(idProprietario);
        asta.setStato(StatoAsta.TERMINATA);
        List<Offerta> listaOfferte = new ArrayList<>();
        Offerta offertaVincente = new Offerta();
        offertaVincente.setUtente(new Utente());
        offertaVincente.setStato(StatoOfferta.VINCENTE);
        offertaVincente.setPrezzo(BigDecimal.valueOf(100));
        Offerta offertaNonVincente = new Offerta();
        offertaNonVincente.setUtente(new Utente());
        offertaNonVincente.setPrezzo(BigDecimal.valueOf(90));
        offertaNonVincente.setStato(StatoOfferta.NON_VINCENTE);
        listaOfferte.add(offertaVincente);
        listaOfferte.add(offertaNonVincente);
        Notifica notifica = new Notifica();
        ArgumentCaptor<Notifica> notificaCaptor = ArgumentCaptor.forClass(Notifica.class);
        ArgumentCaptor<UUID> uuidCaptor = ArgumentCaptor.forClass(UUID.class);


        // Configura i mock
        when(contextMock.getJobDetail()).thenReturn(jobDetailMock);
        when(jobDetailMock.getJobDataMap()).thenReturn(jobDataMapMock);
        when(jobDataMapMock.getString("asta")).thenReturn("astaJson");
        when(objectMapperMock.readValue("astaJson", Asta.class)).thenReturn(asta);
        when(astaRepositoryMock.save(asta)).thenReturn(asta);
        when(offertaRepositoryMock.save(any(Offerta.class))).thenReturn(null);
        when(offertaRepositoryMock.findAll(any(Specification.class))).thenReturn(listaOfferte);
        when(notificaServiceMock.salvaNotifica(any(Notifica.class), any(UUID.class))).thenReturn(notifica);

        TermineAstaJob termineAstaJob = new TermineAstaJob(objectMapperMock, offertaRepositoryMock, astaRepositoryMock, notificaServiceMock);
        termineAstaJob.execute(contextMock);


        // Verifica che i metodi siano stati chiamati sui mock
        verify(offertaRepositoryMock, times(1)).findAll(any(Specification.class));
        verify(offertaRepositoryMock, times(1)).save(any(Offerta.class));
        verify(notificaServiceMock, times(listaOfferte.size() + 1)).salvaNotifica(notificaCaptor.capture(), uuidCaptor.capture());
        verify(astaRepositoryMock, times(1)).save(asta);
        verify(contextMock, times(1)).getJobDetail();
        verify(jobDetailMock, times(1)).getJobDataMap();
        verify(jobDataMapMock, times(1)).getString("asta");
        verify(objectMapperMock, times(1)).readValue("astaJson", Asta.class);
    }

    @Test
    void executeAstaErroreConversioneTest() {
        // Crea i mock
        JobExecutionContext contextMock = mock(JobExecutionContext.class);
        JobDetail jobDetailMock = mock(JobDetail.class);
        JobDataMap jobDataMapMock = mock(JobDataMap.class);
        ObjectMapper objectMapper = new ObjectMapper();


        // Configura i mock
        when(contextMock.getJobDetail()).thenReturn(jobDetailMock);
        when(jobDetailMock.getJobDataMap()).thenReturn(jobDataMapMock);
        when(jobDataMapMock.getString("asta")).thenReturn("astaJson");

        TermineAstaJob termineAstaJob = new TermineAstaJob(objectMapper, offertaRepositoryMock, astaRepositoryMock, notificaServiceMock);
        assertThrows(IllegalArgumentException.class, () -> termineAstaJob.execute(contextMock));


        // Verifica che i metodi siano stati chiamati sui mock
        verify(contextMock, times(1)).getJobDetail();
        verify(jobDetailMock, times(1)).getJobDataMap();
        verify(jobDataMapMock, times(1)).getString("asta");
    }

}
