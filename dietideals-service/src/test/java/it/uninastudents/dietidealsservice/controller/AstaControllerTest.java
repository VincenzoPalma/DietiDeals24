package it.uninastudents.dietidealsservice.controller;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.service.AstaService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.contains;
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
        //Caso in cui la lista dei risultati è vuota
        getAsteTestAux(0, "stringa test non trovata", TipoAsta.INGLESE, CategoriaAsta.ALIMENTARI);
        //Caso in cui la lista dei risultati non è vuota
        getAsteTestAux(3, "stringa test", TipoAsta.INVERSA, CategoriaAsta.ATTREZZI_E_UTENSILI);
    }

    private void getAsteTestAux(int sizeRisultato, String stringaRicerca, TipoAsta tipoAsta, CategoriaAsta categoriaAsta) throws Exception {
        Pageable pageable = PageRequest.of(0, 12);
        ArrayList<Asta> listaAste = new ArrayList<>();
        for (int i = 0; i < sizeRisultato; i++){
            Asta nuovaAsta = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Asta.class);
            listaAste.add(nuovaAsta);
        }
        Page<Asta> risultati = new PageImpl<>(listaAste, pageable, listaAste.size());
        when(astaServiceMock.getAll(pageable, stringaRicerca, tipoAsta, categoriaAsta)).thenReturn(risultati);

        mockMvc.perform(MockMvcRequestBuilders.get("asta/ricerca")
                        .param("page", "0")
                        .param("size", "12")
                        .param("nome", stringaRicerca)
                        .param("tipo", String.valueOf(tipoAsta))
                        .param("categoria", String.valueOf(categoriaAsta))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(sizeRisultato))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].nome").value(contains(stringaRicerca)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].tipo").value(tipoAsta))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].categoria").value(categoriaAsta));
    }
}
