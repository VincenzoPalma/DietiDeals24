package it.uninastudents.dietidealsservice.service;

import com.google.firebase.auth.UserRecord;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import it.uninastudents.dietidealsservice.model.User;
import it.uninastudents.dietidealsservice.model.dto.CreaContoCorrente;
import it.uninastudents.dietidealsservice.model.dto.DatiProfiloUtente;
import it.uninastudents.dietidealsservice.model.dto.UtenteRegistrazione;
import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import it.uninastudents.dietidealsservice.model.entity.enums.RuoloUtente;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@SuppressWarnings("unchecked")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@RunWith(PowerMockRunner.class)
@PrepareForTest({UserRecord.CreateRequest.class})
class UtenteServiceTest {

    @SpyBean
    UtenteService utenteServiceSpy;
    @MockBean
    private UtenteRepository utenteRepositoryMock;
    @MockBean
    private ContoCorrenteRepository contoCorrenteRepositoryMock;
    @Autowired
    private UtenteService utenteService;

    @Test
    void findUtenteByIdAuth() {
        Utente utente = new Utente();
        String idAuth = "id auth";

        when(utenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(utente));

        Utente risultatoEffettivo = utenteService.findUtenteByIdAuth(idAuth);

        verify(utenteRepositoryMock, times(1)).findOne(any(Specification.class));
        assertEquals(utente, risultatoEffettivo);
    }

    @Test
    void findUtenteByIdUtenteNonTrovatoAuth() {
        String idAuth = "id auth";

        when(utenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.empty());

        Utente risultatoEffettivo = utenteService.findUtenteByIdAuth(idAuth);

        verify(utenteRepositoryMock, times(1)).findOne(any(Specification.class));
        assertNull(risultatoEffettivo);
    }

    @Test
    void getUtenteAutenticatoTest() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        User user = mock(User.class);

        Utente utente = new Utente();
        utente.setId(UUID.randomUUID());
        utente.setIdAuth("id auth");

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(utenteServiceSpy.findUtenteByIdAuth(user.getUid())).thenReturn(utente);

