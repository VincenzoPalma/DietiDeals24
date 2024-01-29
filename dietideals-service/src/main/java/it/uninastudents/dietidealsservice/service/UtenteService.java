package it.uninastudents.dietidealsservice.service;

import it.uninastudents.dietidealsservice.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteService {
    private final UtenteRepository repository;


}
