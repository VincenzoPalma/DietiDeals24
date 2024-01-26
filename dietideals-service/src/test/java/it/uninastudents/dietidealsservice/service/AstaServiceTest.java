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
        //test del caso in cui il valore atteso della lunghezza è minore o uguale a 300 caratteri, ed il valore effettivo è minore di 300 caratteri
        assertTrue(astaService.isDescrizioneMinoreUguale300Caratteri("Stringa Testing Descrizione Minore o Uguale 300 Caratteri"));
        //test del caso in cui il valore atteso della lunghezza è minore o uguale a 300 caratteri, ed il valore effettivo è la stringa vuota
        assertTrue(astaService.isDescrizioneMinoreUguale300Caratteri(""));
        //test del caso in cui il valore atteso della lunghezza è minore o uguale a 300 caratteri, ed il valore effettivo è null
        assertFalse(astaService.isDescrizioneMinoreUguale300Caratteri(null));
        //test del caso in cui il valore atteso della lunghezza è minore o uguale a 300 caratteri, ed il valore effettivo è maggiore di 300 caratteri
        assertFalse(astaService.isDescrizioneMinoreUguale300Caratteri("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed ut commodo nulla. Phasellus facilisis aliquet efficitur. Integer pellentesque dui nec elit cursus ultrices." +
                " Vestibulum vel semper ligula. Fusce et elit ut nisi congue suscipit. Maecenas aliquam elit eu dolor laoreet, vel varius ligula aliquam.\n"));
    }

    @Test
    void isStatoAttivoOTerminatoTest(){
        //test del caso in cui il valore atteso dello stato è Attiva o Terminata, ed il valore effettivo è Attiva
        assertTrue(astaService.isStatoAttivaOrTerminata("Attiva"));
        //test del caso in cui il valore atteso dello stato è Attiva o Terminata, ed il valore effettivo è Terminata
        assertTrue(astaService.isStatoAttivaOrTerminata("Terminata"));
        //test del caso in cui il valore atteso dello stato è Attiva o Terminata, ed il valore effettivo è In Corso
        assertFalse(astaService.isStatoAttivaOrTerminata("In Corso"));
        //test del caso in cui il valore atteso dello stato è Attiva o Terminata, ed il valore effettivo è la stringa vuota
        assertFalse(astaService.isStatoAttivaOrTerminata(""));
        //test del caso in cui il valore atteso dello stato è Attiva o Terminata, ed il valore effettivo è null
        assertFalse(astaService.isStatoAttivaOrTerminata(null));
    }

    @Test
    void isCategoriaValidaTest(){ //da rivedere
        //test del caso in cui il valore atteso della categoria è Elettronica, ed il valore effettivo è Elettronica
        assertTrue(astaService.isCategoriaValida("Elettronica"));
        //test del caso in cui il valore atteso della categoria è Informatica, ed il valore effettivo è Informatica
        assertTrue(astaService.isCategoriaValida("Informatica"));
        //test del caso in cui il valore atteso della categoria è Giocattoli, ed il valore effettivo è Giocattoli
        assertTrue(astaService.isCategoriaValida("Giocattoli"));
        //test del caso in cui il valore atteso della categoria è Alimentari, ed il valore effettivo è Alimentari
        assertTrue(astaService.isCategoriaValida("Alimentari"));
        //test del caso in cui il valore atteso della categoria è Servizi, ed il valore effettivo è Servizi
        assertTrue(astaService.isCategoriaValida("Servizi"));
        //test del caso in cui il valore atteso della categoria è Arredamento, ed il valore effettivo è Arredamento
        assertTrue(astaService.isCategoriaValida("Arredamento"));
        //test del caso in cui il valore atteso della categoria è Auto e moto, ed il valore effettivo è Auto e moto
        assertTrue(astaService.isCategoriaValida("Auto e moto"));
        //test del caso in cui il valore atteso della categoria è Libri, ed il valore effettivo è Libri
        assertTrue(astaService.isCategoriaValida("Libri"));
        //test del caso in cui il valore atteso della categoria è Abbigliamento, ed il valore effettivo è Abbigliamento
        assertTrue(astaService.isCategoriaValida("Abbigliamento"));
        //test del caso in cui il valore atteso della categoria è Attrezzi e utensili, ed il valore effettivo è Attrezzi e utensili
        assertTrue(astaService.isCategoriaValida("Attrezzi e utensili"));
        //test del caso in cui il valore atteso della categoria è Bellezza, ed il valore effettivo è Bellezza
        assertTrue(astaService.isCategoriaValida("Bellezza"));
        //test del caso in cui il valore atteso della categoria è Musica e arte, ed il valore effettivo è Musica e arte
        assertTrue(astaService.isCategoriaValida("Musica e arte"));
        //test del caso in cui il valore atteso della categoria è uno nella lista di categorie, ed il valore effettivo è Categoria Non Esistente
        assertFalse(astaService.isCategoriaValida("Categoria Non Esistente"));
        //test del caso in cui il valore atteso della categoria è uno nella lista di categorie, ed il valore effettivo è la stringa vuota
        assertFalse(astaService.isCategoriaValida(""));
        //test del caso in cui il valore atteso della categoria è uno nella lista di categorie, ed il valore effettivo è null
        assertFalse(astaService.isCategoriaValida(null));
    }

    @Test
    void checkSogliaRialzoPositivaTest(){
        //test del caso in cui il valore atteso della soglia di rialzo è positivo, ed il valore effetivo è 500
        assertTrue(astaService.checkSogliaRialzoPositiva(BigDecimal.valueOf(500)));
        //test del caso in cui il valore atteso della soglia di rialzo è positivo, ed il valore effetivo è 0
        assertFalse(astaService.checkSogliaRialzoPositiva(BigDecimal.valueOf(0)));
        //test del caso in cui il valore atteso della soglia di rialzo è positivo, ed il valore effetivo è -500
        assertFalse(astaService.checkSogliaRialzoPositiva(BigDecimal.valueOf(-500)));
        //test del caso in cui il valore atteso della soglia di rialzo è positivo, ed il valore effetivo è null
        assertFalse(astaService.checkSogliaRialzoPositiva(null));
    }

    @Test
    void checkIntervalloTempoOffertaMin30minutiMax3oreTest(){
        //test del caso in cui il valore atteso dell intervalloTempoOfferta è maggiore di 30 monuti e minore di 3 ore, ed il valore effettivo è 50 minuti
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofMinutes(50)));
        //test del caso in cui il valore atteso dell intervalloTempoOfferta è maggiore di 30 monuti e minore di 3 ore, ed il valore effettivo è 30 minuti
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofMinutes(30)));
        //test del caso in cui il valore atteso dell intervalloTempoOfferta è maggiore di 30 monuti e minore di 3 ore, ed il valore effettivo è 1 ora
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofHours(1)));
        //test del caso in cui il valore atteso dell intervalloTempoOfferta è maggiore di 30 monuti e minore di 3 ore, ed il valore effettivo è 3 ore
        assertTrue(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofHours(3)));
        //test del caso in cui il valore atteso dell intervalloTempoOfferta è maggiore di 30 monuti e minore di 3 ore, ed il valore effettivo è 15 minuti
        assertFalse(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofMinutes(15)));
        //test del caso in cui il valore atteso dell intervalloTempoOfferta è maggiore di 30 monuti e minore di 3 ore, ed il valore effettivo è 6 ore
        assertFalse(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(Duration.ofHours(6)));
        //test del caso in cui il valore atteso dell intervalloTempoOfferta è maggiore di 30 monuti e minore di 3 ore, ed il valore effettivo è null
        assertFalse(astaService.checkIntervalloTempoOffertaMin30minutiMax3ore(null));
    }

    @Test
    void checkDataScadenzaMin24oreMax30giorniTest(){
        //test del caso in cui il valore atteso della dataScadenza è maggiore di 24 ore e minore di 30 giorni, ed il valore effettivo è 25 giorni
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusDays(25)));
        //test del caso in cui il valore atteso della dataScadenza è maggiore di 24 ore e minore di 30 giorni, ed il valore effettivo è 30 giorni
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusDays(30)));
        //test del caso in cui il valore atteso della dataScadenza è maggiore di 24 ore e minore di 30 giorni, ed il valore effettivo è 31 ore
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusHours(31)));
        //test del caso in cui il valore atteso della dataScadenza è maggiore di 24 ore e minore di 30 giorni, ed il valore effettivo è 24 ore
        assertTrue(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusHours(24)));
        //test del caso in cui il valore atteso della dataScadenza è maggiore di 24 ore e minore di 30 giorni, ed il valore effettivo è 42 giorni
        assertFalse(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusDays(42)));
        //test del caso in cui il valore atteso della dataScadenza è maggiore di 24 ore e minore di 30 giorni, ed il valore effettivo è 16 ore
        assertFalse(astaService.checkDataScadenzaMin24oreMax30giorni(LocalDateTime.now().plusHours(16)));
        //test del caso in cui il valore atteso della dataScadenza è maggiore di 24 ore e minore di 30 giorni, ed il valore effettivo è null
        assertFalse(astaService.checkDataScadenzaMin24oreMax30giorni(null));
    }

    @Test
    void checkDatiAstaTest(){
        Asta astaIngleseTest = new Asta(1, "Asta Test", "Descrizione Test", null, null, BigDecimal.valueOf(20), Duration.ofHours(2), "Elettronica", "Inglese", "Attiva", new Utente());
        Asta astaSilenziosaTest = new Asta(2, "Asta Test", "Descrizione Test", null, LocalDateTime.now().plusDays(15), null, null, "Elettronica", "Silenziosa", "Attiva", new Utente());
        Asta astaInversaTest = new Asta(3, "Asta Test", "Descrizione Test", null, LocalDateTime.now().plusDays(15), null, null, "Elettronica", "Inversa", "Attiva", new Utente());
        Asta astaErrataTest = new Asta(4, "Asta Test", "Descrizione Test", null, null, BigDecimal.valueOf(20), Duration.ofHours(2), "Elettronica", "Tipo Errato", "Attiva", new Utente());

        //da vedere
        assertTrue(astaService.checkDatiAsta(astaIngleseTest));
        assertTrue(astaService.checkDatiAsta(astaSilenziosaTest));
        assertTrue(astaService.checkDatiAsta(astaInversaTest));
        assertFalse(astaService.checkDatiAsta(astaErrataTest));
        assertFalse(astaService.checkDatiAsta(null));
    }

}