        Utente result = utenteServiceSpy.getUtenteAutenticato();

        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getPrincipal();
        verify(utenteServiceSpy, times(1)).findUtenteByIdAuth(user.getUid());
        assertEquals(utente, result);
    }

    @Test
    void getDatiUtenteTest() {
        Utente utente = new Utente();
        UUID idUtente = UUID.randomUUID();
        DatiProfiloUtente datiProfiloUtente = new DatiProfiloUtente();
        utente.setId(idUtente);

        when(utenteRepositoryMock.findById(idUtente)).thenReturn(Optional.of(utente));

        DatiProfiloUtente risultato = utenteService.getDatiUtente(idUtente);

        verify(utenteRepositoryMock, times(1)).findById(idUtente);
        assertEquals(datiProfiloUtente, risultato);
    }

    @Test
    void getDatiUtenteNonTrovatoTest() {
        Utente utente = new Utente();
        UUID idUtente = UUID.randomUUID();
        utente.setId(idUtente);

        when(utenteRepositoryMock.findById(idUtente)).thenReturn(Optional.empty());

        DatiProfiloUtente risultato = utenteService.getDatiUtente(idUtente);

        verify(utenteRepositoryMock, times(1)).findById(idUtente);
        assertNull(risultato);
    }

    @Test
    void getDatiUtenteIdUtenteNonInseritoTest() {
        Utente utente = new Utente();
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();
        DatiProfiloUtente datiProfiloUtente = new DatiProfiloUtente();

        DatiProfiloUtente risultato = utenteService.getDatiUtente(null);

        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertEquals(datiProfiloUtente, risultato);
    }

    @Test
    void modificaDatiUtenteTest() {
        Utente utente = new Utente();
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();

        DatiProfiloUtente datiProfiloUtente = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(DatiProfiloUtente.class);

        when(utenteRepositoryMock.save(any(Utente.class))).thenReturn(utente);

        Utente risultato = utenteService.modificaDatiUtente(datiProfiloUtente);

        verify(utenteRepositoryMock, times(1)).save(utente);
        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertEquals(utente, risultato);
    }

    @Test
    void getUtenteByEmailTest() {
        Utente utente = new Utente();
        String idAuth = "test@test.com";

        when(utenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(utente));

        Utente risultatoEffettivo = utenteService.getUtenteByEmail(idAuth);

        verify(utenteRepositoryMock, times(1)).findOne(any(Specification.class));
        assertEquals(utente, risultatoEffettivo);
    }

    @Test
    void getUtenteByEmailUtenteNonTrovatoTest() {
        String idAuth = "test@test.com";

        when(utenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.empty());

        Utente risultatoEffettivo = utenteService.getUtenteByEmail(idAuth);

        verify(utenteRepositoryMock, times(1)).findOne(any(Specification.class));
        assertNull(risultatoEffettivo);
    }

    @Test
    void getRuoloUtenteTest() {
        Utente utente = new Utente();
        utente.setRuolo(RuoloUtente.COMPRATORE);
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();

        when(utenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(utente));

        RuoloUtente risultato = utenteService.getRuoloUtente();

        verify(utenteRepositoryMock, times(1)).findOne(any(Specification.class));
        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertEquals(utente.getRuolo(), risultato);
    }

    @Test
    void getRuoloUtenteNonTrovatoTest() {
        Utente utente = new Utente();
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();

        when(utenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.empty());

        RuoloUtente risultato = utenteService.getRuoloUtente();

        verify(utenteRepositoryMock, times(1)).findOne(any(Specification.class));
        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertNull(risultato);
    }

    @Test
    void modificaPartitaIvaTest() {
        Utente utente = new Utente();
        String partitaIva = "partitaIva";
        utente.setPartitaIva(partitaIva);
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();

        when(utenteRepositoryMock.save(any(Utente.class))).thenReturn(utente);

        Utente risultato = utenteService.modificaPartitaIva(partitaIva);

        verify(utenteRepositoryMock, times(1)).save(utente);
        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertEquals(utente, risultato);

    }

    @Test
    void modificaDocumentoVenditoreTest() {
        Utente utente = new Utente();
        String documentoUrl = "url documento";
        utente.setUrlDocumentoIdentita(documentoUrl);
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();

        when(utenteRepositoryMock.save(any(Utente.class))).thenReturn(utente);

        Utente risultato = utenteService.modificaDocumentoVenditore(documentoUrl);

        verify(utenteRepositoryMock, times(1)).save(utente);
        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertEquals(utente, risultato);

    }

    @Test
    void getPartitaIvaTest() {
        Utente utente = new Utente();
        utente.setPartitaIva("partita iva");
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();

        when(utenteRepositoryMock.findOne(any(Specification.class))).thenReturn(Optional.of(utente));

        String risultato = utenteService.getPartitaIva();

        verify(utenteRepositoryMock, times(1)).findOne(any(Specification.class));
        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertEquals(utente.getPartitaIva(), risultato);
    }

    @Test
    void setUtenteVenditoreTest() {
        Utente utente = new Utente();
        utente.setRuolo(RuoloUtente.VENDITORE);
        doReturn(utente).when(utenteServiceSpy).getUtenteAutenticato();

        when(utenteRepositoryMock.save(any(Utente.class))).thenReturn(utente);

        Utente risultato = utenteService.setUtenteVenditore();

        verify(utenteRepositoryMock, times(1)).save(utente);
        verify(utenteServiceSpy, times(1)).getUtenteAutenticato();
        assertEquals(utente, risultato);

    }

    /*I seguenti test relativi al metodo registraUtente sono relativi al caso in cui
    il parametro idFireBase sia non null, ovvero una registrazione con credenziali di terze parti
    */
    @Test
    void registraUtenteCompratoreTest() {
        UtenteRegistrazione utenteRegistrazione = new UtenteRegistrazione();
        Utente utente = new Utente();
        utente.setIdAuth("id auth");
        utente.setRuolo(RuoloUtente.COMPRATORE);
        ArgumentCaptor<Utente> utenteCaptor = ArgumentCaptor.forClass(Utente.class);

        when(utenteRepositoryMock.save(any(Utente.class))).thenReturn(utente);

        Utente risultato = utenteService.registraUtente(utenteRegistrazione, "id auth");

        verify(utenteRepositoryMock, times(2)).save(utenteCaptor.capture());
        assertEquals(utente, risultato);

    }

    @Test
    void registraUtenteVenditoreTest() {
        UtenteRegistrazione utenteRegistrazione = new UtenteRegistrazione();
        utenteRegistrazione.setContoCorrente(new CreaContoCorrente());
        Utente utente = new Utente();
        utente.setContoCorrente(new ContoCorrente());
        utente.setIdAuth("id auth");
        utente.setRuolo(RuoloUtente.VENDITORE);
        ArgumentCaptor<Utente> utenteCaptor = ArgumentCaptor.forClass(Utente.class);

        when(utenteRepositoryMock.save(any(Utente.class))).thenReturn(utente);
        when(contoCorrenteRepositoryMock.save(any(ContoCorrente.class))).thenReturn(utente.getContoCorrente());

        Utente risultato = utenteService.registraUtente(utenteRegistrazione, "id auth");

        verify(utenteRepositoryMock, times(3)).save(utenteCaptor.capture());
        verify(contoCorrenteRepositoryMock, times(1)).save(any(ContoCorrente.class));
        assertEquals(utente, risultato);

    }

    @Test
    void registraUtenteEccezioneTest() {
        Utente risultato = utenteService.registraUtente(null, "id auth");
        assertNull(risultato);
    }
}
