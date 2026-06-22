package com.tienda.notificaciones.service;
import com.tienda.notificaciones.dto.NotificacionDTO;
import com.tienda.notificaciones.model.notificaciones;
import com.tienda.notificaciones.repository.NotificacionesRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @Autowired 
    private NotificacionesRepository repository;
    public void enviarEmail(NotificacionDTO notificacion) {
        System.out.println("=====================================");
        System.out.println("ENVIANDO CORREO A: " + notificacion.getEmailDestino());
        System.out.println("ASUNTO: " + notificacion.getAsunto());
        System.out.println("MENSAJE: " + notificacion.getMensaje());
        System.out.println("=====================================");
    }
    	public List<notificaciones> listar() {
        return repository.findAll();
    }
	public Optional<notificaciones> buscarPorId(Integer id) {
        return repository.findById(id);
    }
	public notificaciones guardarNotificacion(notificaciones notificacion) {
        return repository.save(notificacion);
    }
	public String eliminarPorId(Integer id) {
       List<notificaciones> lista = repository.findAll();
       for (notificaciones n : lista) {
           if (n.getId().equals(id)) {
               repository.deleteById(id);
               return "Prenda eliminada correctamente";
           }

       }
       return "Prenda no encontrada";
   }
	public notificaciones actualizarNotificacion(Integer id, notificaciones notificacion) {
    notificacion.setId(id); 
    return repository.save(notificacion);
    }

}