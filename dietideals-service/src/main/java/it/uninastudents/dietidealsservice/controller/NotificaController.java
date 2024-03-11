package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.service.NotificaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificaController {

    private final NotificaService notificaService;

    @GetMapping("/utente/notifiche")
    public ResponseEntity<List<Notifica>> getNotificheUtente() {
        return ResponseEntity.ok(notificaService.findAllNotificheUtente());
    }

    @DeleteMapping("/utente/notifiche")
    public ResponseEntity<Void> deleteNotifiche() {
        notificaService.cancellaNotificheUtente();
        return ResponseEntity.noContent().build();
    }
}
