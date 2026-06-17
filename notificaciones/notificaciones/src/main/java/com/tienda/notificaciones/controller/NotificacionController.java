package com.tienda.notificaciones.controller;
import com.tienda.notificaciones.dto.NotificacionDTO;
import com.tienda.notificaciones.service.NotificacionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@Valid @RequestBody NotificacionDTO notificacion) {
        service.enviarEmail(notificacion);
        return ResponseEntity.ok("Notificación enviada correctamente");
    }
}