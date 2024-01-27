package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ContoCorrenteService {
    private ContoCorrenteRepository contoCorrenteRepository;

    @Autowired
    public ContoCorrenteService(ContoCorrenteRepository contoCorrenteRepository) {
        this.contoCorrenteRepository = contoCorrenteRepository;
    }

    public ContoCorrenteService() {
        //costruttore vuoto
    }

    public void deleteContoCorrenteByIban(String iban) {
        if (iban != null) {
            contoCorrenteRepository.deleteById(iban);
        }
    }

    public ContoCorrente salvaContoCorrente(ContoCorrente contoCorrente) {
        if (!checkContoCorrente(contoCorrente)) {
            return null;
        }
        return contoCorrenteRepository.save(contoCorrente);
    }

    public ContoCorrente findContoCorrenteByUsername(String username) {
        if (username == null) {
            return null;
        }
        return contoCorrenteRepository.findContoCorrenteByUtenteUsername(username);
    }

    public boolean checkContoCorrente(ContoCorrente contoCorrente) {
        if (contoCorrente == null) {
            return false;
        }
        if (contoCorrente.getNomeTitolare() == null || contoCorrente.getUtente() == null) {
            return false;
        }
        if (!isIbanUguale27Caratteri(contoCorrente.getIban())) {
            return false;
        }
        return isCodiceBicSwiftUguale11Caratteri(contoCorrente.getCodiceBicSwift());
    }

    public boolean isIbanUguale27Caratteri(String iban) {
        return checkRegexPerNCaratteriAlfanumerici(iban, 27);
    }

    public boolean isCodiceBicSwiftUguale11Caratteri(String codiceBicSwift) {
        return checkRegexPerNCaratteriAlfanumerici(codiceBicSwift, 11);
    }

    public boolean checkRegexPerNCaratteriAlfanumerici(String string, int n) {
        if (string == null || n <= 0) {
            return false;
        }
        String regex = "^[a-zA-Z0-9]{" + n + "}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
