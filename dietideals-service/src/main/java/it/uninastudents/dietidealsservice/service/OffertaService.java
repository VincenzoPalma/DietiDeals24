package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Offerta;
import it.uninastudents.dietidealsservice.repository.OffertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OffertaService {

    private final OffertaRepository offertaRepository;

    public Offerta salvaOfferta(Offerta offerta) {
        return offertaRepository.save(offerta);
    }


}
