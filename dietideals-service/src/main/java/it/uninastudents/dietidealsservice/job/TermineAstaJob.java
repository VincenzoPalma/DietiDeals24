package it.uninastudents.dietidealsservice.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.StatoOfferta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import it.uninastudents.dietidealsservice.repository.specs.OffertaSpecs;
import it.uninastudents.dietidealsservice.service.NotificaService;
import it.uninastudents.dietidealsservice.utils.NotificaUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

@RequiredArgsConstructor
public class TermineAstaJob implements Job {

    private final ObjectMapper objectMapper;
    private final OffertaRepository offertaRepository;
    private final AstaRepository astaRepository;
    private final NotificaService notificaService;


    @Override
    public void execute(JobExecutionContext context) {
        String astaJson = context.getJobDetail().getJobDataMap().getString("asta");
        Asta asta = null;
        try {
            asta = objectMapper.readValue(astaJson,Asta.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        asta.setStato(StatoAsta.TERMINATA);
        astaRepository.save(asta);
        var spec = OffertaSpecs.hasAsta(asta.getId());
        List<Offerta> offerte = offertaRepository.findAll(spec);
        System.out.println(offerte.isEmpty());
        if (offerte.isEmpty()){
            Notifica notifica = new Notifica();
            notifica.setUtente(asta.getProprietario());
            notifica.setAsta(asta);
            notifica.setContenuto(NotificaUtils.buildMessaggioAstaTerminataSenzaOfferte(asta.getNome()));
            notificaService.salvaNotifica(notifica, asta.getProprietario().getId());
        } else {
            for(Offerta offerta : offerte){
                Notifica notifica = new Notifica();
                notifica.setUtente(offerta.getUtente());
                notifica.setAsta(asta);
                if (offerta.getStato().equals(StatoOfferta.VINCENTE)){
                    notifica.setContenuto(NotificaUtils.buildMessaggioAstaTerminataUtenteVincitore(asta.getNome(), offerta.getPrezzo()));
                } else {
                    notifica.setContenuto(NotificaUtils.buildMessaggioAstaTerminataUtenteNonVincitore(asta.getNome()));
                    if (asta.getTipo().equals(TipoAsta.SILENZIOSA)){
                        offerta.setStato(StatoOfferta.RIFIUTATA);
                        offertaRepository.save(offerta);
                    }
                }
                notifica = notificaService.salvaNotifica(notifica, asta.getProprietario().getId());
            }
        }
    }
}
