package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AstaServiceTest {
    private final AstaService astaService = new AstaService();

    @Test
    void isDescrizioneMinoreUguale300CaratteriTest(){
        assertTrue(astaService.isDescrizioneMinoreUguale300Caratteri("Stringa Testing Descrizione Minore o Uguale 300 Caratteri"));
        assertTrue(astaService.isDescrizioneMinoreUguale300Caratteri(""));
        assertFalse(astaService.isDescrizioneMinoreUguale300Caratteri(null));
        assertFalse(astaService.isDescrizioneMinoreUguale300Caratteri("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed ut commodo nulla. Phasellus facilisis aliquet efficitur. Integer pellentesque dui nec elit cursus ultrices." +
                " Vestibulum vel semper ligula. Fusce et elit ut nisi congue suscipit. Maecenas aliquam elit eu dolor laoreet, vel varius ligula aliquam.\n"));
    }

    @Test
    void isStatoAttivoOTerminatoTest(){
        assertTrue(astaService.isStatoAttivaOrTerminata("Attiva"));
        assertTrue(astaService.isStatoAttivaOrTerminata("Terminata"));
        assertFalse(astaService.isStatoAttivaOrTerminata("In Corso"));
        assertFalse(astaService.isStatoAttivaOrTerminata(""));
        assertFalse(astaService.isStatoAttivaOrTerminata(null));
    }

    @Test
    void isCategoriaValidaTest(){
        assertTrue(astaService.isCategoriaValida("Elettronica"));
        assertTrue(astaService.isCategoriaValida("Informatica"));
        assertTrue(astaService.isCategoriaValida("Giocattoli"));
        assertTrue(astaService.isCategoriaValida("Alimentari"));
        assertTrue(astaService.isCategoriaValida("Servizi"));
        assertTrue(astaService.isCategoriaValida("Arredamento"));
        assertTrue(astaService.isCategoriaValida("Auto e moto"));
        assertTrue(astaService.isCategoriaValida("Libri"));
        assertTrue(astaService.isCategoriaValida("Abbigliamento"));
        assertTrue(astaService.isCategoriaValida("Attrezzi e utensili"));
        assertTrue(astaService.isCategoriaValida("Bellezza"));
        assertTrue(astaService.isCategoriaValida("Musica e arte"));
        assertFalse(astaService.isCategoriaValida("Categoria Non Esistente"));
        assertFalse(astaService.isCategoriaValida(""));
        assertFalse(astaService.isCategoriaValida(null));
    }

    @Test
    void checkSogliaRialzoPositivaTest(){
        assertTrue(astaService.checkSogliaRialzoPositiva(BigDecimal.valueOf(500)));
        assertFalse(astaService.checkSogliaRialzoPositiva(BigDecimal.valueOf(0)));
        assertFalse(astaService.checkSogliaRialzoPositiva(BigDecimal.valueOf(-500)));
        assertFalse(astaService.checkSogliaRialzoPositiva(null));
    }

    @Test
    void checkIntervalloTempoOffertaMin30minutiMax3oreTest(){
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofMinutes(50)));
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofMinutes(30)));
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofHours(1)));
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofHours(3)));
        assertFalse(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofMinutes(15)));
        assertFalse(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofHours(6)));
        assertFalse(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(null));
    }

    @Test
    void checkDataScadenzaMin24oreMax30giorniTest(){
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusDays(25)));
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusDays(30)));
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusHours(31)));
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusHours(24)));
        assertFalse(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusDays(42)));
        assertFalse(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusHours(16)));
        assertFalse(astaService.checkDataScadenzaMin24oreMax30giorni(null));
    }

    @Test
    void checkDatiAstaTest(){
        Asta astaIngleseTest = new Asta(1, "Asta Test", "Descrizione Test", null, null, BigDecimal.valueOf(20), Duration.ofHours(2), "Elettronica", "Inglese", "Attiva", new Utente());
        Asta astaSilenziosaTest = new Asta(2, "Asta Test", "Descrizione Test", null, LocalDateTime.now().plusDays(15), null, null, "Elettronica", "Silenziosa", "Attiva", new Utente());
        Asta astaInversaTest = new Asta(3, "Asta Test", "Descrizione Test", null, LocalDateTime.now().plusDays(15), null, null, "Elettronica", "Inversa", "Attiva", new Utente());
        Asta astaErrataTest = new Asta(4, "Asta Test", "Descrizione Test", null, null, BigDecimal.valueOf(20), Duration.ofHours(2), "Elettronica", "Tipo Errato", "Attiva", new Utente());
        assertTrue(astaService.checkDatiAsta(astaIngleseTest));
        assertTrue(astaService.checkDatiAsta(astaSilenziosaTest));
        assertTrue(astaService.checkDatiAsta(astaInversaTest));
        assertFalse(astaService.checkDatiAsta(astaErrataTest));
        assertFalse(astaService.checkDatiAsta(null));
    }

}
