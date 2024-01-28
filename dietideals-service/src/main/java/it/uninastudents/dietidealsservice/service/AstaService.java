package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.model.entity.Asta;
import it.uninastudents.dietidealsservice.model.entity.enums.CategoriaAsta;
import it.uninastudents.dietidealsservice.model.entity.enums.TipoAsta;
import it.uninastudents.dietidealsservice.repository.AstaRepository;
import it.uninastudents.dietidealsservice.repository.specs.AstaSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AstaService {

    private final AstaRepository repository;

    public Asta salvaAsta(Asta asta) {
        return repository.save(asta);
    }

    public Page<Asta> getAll(Pageable pageable, String nome, TipoAsta tipo, CategoriaAsta categoria) {
        var finalName = nome != null ? "%".concat(nome.toUpperCase()).concat("%") : null;
        var spec = AstaSpecs.hasNome(finalName).or(AstaSpecs.hasTipo(tipo)).or(AstaSpecs.hasCategoria(categoria));
        return repository.findAll(spec, pageable);
    }

}