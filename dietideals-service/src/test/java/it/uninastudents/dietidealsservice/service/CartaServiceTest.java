package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartaServiceTest {

    private final CartaService cartaService = new CartaService();

    @Test
    void isDataScadenzaFuturaTest() {
        //test del caso in cui il valore atteso della dataScadenzaFutura è maggiore della data odierna, e il valore effettivo è 3 anni maggiore della data odierna
        assertTrue(cartaService.isDataScadenzaFutura(LocalDate.now().plusYears(3)));
        //test del caso in cui il valore atteso della dataScadenzaFutura è maggiore della data odierna, e il valore effettivo è 1 anno minore della data odierna
        assertFalse(cartaService.isDataScadenzaFutura(LocalDate.now().minusYears(1)));
        //test del caso in cui il valore atteso della dataScadenzaFutura è maggiore della data odierna, e il valore effettivo è la data odierna
        assertFalse(cartaService.isDataScadenzaFutura(LocalDate.now()));
        //test del caso in cui il valore atteso della dataScadenzaFutura è maggiore della data odierna, e il valore effettivo è null
        assertFalse(cartaService.isDataScadenzaFutura(null));
    }

    @Test
    void checkCartaTest() {
        Carta cartaTest1 = new Carta("1234567890123456", "Luigi Test", "123", LocalDate.now().plusYears(1), new Utente());
        Carta cartaTest2 = new Carta("1234567890123457", "Paolo Test", "124", LocalDate.now().plusYears(2), null);
        Carta cartaTest3 = new Carta("1234567890123458", null, "125", LocalDate.now().plusYears(3), new Utente());
        //test del caso in cui il valore atteso è un oggetto Carta con dati corretti e nomeTitolare e utente non nulli, e il valore effettivo è un oggetto Carta con dati corretti e nomeTitolare e utente non nulli
        assertTrue(cartaService.checkCarta(cartaTest1));
        //test del caso in cui il valore atteso è un oggetto Carta con dati corretti e nomeTitolare e utente non nulli, e il valore effettivo è un oggetto Carta con dati corretti e con utente nullo
        assertFalse(cartaService.checkCarta(cartaTest2));
        //test del caso in cui il valore atteso è un oggetto Carta con dati corretti e nomeTitolare e utente non nulli, e il valore effettivo è un oggetto Carta con dati corretti e con nomeTitolare nullo
        assertFalse(cartaService.checkCarta(cartaTest3));
        //test del caso in cui il valore atteso è un oggetto Carta con dati corretti e nomeTitolare e utente non nulli, e il valore effettivo è un oggetto Carta nullo
        assertFalse(cartaService.checkCarta(null));
    }

    @Test
    void isNumeroUguale16CaratteriTest() {
        //test del caso in cui il valore atteso del numero dei caratteri è 16, e il valore effettivo è 16 caratteri
        assertTrue(cartaService.isNumeroUguale16Caratteri("1234567890123456"));
        //test del caso in cui il valore atteso del numero dei caratteri è 16, e il valore effettivo è 4 caratteri
        assertFalse(cartaService.isNumeroUguale16Caratteri("1234"));
        //test del caso in cui il valore atteso del numero dei caratteri è 16, e il valore effettivo è 17 caratteri
        assertFalse(cartaService.isNumeroUguale16Caratteri("12345678901234567"));
        //test del caso in cui il valore atteso del numero dei caratteri è 16, e il valore effettivo è null
        assertFalse(cartaService.isNumeroUguale16Caratteri(null));
    }

    @Test
    void isCodiceCvvCvcUguale3CifreTest() {
        //test del caso in cui il valore atteso dei caratteri del CodiceCvvCvc è 3, e il valore effettivo è 3 caratteri
        assertTrue(cartaService.isCodiceCvvCvcUguale3Cifre("123"));
        //test del caso in cui il valore atteso dei caratteri del CodiceCvvCvc è 3, e il valore effettivo è 4 caratteri
        assertFalse(cartaService.isCodiceCvvCvcUguale3Cifre("1234"));
        //test del caso in cui il valore atteso dei caratteri del CodiceCvvCvc è 3, e il valore effettivo è 2 caratteri
        assertFalse(cartaService.isCodiceCvvCvcUguale3Cifre("12"));
        //test del caso in cui il valore atteso dei caratteri del CodiceCvvCvc è 3, e il valore effettivo è null
        assertFalse(cartaService.isCodiceCvvCvcUguale3Cifre(null));
    }

    @Test
    void checkRegexPerNCifreTest() {
        //test del caso in cui il valore atteso della regex per n cifre è n, e il valore effettivo è n
        assertTrue(cartaService.checkRegexPerNCifre(3, "123"));
        //test del caso in cui il valore atteso della regex per n cifre è n, e il valore effettivo è n+1
        assertFalse(cartaService.checkRegexPerNCifre(3, "1234"));
        //test del caso in cui il valore atteso della regex per n cifre è n, e il valore effettivo è n-1
        assertFalse(cartaService.checkRegexPerNCifre(4, "123"));
        //test del caso in cui il valore atteso della regex per n cifre è n, e il valore effettivo è n caratteri
        assertFalse(cartaService.checkRegexPerNCifre(4, "abcd"));
        //test del caso in cui il valore atteso della regex per n cifre è n, e il valore effettivo è una stringa con caratteri
        assertFalse(cartaService.checkRegexPerNCifre(3, "a1c"));
        //test del caso in cui il valore atteso della regex per n cifre è -n, e il valore effettivo è n
        assertFalse(cartaService.checkRegexPerNCifre(-3, "123"));
        //test del caso in cui il valore atteso della regex per n cifre è n > 0, e il valore effettivo di n è 0
        assertFalse(cartaService.checkRegexPerNCifre(0, "123"));
        //test del caso in cui il valore atteso della regex per n cifre è n, e il valore effettivo è null
        assertFalse(cartaService.checkRegexPerNCifre(3, null));
    }

}
