package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.id.IdRelazioneAstaUtente;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

@Service
@Transactional
public class OffertaService {
    private OffertaRepository offertaRepository;

    @Autowired
    public OffertaService(OffertaRepository offertaRepository) {
        this.offertaRepository = offertaRepository;
    }

    public OffertaService() {
        //costruttore vuoto
    }

    public void deleteOffertaById(IdRelazioneAstaUtente id) {
        if (id != null) {
            offertaRepository.deleteById(id);
        }
    }

    public Offerta salvaOfferta(Offerta offerta) {
        if (offerta == null || !checkPrezzoPositivo(offerta.getPrezzo())) {
            return null;
        }
        return offertaRepository.save(offerta);
    }

    public Set<Offerta> findOffertaByIdAsta(Integer idAsta) {
        if (idAsta == null) {
            return Collections.emptySet();
        }
        return offertaRepository.findOffertaByAsta_IdAsta(idAsta);
    }

    public Offerta findUltimaOffertaByIdAsta(Integer idAsta) {
        if (idAsta == null) {
            return null;
        }
        return offertaRepository.findUltimaOffertaByAsta_IdAsta(idAsta);
    }

    public boolean checkPrezzoPositivo(BigDecimal prezzo) {
        if (prezzo == null) {
            return false;
        }
        return prezzo.compareTo(BigDecimal.valueOf(0)) > 0;
    }

}
