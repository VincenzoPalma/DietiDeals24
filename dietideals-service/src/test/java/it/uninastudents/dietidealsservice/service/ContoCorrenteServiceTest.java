package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.model.entity.Utente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContoCorrenteServiceTest {

    private final ContoCorrenteService contoCorrenteService = new ContoCorrenteService();

    @Test
    void checkRegexPerNCaratteriAlfanumericiTest() {
        //test del caso in cui il valore atteso della regex per n caratteri alfanumerici è n, e il valore effettivo è n caratteri alfanumerici
        assertTrue(contoCorrenteService.checkRegexPerNCaratteriAlfanumerici("a1b2c", 5));
        //test del caso in cui il valore atteso della regex per n caratteri alfanumerici è n, e il valore effettivo è n cifre
        assertTrue(contoCorrenteService.checkRegexPerNCaratteriAlfanumerici("1234", 4));
        //test del caso in cui il valore atteso della regex per n caratteri alfanumerici è n, e il valore effettivo è n caratteri
        assertTrue(contoCorrenteService.checkRegexPerNCaratteriAlfanumerici("abcdfeg", 7));
        //test del caso in cui il valore atteso della regex per n caratteri alfanumerici è n, e il valore effettivo è n-1 caratteri
        assertFalse(contoCorrenteService.checkRegexPerNCaratteriAlfanumerici("a1b2c", 4));
        //test del caso in cui il valore atteso della regex per n caratteri alfanumerici è n, e il valore effettivo è n+1 caratteri
        assertFalse(contoCorrenteService.checkRegexPerNCaratteriAlfanumerici("a1b2c", 6));
        //test del caso in cui il valore atteso della regex per n caratteri alfanumerici è n, e il valore effettivo di n è 0
        assertFalse(contoCorrenteService.checkRegexPerNCaratteriAlfanumerici("abc23", 0));
        //test del caso in cui il valore atteso della regex per n caratteri alfanumerici è n, e il valore effettivo è null
        assertFalse(contoCorrenteService.checkRegexPerNCaratteriAlfanumerici(null, 4));
    }

    @Test
    void isCodiceBicSwiftUguale11CaratteriTest() {
        //test del caso in cui il valore atteso dei caratteri del CodiceBicSwift è 11, e il valore effettivo è 11 caratteri
        assertTrue(contoCorrenteService.isCodiceBicSwiftUguale11Caratteri("a1b2c3d4g7h"));
        //test del caso in cui il valore atteso dei caratteri del CodiceBicSwift è 11, e il valore effettivo è 10 caratteri
        assertFalse(contoCorrenteService.isCodiceBicSwiftUguale11Caratteri("a1b2c3d4g7"));
        //test del caso in cui il valore atteso dei caratteri del CodiceBicSwift è 11, e il valore effettivo è 12 caratteri
        assertFalse(contoCorrenteService.isCodiceBicSwiftUguale11Caratteri("a1b2c3d4g7h3"));
        //test del caso in cui il valore atteso dei caratteri del CodiceBicSwift è 11, e il valore effettivo è null
        assertFalse(contoCorrenteService.isCodiceBicSwiftUguale11Caratteri(null));
    }

    @Test
    void isIbanUguale27CaratteriTest() {
        //test del caso in cui il valore atteso dei caratteri dell iban è 27, e il valore effettivo è 27 caratteri
        assertTrue(contoCorrenteService.isIbanUguale27Caratteri("A1b2C3d4E5f6G7h8I9J0K1L2M3N"));
        //test del caso in cui il valore atteso dei caratteri dell iban è 27, e il valore effettivo è 26 caratteri
        assertFalse(contoCorrenteService.isIbanUguale27Caratteri("a1b2c3d4g7hdf73h79dy28ifue"));
        //test del caso in cui il valore atteso dei caratteri dell iban è 27, e il valore effettivo è 28 caratteri
        assertFalse(contoCorrenteService.isIbanUguale27Caratteri("a1b2c3d4g7hdf73h79dy28ifue9r"));
        //test del caso in cui il valore atteso dei caratteri dell iban è 27, e il valore effettivo è null
        assertFalse(contoCorrenteService.isIbanUguale27Caratteri(null));
    }

    @Test
    void checkContoCorrenteTest() {
        ContoCorrente contoCorrenteTest1 = new ContoCorrente("A1b2C3d4E5f6G7h8I9J0K1L2M3N", "Pierluigi Esposito", "a1b2c3d4g7h", new Utente());
        ContoCorrente contoCorrenteTest2 = new ContoCorrente("A1b2C3d4E5f6G7h8I9J0K1L2M3L", null, "a1b2c3d4g7y", new Utente());
        ContoCorrente contoCorrenteTest3 = new ContoCorrente("A1b2C3d4E5f6G7h8I9J0K1L2M3S", "Piero Esposito", "a1b2c3d4g7a", null);
        //test del caso in cui il valore atteso è un oggetto ContoCorrente non nullo, con dati corretti e nomeTitolare e utente non nulli, il valore effettivo è un oggetto ContoCorrente non nullo, con dati corretti e nomeTitolare e utente non nulli
        assertTrue(contoCorrenteService.checkContoCorrente(contoCorrenteTest1));
        //test del caso in cui il valore atteso è un oggetto ContoCorrente non nullo, con dati corretti e nomeTitolare e utente non nulli, il valore effettivo è un oggetto ContoCorrente non nullo, con dati corretti e nomeTitolare nullo
        assertFalse(contoCorrenteService.checkContoCorrente(contoCorrenteTest2));
        //test del caso in cui il valore atteso è un oggetto ContoCorrente non nullo, con dati corretti e nomeTitolare e utente non nulli, il valore effettivo è un oggetto ContoCorrente non nullo, con dati corretti e utente nullo
        assertFalse(contoCorrenteService.checkContoCorrente(contoCorrenteTest3));
    }

}
