package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.ContoCorrente;
import it.uninastudents.dietidealsservice.repository.ContoCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContoCorrenteService {
    private ContoCorrenteRepository contoCorrenteRepository;

    @Autowired
    public ContoCorrenteService(ContoCorrenteRepository contoCorrenteRepository) { this.contoCorrenteRepository= contoCorrenteRepository; }

    public ContoCorrenteService(){
        //costruttore vuoto
    }

    public void deleteContocorrenteByIban(String iban){
        if (iban != null){
            contoCorrenteRepository.deleteById(iban);
        }
    }

    public ContoCorrente salvaContoCorrente(ContoCorrente contoCorrente){
        if(!checkContoCorrente(contoCorrente)){
            return null;
        }
        //return ContoCorrenteRepository.save(contoCorrente);
    }

    public boolean checkContoCorrente(ContoCorrente contoCorrente){
        if(contoCorrente==null){
            return false;
        }
        if(contoCorrente.getNomeTitolare()==null || contoCorrente.getUtente()==null){
            return false;
        }


        return true;
    }

    //da vedere regex per controlli iban e codice bic swift
}
