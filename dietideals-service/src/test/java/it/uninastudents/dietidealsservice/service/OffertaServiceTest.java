package it.uninastudents.dietidealsservice.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OffertaServiceTest {

    private final OffertaService offertaService = new OffertaService();

    @Test
    void checkPrezzoPositivoTest() {
        //test del caso in cui il valore atteso è un BigDecimal positivo, e il valore effettivo è un BigDecimal positivo
        assertTrue(offertaService.checkPrezzoPositivo(BigDecimal.valueOf(10)));
        //test del caso in cui il valore atteso è un BigDecimal positivo, e il valore effettivo è un BigDecimal negativo
        assertFalse(offertaService.checkPrezzoPositivo(BigDecimal.valueOf(-2)));
        //test del caso in cui il valore atteso è un BigDecimal positivo, e il valore effettivo è un BigDecimal pari a 0
        assertFalse(offertaService.checkPrezzoPositivo(BigDecimal.valueOf(0)));
        //test del caso in cui il valore atteso è un BigDecimal positivo, e il valore effettivo è null
        assertFalse(offertaService.checkPrezzoPositivo(null));
    }
}
