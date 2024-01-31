package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class AstaServiceTest {

    @MockBean
    private AstaRepository astaRepository;

    @Autowired
    private AstaService astaService;

    @SuppressWarnings("unchecked")
    @Test
    void getAllTest() {
        Pageable pageable = PageRequest.of(0, 12);
        String nome = "exampleName";
        TipoAsta tipo = TipoAsta.INGLESE;
        CategoriaAsta categoria = CategoriaAsta.LIBRI;
        Asta asta1 = new Asta();
        Asta asta2 = new Asta();
        asta1.setNome("test" + nome + "prova");
        asta2.setNome("test" + nome + "prova");
        asta1.setTipo(tipo);
        asta2.setTipo(tipo);
        asta1.setCategoria(categoria);
        asta2.setCategoria(categoria);

        ArrayList<Asta> listaAste = new ArrayList<>();
        listaAste.add(asta1);
        listaAste.add(asta2);

        Page<Asta> risultatoAtteso = new PageImpl<>(listaAste);

        when(astaRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(risultatoAtteso);

        Page<Asta> risultato = astaService.getAll(pageable, nome, tipo, categoria);

        verify(astaRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        assertEquals(risultato, risultatoAtteso);
    }
}
