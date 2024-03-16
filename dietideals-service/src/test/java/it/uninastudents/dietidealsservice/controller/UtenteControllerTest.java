package it.uninastudents.dietidealsservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.dto.DatiProfiloUtente;
import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import it.uninastudents.dietidealsservice.model.mapper.UtenteMapper;
import it.uninastudents.dietidealsservice.service.UtenteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class UtenteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteService utenteServiceMock;

    @Autowired
    private UtenteMapper utenteMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveUtenteDatiCorrettiTest() throws Exception {
        UtenteRegistrazione datiNuovoUtente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(UtenteRegistrazione.class);
        datiNuovoUtente.setPartitaIva(null);
        datiNuovoUtente.setDataNascita(LocalDate.now().minusYears(1));
        datiNuovoUtente.setContoCorrente(null);
        datiNuovoUtente.setEmail("test.test@test.com");

        Utente utenteRisultato = utenteMapper.utenteRegistrazioneToUtente(datiNuovoUtente);
        UUID idUtenteRisultato = UUID.randomUUID();
        utenteRisultato.setIdAuth("abcdefghilmnopqrstuvz1234567");
        utenteRisultato.setId(idUtenteRisultato);

        when(utenteServiceMock.registraUtente(any(UtenteRegistrazione.class), eq(null))).thenReturn(utenteRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/registrazione")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(datiNuovoUtente))).andReturn();

        verify(utenteServiceMock, times(1)).registraUtente(any(UtenteRegistrazione.class), eq(null));
        assertEquals(201, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(utenteRisultato), mvcResult.getResponse().getContentAsString());
        assertEquals("/registrazione/" + idUtenteRisultato, mvcResult.getResponse().getHeader("Location"));
    }

    @Test
    void saveUtenteDatiNonCorrettiTest() throws Exception {
        UtenteRegistrazione datiNuovoUtente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(UtenteRegistrazione.class);
        datiNuovoUtente.setPartitaIva("123456789011"); //dato errato
        datiNuovoUtente.setDataNascita(LocalDate.now().minusYears(1));
        datiNuovoUtente.setContoCorrente(null);
        datiNuovoUtente.setEmail("test.test@test.com");
        System.out.println(objectMapper.writeValueAsString(datiNuovoUtente));

        Utente utenteRisultato = utenteMapper.utenteRegistrazioneToUtente(datiNuovoUtente);
        UUID idUtenteRisultato = UUID.randomUUID();
        utenteRisultato.setIdAuth("abcdefghilmnopqrstuvz1234567");
        utenteRisultato.setId(idUtenteRisultato);

        when(utenteServiceMock.registraUtente(any(UtenteRegistrazione.class), eq(null))).thenReturn(utenteRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/registrazione")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(datiNuovoUtente))).andReturn();

        verify(utenteServiceMock, times(0)).registraUtente(any(UtenteRegistrazione.class), eq(null));
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void saveUtenteNonValidoTest() throws Exception {
        UtenteRegistrazione datiNuovoUtente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(UtenteRegistrazione.class);
        datiNuovoUtente.setPartitaIva(null);
        datiNuovoUtente.setDataNascita(LocalDate.now().minusYears(1));
        datiNuovoUtente.setContoCorrente(null);
        datiNuovoUtente.setEmail("test.test@test.com");
        System.out.println(objectMapper.writeValueAsString(datiNuovoUtente));

        Utente utenteRisultato = utenteMapper.utenteRegistrazioneToUtente(datiNuovoUtente);
        UUID idUtenteRisultato = UUID.randomUUID();
        utenteRisultato.setIdAuth("abcdefghilmnopqrstuvz1234567");
        utenteRisultato.setId(idUtenteRisultato);

        when(utenteServiceMock.registraUtente(any(UtenteRegistrazione.class), eq(null))).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/registrazione")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(datiNuovoUtente))).andReturn();

        verify(utenteServiceMock, times(1)).registraUtente(any(UtenteRegistrazione.class), eq(null));
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void modifyDatiUtenteDatiCorretti() throws Exception {
        DatiProfiloUtente nuovoDatiProfiloUtente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(DatiProfiloUtente.class);
        nuovoDatiProfiloUtente.setDescrizione("descrizione");

        Utente utenteModificato = utenteMapper.datiProfiloUtenteToUtente(nuovoDatiProfiloUtente);
        UUID idUtenteModificato = UUID.randomUUID();
        utenteModificato.setId(idUtenteModificato);
        utenteModificato.setIdAuth("abcdefghilmnopqrstuvz1234567");

        when(utenteServiceMock.modificaDatiUtente(any(DatiProfiloUtente.class))).thenReturn(utenteModificato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/utente/modificaDatiUtente")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovoDatiProfiloUtente))).andReturn();

        verify(utenteServiceMock, times(1)).modificaDatiUtente(any(DatiProfiloUtente.class));
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(utenteModificato), mvcResult.getResponse().getContentAsString());
        assertEquals("/utente/" + utenteModificato.getId() + "/datiUtente", mvcResult.getResponse().getHeader("Location"));
    }

    @Test
    void modifyDatiUtenteDatiNonCorretti() throws Exception {
        DatiProfiloUtente nuovoDatiProfiloUtente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(DatiProfiloUtente.class);
        nuovoDatiProfiloUtente.setDescrizione("d".repeat(301)); //dato non corretto

        Utente utenteModificato = utenteMapper.datiProfiloUtenteToUtente(nuovoDatiProfiloUtente);
        UUID idUtenteModificato = UUID.randomUUID();
        utenteModificato.setId(idUtenteModificato);
        utenteModificato.setIdAuth("abcdefghilmnopqrstuvz1234567");

        when(utenteServiceMock.modificaDatiUtente(any(DatiProfiloUtente.class))).thenReturn(utenteModificato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/utente/modificaDatiUtente")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovoDatiProfiloUtente))).andReturn();

        verify(utenteServiceMock, times(0)).modificaDatiUtente(any(DatiProfiloUtente.class));
        assertEquals(400, mvcResult.getResponse().getStatus());
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getDatiUtenteProprioTest() throws Exception {
        DatiProfiloUtente risultato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(DatiProfiloUtente.class);
        when(utenteServiceMock.getDatiUtente(null)).thenReturn(risultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/utente/datiUtente")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(utenteServiceMock, times(1)).getDatiUtente(null);
        assertEquals(objectMapper.writeValueAsString(risultato), bodyRisposta.toString());
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getDatiUtenteAltruiTest() throws Exception {
        DatiProfiloUtente risultato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(DatiProfiloUtente.class);
        UUID idUtente = UUID.randomUUID();
        when(utenteServiceMock.getDatiUtente(idUtente)).thenReturn(risultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/utente/datiUtente?idUtente=" + idUtente)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(utenteServiceMock, times(1)).getDatiUtente(idUtente);
        assertEquals(objectMapper.writeValueAsString(risultato), bodyRisposta.toString());
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getUtenteByEmailTest() throws Exception {
        Utente risultato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Utente.class);
        String emailUtente = "test.test@test.com";
        risultato.setEmail(emailUtente);
        when(utenteServiceMock.getUtenteByEmail(emailUtente)).thenReturn(risultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/registrazione/esisteEmail/" + emailUtente)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(utenteServiceMock, times(1)).getUtenteByEmail(emailUtente);
        assertEquals(objectMapper.writeValueAsString(risultato), bodyRisposta.toString());
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getUtenteByIdAuthTest() throws Exception {
        Utente risultato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Utente.class);
        String idAuth = "abcdefghilmnopqrstuvz1234567";
        risultato.setIdAuth(idAuth);
        when(utenteServiceMock.findUtenteByIdAuth(idAuth)).thenReturn(risultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/utente/idUtente/" + idAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(utenteServiceMock, times(1)).findUtenteByIdAuth(idAuth);
        assertEquals(objectMapper.writeValueAsString(risultato), bodyRisposta.toString());
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getRuoloUtenteTest() throws Exception {
        RuoloUtente risultato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Utente.class).getRuolo();
        when(utenteServiceMock.getRuoloUtente()).thenReturn(risultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/utente/ruolo")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(utenteServiceMock, times(1)).getRuoloUtente();
        assertEquals(objectMapper.writeValueAsString(risultato), bodyRisposta.toString());
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void modifyPartitaIvaTest() throws Exception {
        String nuovaPartitaIva = "12312312312";
        Utente utenteModificato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Utente.class);
        UUID idUtenteModificato = UUID.randomUUID();
        utenteModificato.setId(idUtenteModificato);
        utenteModificato.setIdAuth("abcdefghilmnopqrstuvz1234567");
        utenteModificato.setPartitaIva(nuovaPartitaIva);

        when(utenteServiceMock.modificaPartitaIva(any(java.lang.String.class))).thenReturn(utenteModificato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/utente/datiVenditore/partitaIva")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(utenteModificato))).andReturn();

        verify(utenteServiceMock, times(1)).modificaPartitaIva(any(java.lang.String.class));
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void modifyDocumentoVenditoreTest() throws Exception {
        String nuovoDocumentoVenditore = "https://test.documento";
        Utente utenteModificato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Utente.class);
        UUID idUtenteModificato = UUID.randomUUID();
        utenteModificato.setId(idUtenteModificato);
        utenteModificato.setIdAuth("abcdefghilmnopqrstuvz1234567");
        utenteModificato.setUrlDocumentoIdentita(nuovoDocumentoVenditore);

        when(utenteServiceMock.modificaDocumentoVenditore(any(java.lang.String.class))).thenReturn(utenteModificato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/utente/datiVenditore/documentoVenditore")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(utenteModificato))).andReturn();

        verify(utenteServiceMock, times(1)).modificaDocumentoVenditore(any(java.lang.String.class));
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void setUtenteVenditoreTest() throws Exception {
        Utente utenteModificato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Utente.class);
        UUID idUtenteModificato = UUID.randomUUID();
        utenteModificato.setId(idUtenteModificato);
        utenteModificato.setIdAuth("abcdefghilmnopqrstuvz1234567");
        utenteModificato.setRuolo(RuoloUtente.VENDITORE);

        when(utenteServiceMock.setUtenteVenditore()).thenReturn(utenteModificato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/utente/ruolo")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(utenteModificato))).andReturn();

        verify(utenteServiceMock, times(1)).setUtenteVenditore();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getPartitaIvaTest() throws Exception {
        String risultato = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Utente.class).getPartitaIva();
        when(utenteServiceMock.getPartitaIva()).thenReturn(risultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/utente/partitaIva")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        verify(utenteServiceMock, times(1)).getPartitaIva();
        assertEquals(risultato, mvcResult.getResponse().getContentAsString());
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
