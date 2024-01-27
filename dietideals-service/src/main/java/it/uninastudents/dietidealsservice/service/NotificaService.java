package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.id.IdRelazioneAstaUtente;
import it.uninastudents.dietidealsservice.repository.NotificaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Service
@Transactional
public class NotificaService {
    private NotificaRepository notificaRepository;

    @Autowired
    public NotificaService(NotificaRepository notificaRepository) {
        this.notificaRepository = notificaRepository;
    }

    public NotificaService() {
        //costruttore vuoto
    }

    public void deleteNotificaById(IdRelazioneAstaUtente id) {
        if (id != null) {
            notificaRepository.deleteById(id);
        }
    }

    public Notifica salvaNotifica(Notifica notifica) {
        if (notifica == null) {
            return null;
        }
        return notificaRepository.save(notifica);
    }

    public Set<Notifica> findNotificaByUsername(String username) {
        if (username == null) {
            return Collections.emptySet();
        }
        return notificaRepository.findNotificaByUtenteUsername(username);
    }


}
