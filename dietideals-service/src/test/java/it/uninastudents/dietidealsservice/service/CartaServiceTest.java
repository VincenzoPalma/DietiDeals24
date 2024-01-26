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
    void isDataScadenzaFuturaTest(){
        assertTrue(cartaService.isDataScadenzaFutura(LocalDate.now().plusYears(3)));
        assertFalse(cartaService.isDataScadenzaFutura(LocalDate.now().minusYears(1)));
        assertFalse(cartaService.isDataScadenzaFutura(LocalDate.now()));
        assertFalse(cartaService.isDataScadenzaFutura(null));
    }

    @Test
    void checkCartaTest(){
        Carta cartaTest1 = new Carta("1234567890123456", "Luigi Test", "123", LocalDate.now().plusYears(1), new Utente());
        Carta cartaTest2 = new Carta("1234567890123457", "Paolo Test", "124", LocalDate.now().plusYears(2), null);
        Carta cartaTest3 = new Carta("1234567890123458", null, "125", LocalDate.now().plusYears(3), new Utente());
        assertTrue(cartaService.checkCarta(cartaTest1));
        assertFalse(cartaService.checkCarta(cartaTest2));
        assertFalse(cartaService.checkCarta(cartaTest3));
        assertFalse(cartaService.checkCarta(null));
    }

    @Test
    void isNumeroUguale16Test(){
        assertTrue(cartaService.isNumeroUguale16Caratteri("1234567890123456"));
        assertFalse(cartaService.isNumeroUguale16Caratteri("1234"));
        assertFalse(cartaService.isNumeroUguale16Caratteri("12345678901234567"));
        assertFalse(cartaService.isNumeroUguale16Caratteri(null));
    }

    @Test
    void isCodiceCvvCvcUguale3CifreTest(){
        assertTrue(cartaService.isCodiceCvvCvcUguale3Cifre("123"));
        assertFalse(cartaService.isCodiceCvvCvcUguale3Cifre("1234"));
        assertFalse(cartaService.isCodiceCvvCvcUguale3Cifre("12"));
        assertFalse(cartaService.isCodiceCvvCvcUguale3Cifre(null));
    }

    @Test
    void checkRegexPerNCifreTest(){
        assertTrue(cartaService.checkRegexPerNCifre(3,"123"));
        assertFalse(cartaService.checkRegexPerNCifre(3, "1234"));
        assertFalse(cartaService.checkRegexPerNCifre(4,"123"));
        assertFalse(cartaService.checkRegexPerNCifre(4, "abcd"));
        assertFalse(cartaService.checkRegexPerNCifre(3,"a1c"));
        assertFalse(cartaService.checkRegexPerNCifre(-3,"123"));
        assertFalse(cartaService.checkRegexPerNCifre(0,"123"));
        assertFalse(cartaService.checkRegexPerNCifre(3,null));
    }

    void isNumeroUguale16CaratteriTest(){

    }
}
