package it.uninastudents.dietidealsservice.controller;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.dto.AstaRequest;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.service.AstaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AstaService astaServiceMock;

    @Autowired
    private AstaController astaController;

    @Test
    void getAstePerNomeTipoCategoriaTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota e sono presenti tutti i parametri
        getAsteTestAux(0, "stringa test non trovata", TipoAsta.INGLESE, CategoriaAsta.ALIMENTARI);
        //Caso in cui la lista dei risultati non è vuota e sono presenti tutti i parametri
        getAsteTestAux(3, "stringa test", TipoAsta.INVERSA, CategoriaAsta.ATTREZZI_E_UTENSILI);
    }

    @Test
    void getAstePerNomeTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota ed è presente solo il parametro stringaRicerca
        getAsteTestAux(0, "stringa test", null, null);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro stringaRicerca
        getAsteTestAux(5, "stringa test", null, null);
    }

    @Test
    void getAstePerTipoTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota ed è presente solo il parametro tipoAsta
        getAsteTestAux(0, null, TipoAsta.INGLESE, null);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro tipoAsta
        getAsteTestAux(8, null, TipoAsta.INGLESE, null);
    }

    @Test
    void getAstePerCategoriaTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota ed è presente solo il parametro categoriaAsta
        getAsteTestAux(0, null, null, CategoriaAsta.ARREDAMENTO);
        //Caso in cui la lista dei risultati non è vuota ed è presente solo il parametro categoriaAsta
        getAsteTestAux(2, null, null, CategoriaAsta.ARREDAMENTO);
    }

    @Test
    void getAsteSenzaParametriTest() throws Exception {
        //Caso in cui la lista dei risultati è vuota e non è presente alcun parametro
        getAsteTestAux(0, null, null, null);
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

        MockHttpServletRequestBuilder requestBuilder = get("/asta/ricerca")
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
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(sizeRisultato))
                .andExpect(jsonPath("$.content[*].nome").value(stringaRicerca != null ? everyItem(containsString(stringaRicerca)) : anything()))
                .andExpect(jsonPath("$.content[*].tipo").value(tipoAsta != null ? everyItem(is(tipoAsta)) : anything()))
                .andExpect(jsonPath("$.content[*].stato").value("attivo"))
                .andExpect(jsonPath("$.content[*].categoria").value(categoriaAsta != null ? everyItem(is(categoriaAsta)) : anything()));
    }

    @Test
    void getAsteTest() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by("creationDate").ascending());
        String nome = "exampleName";
        TipoAsta tipo = TipoAsta.INGLESE;
        CategoriaAsta categoria = CategoriaAsta.LIBRI;
        AstaRequest asta1 = new AstaRequest();
        AstaRequest asta2 = new AstaRequest();
        asta1.setNome("test" + nome + "prova");
        asta2.setNome("test" + nome + "prova");
        asta1.setTipo(tipo);
        asta2.setTipo(tipo);
        asta1.setCategoria(categoria);
        asta2.setCategoria(categoria);
        ArrayList<AstaRequest> listaAste = new ArrayList<>();
        listaAste.add(asta1);
        listaAste.add(asta2);
        Page<AstaRequest> risultatoAtteso = new PageImpl<>(listaAste);
        when(astaServiceMock.getAll(pageable, nome, tipo, categoria)).thenReturn(risultatoAtteso);
        Page<AstaRequest> risultato = astaController.getAste(0, 12, nome, tipo, categoria);
        verify(astaServiceMock, times(1)).getAll(pageable, nome, tipo, categoria);
        assertEquals(risultatoAtteso, risultato);
    }
}
