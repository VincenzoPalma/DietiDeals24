package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional
public class AstaService {
    private AstaRepository astaRepository;
    private final ArrayList<String> listaCategorie = new ArrayList<String>();

    @Autowired
    public AstaService(AstaRepository astaRepository) {
        this.astaRepository = astaRepository;
        ArrayList<String> newListaCategorie = new ArrayList<String>();
        newListaCategorie.add("Elettronica");
        newListaCategorie.add("Informatica");
        newListaCategorie.add("Giocattoli");
        newListaCategorie.add("Alimentari");
        newListaCategorie.add("Servizi");
        newListaCategorie.add("Arredamento");
        newListaCategorie.add("Auto e moto");
        newListaCategorie.add("Libri");
        newListaCategorie.add("Abbigliamento");
        newListaCategorie.add("Attrezzi e utensili");
        newListaCategorie.add("Bellezza");
        newListaCategorie.add("Musica e arte");
    }

    public AstaService() {
    }

    public Asta salvaAsta(Asta asta) {
        if (!checkDatiAsta(asta)) return null;
        return astaRepository.save(asta);
    }

    public boolean checkDatiAsta(Asta asta) {
        if (asta == null){
            return false;
        }
        String tipoAsta = asta.getTipo();
        if (tipoAsta.equals("Inglese")){
            if (!checkSogliaRialzoPositiva(asta)) return false;
            if (!checkIntervalloTempoOffertaMin30minutiMax3ore(asta)) return false;
        } else if (tipoAsta.equals("Silenziosa") || tipoAsta.equals("Inversa")) {
            if (!checkDataScadenzaMin24oreMax30giorni(asta)) return false;
        }
        if (!isStatoAttivoOTerminato(asta.getStato())) return false;
        if (!isDescrizioneMinoreUguale300Caratteri(asta.getDescrizione())) return false;
        if (!isCategoriaValida(asta.getCategoria())) return false;
        return true;
    }

    public boolean checkDataScadenzaMin24oreMax30giorni(Asta asta) {
        if (asta == null){
            return false;
        }
        return asta.getDataScadenza().isBefore(LocalDateTime.now().plusDays(30)) && asta.getDataScadenza().isAfter(LocalDateTime.now().plusHours(24));
    }

    public boolean checkIntervalloTempoOffertaMin30minutiMax3ore(Asta asta) {
        if (asta == null){
            return false;
        }
        return asta.getIntervalloTempoOfferta().compareTo(Duration.ofHours(3)) < 0 && asta.getIntervalloTempoOfferta().compareTo(Duration.ofMinutes(30)) > 0;
    }

    public boolean checkSogliaRialzoPositiva(Asta asta) {
        if (asta == null){
            return false;
        }
        return asta.getSogliaRialzo().compareTo(BigDecimal.valueOf(0)) > 0;
    }

    public boolean isStatoAttivoOTerminato(String stato) {
        if (stato == null){
            return false;
        }
        return stato.equals("Attiva") || stato.equals("Terminata");
    }

    public boolean isDescrizioneMinoreUguale300Caratteri(String descrizione) {
        if (descrizione == null){
            return false;
        }
        return descrizione.length() <= 300;
    }

    public boolean isCategoriaValida(String categoria) {
        if (categoria == null){
            return false;
        }
        return listaCategorie.contains(categoria);
    }
}