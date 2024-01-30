package it.uninastudents.dietidealsservice.controller;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.service.AstaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@WebMvcTest(AstaController.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AstaService astaServiceMock;

    @InjectMocks
    private AstaController astaController;

    @Test
    void getAsteTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota e sono presenti tutti i parametri
        getAsteTestAux(0, "stringa test non trovata", TipoAsta.INGLESE, CategoriaAsta.ALIMENTARI);
        //Caso in cui la lista dei risultati non è vuota e sono presenti tutti i parametri
        getAsteTestAux(3, "stringa test", TipoAsta.INVERSA, CategoriaAsta.ATTREZZI_E_UTENSILI);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro stringaRicerca
        getAsteTestAux(5, "stringa test", null, null);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro tipoAsta
        getAsteTestAux(8, null, TipoAsta.INGLESE, null);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro categoriaAsta
        getAsteTestAux(2, null, null, CategoriaAsta.ARREDAMENTO);
        //Caso in cui la lista dei risultati non è vuota e non è presente alcun parametro
        getAsteTestAux(11, null, null, null);
    }

    private void getAsteTestAux(int sizeRisultato, String stringaRicerca, TipoAsta tipoAsta, CategoriaAsta categoriaAsta) throws Exception {
        Pageable pageable = PageRequest.of(0, 12);
        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < sizeRisultato; i++) {
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            nuovaAsta.setCategoria(categoriaAsta);
            nuovaAsta.setStato(StatoAsta.ATTIVA);
            nuovaAsta.setTipo(tipoAsta);
            if (stringaRicerca != null) {
                nuovaAsta.setNome("prova" + stringaRicerca + "test");
            } else {
                nuovaAsta.setNome("prova test");
            }
            listaAste.add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, sizeRisultato);
        when(astaServiceMock.getAll(pageable, stringaRicerca, tipoAsta, categoriaAsta)).thenReturn(risultati);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/asta/ricerca")
                .param("page", "0")
                .param("size", "12");
        if (stringaRicerca != null) {
            requestBuilder.param("nome", stringaRicerca);
        }
        if (categoriaAsta != null) {
            requestBuilder.param("categoria", String.valueOf(categoriaAsta));
        }
        if (tipoAsta != null) {
            requestBuilder.param("tipo", String.valueOf(tipoAsta));
        }
        mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(sizeRisultato))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].nome").value(stringaRicerca != null ? Matchers.everyItem(Matchers.containsString(stringaRicerca)) : Matchers.anything()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].tipo").value(tipoAsta != null ? Matchers.everyItem(Matchers.is(tipoAsta)) : Matchers.anything()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].stato").value("attivo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].categoria").value(categoriaAsta != null ? Matchers.everyItem(Matchers.is(categoriaAsta)) : Matchers.anything()));
    }
}
