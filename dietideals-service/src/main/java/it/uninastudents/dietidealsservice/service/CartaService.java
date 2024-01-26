package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Carta;
import it.uninastudents.dietidealsservice.repository.CartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CartaService {
    private CartaRepository cartaRepository;

    @Autowired
    public CartaService(CartaRepository cartaRepository){
        this.cartaRepository = cartaRepository;
    }

    public CartaService() {
        //costruttore vuoto
    }

    public void deleteCartaByNumero(String numeroCarta){
        if (numeroCarta != null){
            cartaRepository.deleteById(numeroCarta);
        }
    }

    public Set<Carta> findCarteByUtenteUsername(String username){
        if (username == null){
            return Collections.emptySet();
        }
        return cartaRepository.findCarteByUtenteUsername(username);
    }

    public Carta salvaCarta(Carta carta){
        if (!checkCarta(carta)){
            return null;
        }
        return cartaRepository.save(carta);
    }

    public boolean checkCarta(Carta carta){
        if (carta == null){
            return false;
        }
        if (carta.getNomeTitolare() == null || carta.getUtente() == null){
            return false;
        }
        if (!isNumeroUguale16Caratteri(carta.getNumero())){
            return false;
        }
        if (!isCodiceCvvCvcUguale3Cifre(carta.getCodiceCvvCvc())){
            return false;
        }
        if (!isDataScadenzaFutura(carta.getDataScadenza())){
            return false;
        }
        return true;
    }

    public boolean isNumeroUguale16Caratteri(String numeroCarta){
        return checkRegexPerNCifre(16, numeroCarta);
    }

    public boolean isCodiceCvvCvcUguale3Cifre(String codiceCvvCvc){
        return checkRegexPerNCifre(3, codiceCvvCvc);
    }

    public boolean checkRegexPerNCifre(int n, String stringa){
        if (stringa == null || n <= 0){
            return false;
        }
        String regex = "^[0-9]{"+ n +"}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringa);
        return matcher.matches();
    }

    public boolean isDataScadenzaFutura(LocalDate dataScadenza){
        if (dataScadenza == null){
            return false;
        }
        return dataScadenza.isAfter(LocalDate.now());
    }
}
