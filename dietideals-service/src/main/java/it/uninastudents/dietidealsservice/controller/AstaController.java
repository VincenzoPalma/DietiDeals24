package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.service.AstaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AstaController {

    private final AstaService astaService;



}
