package it.uninastudents.dietidealsservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.dto.CreaCarta;
import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.mapper.CartaMapper;
import it.uninastudents.dietidealsservice.service.CartaService;
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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class CartaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartaService cartaServiceMock;

    @Autowired
    private CartaMapper cartaMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveCartaTest() throws Exception {
        CreaCarta nuovaCarta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CreaCarta.class);
        nuovaCarta.setNumero("1234567890123456");
        nuovaCarta.setDataScadenza(OffsetDateTime.now().plusDays(1));
        nuovaCarta.setCodiceCvvCvc("123");

        Carta cartaRisultato = cartaMapper.creaCartaToCarta(nuovaCarta);
        UUID idCartaRisultato = UUID.randomUUID();
        cartaRisultato.setId(idCartaRisultato);

        when(cartaServiceMock.salvaCarta(any(Carta.class))).thenReturn(cartaRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/utente/carte")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovaCarta))).andReturn();

        verify(cartaServiceMock, times(1)).salvaCarta(any(Carta.class));
        assertEquals(201, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(cartaRisultato), mvcResult.getResponse().getContentAsString());
        assertEquals("/utente/carte/" + idCartaRisultato, mvcResult.getResponse().getHeader("Location"));
    }

    @Test
    void deleteCartaTest() throws Exception {
        UUID idCarta = UUID.randomUUID();
        doNothing().when(cartaServiceMock).cancellaCarta(idCarta);

        MvcResult mvcResult = mockMvc.perform(delete("/carte/{idCarta}", idCarta)
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals(204,  mvcResult.getResponse().getStatus());
        verify(cartaServiceMock, times(1)).cancellaCarta(idCarta);
    }

    @Test
    void getCarteUtenteTest() throws Exception {
        int sizeRisultato = 3;
        List<Carta> risultati = new ArrayList<>();
        for (int i = 0; i < sizeRisultato; i ++){
            Carta nuovaCarta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Carta.class);
            risultati.add(nuovaCarta);
        }
        when(cartaServiceMock.getAllByUtente()).thenReturn(risultati);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/utente/carte")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(cartaServiceMock, times(1)).getAllByUtente();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(bodyRisposta.isArray());
    }
}
