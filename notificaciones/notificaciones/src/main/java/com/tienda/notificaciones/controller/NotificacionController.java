package com.tienda.notificaciones.controller;
import com.tienda.notificaciones.dto.NotificacionDTO;
import com.tienda.notificaciones.model.notificaciones;
import com.tienda.notificaciones.service.NotificacionService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(
        summary = "Listar notificaciones",
        description = "Obtiene una lista con todas las notificaciones registradas en el sistema"
)
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
})

    @GetMapping("/listar") 
    public ResponseEntity<List<notificaciones>> listar() {
    List<notificaciones> lista = service.listar(); 
    return ResponseEntity.ok(lista);
    }


    @Operation(
        summary = "Buscar notificación por ID",
        description = "Obtiene una notificación específica por su ID"
)
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
})
    @GetMapping("/buscar/{id}")
    public Optional<notificaciones> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
    @Operation(summary = "Agregar una notificación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificación agregada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") 
    })
    @PostMapping("/agregar")
    public ResponseEntity<notificaciones> agregar(@Valid @RequestBody notificaciones notificacion) {
        notificaciones guardado = service.guardarNotificacion(notificacion);
        return ResponseEntity.status(201).body(guardado);
    }
    @Operation(summary = "eliminar una notificación por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") 
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        service.eliminarPorId(id);
        return ResponseEntity.ok("Notificación eliminada correctamente");
    }

}