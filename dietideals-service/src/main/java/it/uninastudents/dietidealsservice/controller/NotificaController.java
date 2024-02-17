package it.uninastudents.dietidealsservice.controller;

import it.uninastudents.dietidealsservice.model.entity.Notifica;
import it.uninastudents.dietidealsservice.service.NotificaService;
import it.uninastudents.dietidealsservice.utils.ControllerUtils;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificaController {

    private final NotificaService notificaService;

    @GetMapping("/utente/notifiche")
    public ResponseEntity<Page<Notifica>> getNotificheUtente(@RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
                                                             @RequestParam(name = "size", defaultValue = "12") @Min(1) int size) {
        Pageable pageable = ControllerUtils.pageableBuilder(page, size, Sort.by("creationDate").ascending());
        return ResponseEntity.ok(notificaService.findAllNotificheUtente(pageable));
    }

    @DeleteMapping("/utente/notifiche")
    public ResponseEntity<Void> deleteNotifiche(){
        notificaService.cancellaNotificheUtente();
        return ResponseEntity.noContent().build();
    }
}
