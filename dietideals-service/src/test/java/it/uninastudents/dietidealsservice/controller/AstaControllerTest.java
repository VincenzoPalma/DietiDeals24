package it.uninastudents.dietidealsservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.exceptions.UnauthorizedException;
import it.uninastudents.dietidealsservice.model.dto.CreaAsta;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.model.mapper.AstaMapper;
import it.uninastudents.dietidealsservice.service.AstaService;
import it.uninastudents.dietidealsservice.utils.ControllerUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AstaService astaServiceMock;

    @Autowired
    private AstaMapper astaMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void creazioneAstaConDatiCorrettiEUtenteAutorizzato() throws Exception {
        CreaAsta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CreaAsta.class);
        nuovaAsta.setIntervalloTempoOfferta(50);
        nuovaAsta.setDataScadenza(OffsetDateTime.now().plusDays(1));

        Asta astaRisultato = astaMapper.creaAstaToAsta(nuovaAsta); // Inizializza un oggetto Asta simulato restituito dal service
        UUID idAstaRisultato = UUID.randomUUID();
        astaRisultato.setId(idAstaRisultato); // Imposta un ID simulato per l'oggetto Asta
        astaRisultato.setStato(StatoAsta.ATTIVA);

        when(astaServiceMock.salvaAsta(any(Asta.class))).thenReturn(astaRisultato); // Configura il comportamento del service mock

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/utente/aste")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovaAsta))).andReturn();

        verify(astaServiceMock, times(1)).salvaAsta(any(Asta.class));
        assertEquals(201, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(astaRisultato), mvcResult.getResponse().getContentAsString());
        assertEquals("/utente/aste/" + idAstaRisultato, mvcResult.getResponse().getHeader("Location"));
    }

    @Test
    void creazioneAstaConUtenteNonAutorizzato() throws Exception {
        CreaAsta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CreaAsta.class);
        nuovaAsta.setIntervalloTempoOfferta(50);
        nuovaAsta.setDataScadenza(OffsetDateTime.now().plusDays(1));
        nuovaAsta.setTipo(TipoAsta.INGLESE); //anche TipoAsta.SILENZIOSA è valido per il test

        Asta astaRisultato = astaMapper.creaAstaToAsta(nuovaAsta);
        UUID idAstaRisultato = UUID.randomUUID();
        astaRisultato.setId(idAstaRisultato);
        astaRisultato.setStato(StatoAsta.ATTIVA);

        when(astaServiceMock.salvaAsta(any(Asta.class))).thenThrow(new UnauthorizedException("Un utente compratore non può creare un'asta inglese o silenziosa."));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/utente/aste")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovaAsta))).andReturn();

        verify(astaServiceMock, times(1)).salvaAsta(any(Asta.class));
        assertEquals(403, mvcResult.getResponse().getStatus());
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void creazioneAstaConDatiNonCorretti() throws Exception {
        CreaAsta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CreaAsta.class);
        nuovaAsta.setDataScadenza(OffsetDateTime.now().plusDays(1));
        nuovaAsta.setIntervalloTempoOfferta(600); //dato non corretto

        Asta astaRisultato = astaMapper.creaAstaToAsta(nuovaAsta);
        UUID idAstaRisultato = UUID.randomUUID();
        astaRisultato.setId(idAstaRisultato);
        astaRisultato.setStato(StatoAsta.ATTIVA);

        when(astaServiceMock.salvaAsta(any(Asta.class))).thenReturn(astaRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/utente/aste")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovaAsta))).andReturn();

        verify(astaServiceMock, times(0)).salvaAsta(any(Asta.class));
        assertEquals(400, mvcResult.getResponse().getStatus());
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAstePerNomeTipoCategoriaTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota e sono presenti tutti i parametri
        getAllAsteTest(0, "stringa test non trovata", TipoAsta.INGLESE, CategoriaAsta.ALIMENTARI);
        //Caso in cui la lista dei risultati non è vuota e sono presenti tutti i parametri
        getAllAsteTest(3, "stringa test", TipoAsta.INVERSA, CategoriaAsta.ATTREZZI_E_UTENSILI);
    }

    @Test
    void getAstePerNomeTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota ed è presente solo il parametro stringaRicerca
        getAllAsteTest(0, "stringa test", null, null);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro stringaRicerca
        getAllAsteTest(5, "stringa test", null, null);
    }

    @Test
    void getAstePerTipoTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota ed è presente solo il parametro tipoAsta
        getAllAsteTest(0, null, TipoAsta.INGLESE, null);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro tipoAsta
        getAllAsteTest(8, null, TipoAsta.INGLESE, null);
    }

    @Test
    void getAstePerCategoriaTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota ed è presente solo il parametro categoriaAsta
        getAllAsteTest(0, null, null, CategoriaAsta.ARREDAMENTO);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro categoriaAsta
        getAllAsteTest(2, null, null, CategoriaAsta.ARREDAMENTO);
    }

    @Test
    void getAsteSenzaParametriTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota e non è presente alcun parametro
        getAllAsteTest(0, null, null, null);
        //Caso in cui la lista dei risultati non è vuota e non è presente alcun parametro
        getAllAsteTest(11, null, null, null);
    }

    private void getAllAsteTest(int sizeRisultato, String stringaRicerca, TipoAsta tipoAsta, CategoriaAsta categoriaAsta) throws Exception {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());
        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < sizeRisultato; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setCategoria(categoriaAsta);
            nuovaAsta.setStato(StatoAsta.ATTIVA);
            nuovaAsta.setTipo(tipoAsta);
            if (stringaRicerca != null) {
                nuovaAsta.setNome("prova " + stringaRicerca + " test");
            } else {
                nuovaAsta.setNome("prova test");
            }
            listaAste.add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, sizeRisultato);
        when(astaServiceMock.getAll(pageable, stringaRicerca, tipoAsta, categoriaAsta)).thenReturn(risultati);

        MockHttpServletRequestBuilder requestBuilder = get("/aste");
        if (stringaRicerca != null) {
            requestBuilder.param("nome", stringaRicerca);
        }
        if (categoriaAsta != null) {
            requestBuilder.param("categoria", String.valueOf(categoriaAsta));
        }
        if (tipoAsta != null) {
            requestBuilder.param("tipo", String.valueOf(tipoAsta));
        }

        MvcResult mvcResult = mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andReturn();
        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        int sizeRisposta = bodyRisposta.get("content").size();
        assertEquals(0, bodyRisposta.get("pageable").get("pageNumber").asInt());
        assertEquals(12, bodyRisposta.get("pageable").get("pageSize").asInt());
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(bodyRisposta.get("content").isArray());
        assertEquals(sizeRisultato, sizeRisposta);

        for (JsonNode astaRisultato : bodyRisposta.get("content")) {
            if (stringaRicerca != null && sizeRisposta > 0) {
                assertTrue(astaRisultato.get("nome").asText().contains(stringaRicerca));
            }

            if (tipoAsta != null && sizeRisposta > 0) {
                assertEquals(tipoAsta.toString(), astaRisultato.get("tipo").asText());
            }

            if (categoriaAsta != null && sizeRisposta > 0) {
                assertEquals(categoriaAsta.toString(), astaRisultato.get("categoria").asText());
            }
        }
    }

    @Test
    void getAsteUtenteTest() throws Exception {
        getAsteUtenteByStatoTest(StatoAsta.ATTIVA);
        getAsteUtenteByStatoTest(StatoAsta.TERMINATA);
    }

    void getAsteUtenteByStatoTest(StatoAsta statoAsta) throws Exception {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());
        ArrayList<Asta> listaAste = new ArrayList<>();
        int sizeRisultato = 5;
        for (int i = 0; i < sizeRisultato; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setStato(statoAsta);
            listaAste.add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, sizeRisultato);
        when(astaServiceMock.getAsteUtenteByStato(pageable, statoAsta)).thenReturn(risultati);
        MockHttpServletRequestBuilder requestBuilder = get("/utente/aste").param("stato", statoAsta.toString());

        MvcResult mvcResult = mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andReturn();
        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(astaServiceMock, times(1)).getAsteUtenteByStato(pageable, statoAsta);
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(bodyRisposta.get("content").isArray());
        assertEquals(0, bodyRisposta.get("pageable").get("pageNumber").asInt());
        assertEquals(12, bodyRisposta.get("pageable").get("pageSize").asInt());
        assertTrue(bodyRisposta.get("pageable").get("sort").get("sorted").asBoolean());
        for (JsonNode astaRisultato : bodyRisposta.get("content")) {
            assertEquals(statoAsta.toString(), astaRisultato.get("stato").asText());
        }
    }

    @Test
    void getAstePartecipateTest() throws Exception {
        getAstePartecipateByVinta(true);
        getAstePartecipateByVinta(false);
    }

    void getAstePartecipateByVinta(boolean vinta) throws Exception {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());
        ArrayList<Asta> listaAste = new ArrayList<>();
        int sizeRisultato = 5;
        for (int i = 0; i < sizeRisultato; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            listaAste.add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, sizeRisultato);
        when(astaServiceMock.getAsteACuiUtenteHaPartecipato(pageable, vinta)).thenReturn(risultati);
        MockHttpServletRequestBuilder requestBuilder = get("/utente/offerte/asta").param("vinta", String.valueOf(vinta));

        MvcResult mvcResult = mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andReturn();
        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(astaServiceMock, times(1)).getAsteACuiUtenteHaPartecipato(pageable, vinta);
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(bodyRisposta.get("content").isArray());
        assertEquals(0, bodyRisposta.get("pageable").get("pageNumber").asInt());
        assertEquals(12, bodyRisposta.get("pageable").get("pageSize").asInt());
        assertTrue(bodyRisposta.get("pageable").get("sort").get("sorted").asBoolean());
    }

}
