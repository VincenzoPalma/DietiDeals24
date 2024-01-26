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
import java.util.Collections;
import java.util.Set;

@Service
@Transactional
public class AstaService {
    private AstaRepository astaRepository;
    private static final ArrayList<String> listaCategorie = new ArrayList<String>();

    //inizializzazione arraylist categorie
    static {
        listaCategorie.add("Elettronica");
        listaCategorie.add("Informatica");
        listaCategorie.add("Giocattoli");
        listaCategorie.add("Alimentari");
        listaCategorie.add("Servizi");
        listaCategorie.add("Arredamento");
        listaCategorie.add("Auto e moto");
        listaCategorie.add("Libri");
        listaCategorie.add("Abbigliamento");
        listaCategorie.add("Attrezzi e utensili");
        listaCategorie.add("Bellezza");
        listaCategorie.add("Musica e arte");
    }

    @Autowired
    public AstaService(AstaRepository astaRepository) {
        this.astaRepository = astaRepository;
    }

    public AstaService(){
        //costruttore vuoto
    }

    public Asta salvaAsta(Asta asta) {
        if (!checkDatiAsta(asta)) return null;
        return astaRepository.save(asta);
    }

    public void cancellaAstaById(Integer idAsta){
        if (idAsta != null){
            astaRepository.deleteById(idAsta);
        }
    }

    public Set<Asta> findAsteAttive(){
        return astaRepository.findAsteAttiveOrderByDataScadenzaAsc();
    }

    public Set<Asta> findAsteByParolaChiave(String parolaChiave){
    if(parolaChiave == null || parolaChiave.isEmpty()){
            return Collections.emptySet();
        }
        return astaRepository.findAstePerParolaChiaveOrderByDataScadenzaAsc(parolaChiave);
    }

    public Set<Asta> findAsteAttiveByTipo(String tipo){
        if(tipo == null){
            return Collections.emptySet();
        }
        return astaRepository.findAsteAttiveByTipoOrderByDataScadenzaAsc(tipo);
    }

    public Set<Asta> findAsteAttiveByCategoria(String categoria){
        if (!isCategoriaValida(categoria)) return Collections.emptySet();
        return astaRepository.findAsteAttiveByCategoriaOrderByDataScadenzaAsc(categoria);
    }

    public Set<Asta> findAsteAttiveByProprietario(String username){
        if(username == null){
            return Collections.emptySet();
        }
        return astaRepository.findAsteAttiveByProprietario(username);
    }

    public Set<Asta> findAsteTerminateByProprietario(String username){
        if(username == null){
            return Collections.emptySet();
        }
        return astaRepository.findAsteTerminateByProprietario(username);
    }

    public Set<Asta> findAsteSeguiteByProprietario(String username){
        if(username == null){
            return Collections.emptySet();
        }
        return astaRepository.findAsteSeguiteByProprietario(username);
    }

    public Set<Asta> findAsteVinteByProprietario(String username){
        if(username == null){
            return Collections.emptySet();
        }
        return astaRepository.findAsteVinteByProprietario(username);
    }

    public boolean checkDatiAsta(Asta asta) {
        if (asta == null){
            return false;
        }
        String tipoAsta = asta.getTipo();
        if (tipoAsta.equals("Inglese")){
            if (!checkSogliaRialzoPositiva(asta.getSogliaRialzo())) return false;
            if (!checkIntervalloTempoOffertaMin30minutiMax3ore(asta.getIntervalloTempoOfferta())) return false;
        } else if (tipoAsta.equals("Silenziosa") || tipoAsta.equals("Inversa")) {
            if (!checkDataScadenzaMin24oreMax30giorni(asta.getDataScadenza())) return false;
        } else {
            return false;
        }
        if (!isStatoAttivaOrTerminata(asta.getStato())) return false;
        if (!isDescrizioneMinoreUguale300Caratteri(asta.getDescrizione())) return false;
        if (!isCategoriaValida(asta.getCategoria())) return false;
        return true;
    }

    public boolean checkDataScadenzaMin24oreMax30giorni(LocalDateTime dataScadenza) {
        if (dataScadenza == null){
            return false;
        }
        return dataScadenza.isBefore(LocalDateTime.now().plusDays(31)) && dataScadenza.isAfter(LocalDateTime.now().plusHours(23));
    }

    public boolean checkIntervalloTempoOffertaMin30minutiMax3ore(Duration intervalloTempoOfferta) {
        if (intervalloTempoOfferta == null){
            return false;
        }
        return intervalloTempoOfferta.compareTo(Duration.ofHours(3)) <= 0 && intervalloTempoOfferta.compareTo(Duration.ofMinutes(30)) >= 0;
    }

    public boolean checkSogliaRialzoPositiva(BigDecimal sogliaRialzo) {
        if (sogliaRialzo == null){
            return false;
        }
        return sogliaRialzo.compareTo(BigDecimal.valueOf(0)) > 0;
    }

    public boolean isStatoAttivaOrTerminata(String stato) {
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