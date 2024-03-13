package it.uninastudents.dietidealsservice.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificaUtilsTest {

    @Test
    void buildMessaggioOffertaSuperataTest() {
        String nomeAsta = "Asta di prova";
        BigDecimal prezzo = new BigDecimal("123.45");
        String result = NotificaUtils.buildMessaggioOffertaSuperata(nomeAsta, prezzo);
        assertEquals("La tua offerta per Asta di prova è stata superata per 123.45€", result);
    }

    @Test
    void buildMessaggioAstaTerminataSenzaOfferteTest() {
        String nomeAsta = "Asta di prova";
        String result = NotificaUtils.buildMessaggioAstaTerminataSenzaOfferte(nomeAsta);
        assertEquals("La tua asta Asta di prova è terminata senza offerte", result);
    }


    @Test
    void testBuildMessaggioAstaTerminataUtenteVincitore() {
        String nomeAsta = "Asta di prova";
        BigDecimal prezzo = new BigDecimal("123.45");
        String result = NotificaUtils.buildMessaggioAstaTerminataUtenteVincitore(nomeAsta, prezzo);
        assertEquals("Congratulazioni! Ti sei aggiudicato l'asta Asta di prova per 123.45€", result);
    }

    @Test
    void testBuildMessaggioAstaTerminataUtenteNonVincitore() {
        String nomeAsta = "Asta di prova";
        String result = NotificaUtils.buildMessaggioAstaTerminataUtenteNonVincitore(nomeAsta);
        assertEquals("L'asta Asta di prova a cui hai partecipato è terminata", result);
    }

    @Test
    void testBuildMessaggioAstaTerminataProprietario() {
        String nomeAsta = "Asta di prova";
        String result = NotificaUtils.buildMessaggioAstaTerminataProprietario(nomeAsta);
        assertEquals("La tua asta Asta di prova è terminata", result);
    }


}
