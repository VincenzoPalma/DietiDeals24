package it.uninastudents.dietidealsservice.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(astaService.isStatoAttivo("Attiva"));
        assertTrue(astaService.isStatoAttivo("Terminata"));
        assertFalse(astaService.isStatoAttivo("In Corso"));
        assertFalse(astaService.isStatoAttivo(""));
        assertFalse(astaService.isStatoAttivo(null));
    }
}
