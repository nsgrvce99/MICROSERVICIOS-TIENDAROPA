package com.tienda.notificaciones.service;
import com.tienda.notificaciones.dto.NotificacionDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    public void enviarEmail(NotificacionDTO notificacion) {
        System.out.println("=====================================");
        System.out.println("ENVIANDO CORREO A: " + notificacion.getEmailDestino());
        System.out.println("ASUNTO: " + notificacion.getAsunto());
        System.out.println("MENSAJE: " + notificacion.getMensaje());
        System.out.println("=====================================");
    }
}