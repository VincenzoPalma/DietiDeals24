package it.uninastudents.dietidealsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.dto.CreaContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.mapper.ContoCorrenteMapper;
import it.uninastudents.dietidealsservice.service.ContoCorrenteService;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class ContoCorrenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContoCorrenteService contoCorrenteServiceMock;

    @Autowired
    private ContoCorrenteMapper contoCorrenteMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveContoCorrenteDatiCorrettiTest() throws Exception {
        CreaContoCorrente nuovoContoCorrente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CreaContoCorrente.class);
        nuovoContoCorrente.setIban("abc123XYZ4567890abcdefghijk");
        nuovoContoCorrente.setCodiceBicSwift("123asda1");

        ContoCorrente contoCorrenteRisultato = contoCorrenteMapper.creaContoCorrenteToContoCorrente(nuovoContoCorrente);
        UUID idContoCorrenteRisultato = UUID.randomUUID();
        contoCorrenteRisultato.setId(idContoCorrenteRisultato);

        when(contoCorrenteServiceMock.salvaContoCorrente(any(ContoCorrente.class))).thenReturn(contoCorrenteRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/utente/contoCorrente")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovoContoCorrente))).andReturn();

        verify(contoCorrenteServiceMock, times(1)).salvaContoCorrente(any(ContoCorrente.class));
        assertEquals(201, mvcResult.getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(contoCorrenteRisultato), mvcResult.getResponse().getContentAsString());
        assertEquals("/utente/contoCorrente/" + idContoCorrenteRisultato, mvcResult.getResponse().getHeader("Location"));
    }

    @Test
    void saveContoCorrenteDatiNonCorrettiTest() throws Exception {
        CreaContoCorrente nuovoContoCorrente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CreaContoCorrente.class);
        nuovoContoCorrente.setIban("abc123XYZ4567890abcdefghijks"); //dato non corretto
        nuovoContoCorrente.setCodiceBicSwift("123asda1");

        ContoCorrente contoCorrenteRisultato = contoCorrenteMapper.creaContoCorrenteToContoCorrente(nuovoContoCorrente);
        UUID idContoCorrenteRisultato = UUID.randomUUID();
        contoCorrenteRisultato.setId(idContoCorrenteRisultato);

        when(contoCorrenteServiceMock.salvaContoCorrente(any(ContoCorrente.class))).thenReturn(contoCorrenteRisultato);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/utente/contoCorrente")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(nuovoContoCorrente))).andReturn();

        verify(contoCorrenteServiceMock, times(0)).salvaContoCorrente(any(ContoCorrente.class));
        assertEquals(400, mvcResult.getResponse().getStatus());
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }
}
