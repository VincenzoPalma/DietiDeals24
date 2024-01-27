package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UtenteService {
    private UtenteRepository utenteRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public UtenteService() {
        //costruttore vuoto
    }

    public void deleteUtenteByUsername(String username) {
        if (username != null) {
            utenteRepository.deleteById(username);
        }
    }
}
