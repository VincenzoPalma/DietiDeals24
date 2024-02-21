package it.uninastudents.dietidealsservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.service.OffertaService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class OffertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffertaService offertaServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveOffertaDatiCorrettiTest() throws Exception {
        BigDecimal prezzoNuovaOfferta = BigDecimal.valueOf(50.00);
        Offerta offertaRisultato = new Offerta();
        offertaRisultato.setPrezzo(prezzoNuovaOfferta);
        UUID idOffertaRisultato = UUID.randomUUID();
        offertaRisultato.setId(idOffertaRisultato);
        UUID idAsta = UUID.randomUUID();

        when(offertaServiceMock.salvaOfferta(idAsta, prezzoNuovaOfferta)).thenReturn(offertaRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/aste/" + idAsta + "/offerte")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(prezzoNuovaOfferta))).andReturn();

        verify(offertaServiceMock, times(1)).salvaOfferta(idAsta, prezzoNuovaOfferta);
        assertEquals(201, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(offertaRisultato), mvcResult.getResponse().getContentAsString());
        assertEquals("/asta/" + idAsta + "/offerte/" + idOffertaRisultato, mvcResult.getResponse().getHeader("Location"));
    }

    @Test
    void saveOffertaDatiNonCorrettiTest() throws Exception {
        BigDecimal prezzoNuovaOfferta = BigDecimal.valueOf(-50.00); //dato non corretto
        Offerta offertaRisultato = new Offerta();
        offertaRisultato.setPrezzo(prezzoNuovaOfferta);
        UUID idOffertaRisultato = UUID.randomUUID();
        offertaRisultato.setId(idOffertaRisultato);
        UUID idAsta = UUID.randomUUID();

        when(offertaServiceMock.salvaOfferta(idAsta, prezzoNuovaOfferta)).thenReturn(offertaRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/aste/" + idAsta + "/offerte")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(prezzoNuovaOfferta))).andReturn();

        verify(offertaServiceMock, times(0)).salvaOfferta(idAsta, prezzoNuovaOfferta);
        assertEquals(400, mvcResult.getResponse().getStatus());
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getOfferteNonVincentiTest() throws Exception {
        getOfferteTest(StatoOfferta.NON_VINCENTE);
    }

    @Test
    void getOfferteVincentiTest() throws Exception {
        getOfferteTest(StatoOfferta.VINCENTE);
    }

    void getOfferteTest(StatoOfferta statoOfferta) throws Exception {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").descending());
        int sizeRisultato;
        if (statoOfferta.equals(StatoOfferta.VINCENTE)) {
            sizeRisultato = 1;
        } else {
            sizeRisultato = 15;
        }
        ArrayList<Offerta> listaOfferte = new ArrayList<>();
        for (int i = 0; i < sizeRisultato; i++) {
            Offerta nuovaOfferta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Offerta.class);
            listaOfferte.add(nuovaOfferta);
        }
        Page<Offerta> risultati = new PageImpl<>(listaOfferte, pageable, sizeRisultato);
        UUID idAsta = UUID.randomUUID();
        when(offertaServiceMock.findOffertaByStato(pageable, idAsta, statoOfferta)).thenReturn(risultati);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/aste/" + idAsta + "/offerte?statoOfferta=" + statoOfferta)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        assertEquals(0, bodyRisposta.get("pageable").get("pageNumber").asInt());
        assertEquals(12, bodyRisposta.get("pageable").get("pageSize").asInt());
        assertTrue(bodyRisposta.get("pageable").get("sort").get("sorted").asBoolean());
        verify(offertaServiceMock, times(1)).findOffertaByStato(pageable, idAsta, statoOfferta);
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(bodyRisposta.get("content").isArray());
        assertEquals(sizeRisultato, bodyRisposta.get("content").size());
    }
}
