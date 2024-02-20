package it.uninastudents.dietidealsservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.service.NotificaService;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class NotificaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificaService notificaServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getNotificheUtenteTest() throws Exception {
        Pageable pageable = ControllerUtils.pageableBuilder(0, 12, Sort.by("creationDate").ascending());
        int sizeRisultato = 7;
        ArrayList<Notifica> listaNotifiche = new ArrayList<>();
        for (int i = 0; i < sizeRisultato; i++) {
            Notifica nuovaNotifica = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Notifica.class);
            listaNotifiche.add(nuovaNotifica);
        }
        Page<Notifica> risultati = new PageImpl<>(listaNotifiche, pageable, sizeRisultato);
        when(notificaServiceMock.findAllNotificheUtente(pageable)).thenReturn(risultati);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/utente/notifiche")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")).andReturn();

        JsonNode bodyRisposta = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        verify(notificaServiceMock, times(1)).findAllNotificheUtente(pageable);
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(bodyRisposta.get("content").isArray());
    }

    @Test
    void deleteNotificheTest() throws Exception {
        doNothing().when(notificaServiceMock).cancellaNotificheUtente();

        MvcResult mvcResult = mockMvc.perform(delete("/utente/notifiche")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals(204, mvcResult.getResponse().getStatus());
        verify(notificaServiceMock, times(1)).cancellaNotificheUtente();
    }
}
