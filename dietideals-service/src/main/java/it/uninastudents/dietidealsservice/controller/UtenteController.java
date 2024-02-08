package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.mapper.UtenteMapper;
import it.uninastudents.dietidealsservice.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final UtenteMapper utenteMapper;

    @GetMapping("/GetUser")
    private String GetUser(){
        return "mammt";
    }
}
