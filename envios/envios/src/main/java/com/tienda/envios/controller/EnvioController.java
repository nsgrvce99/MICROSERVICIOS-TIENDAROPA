package com.tienda.envios.controller;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.envios.dto.EnvioDTO;
import com.tienda.envios.model.Envio;
import com.tienda.envios.service.EnvioService;// Permite la inyección automática de dependencias

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/envios")
public class EnvioController {
    private final EnvioService service;
    public EnvioController(EnvioService service) { this.service = service; }
     @Operation(summary = "Generar un nuevo envío")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Envío generado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") 
    })
    @PostMapping("/generar")
    public ResponseEntity<EnvioDTO> generar(@Valid @RequestBody Envio envio) {
        Envio nuevo = service.generarEnvio(envio);
        return ResponseEntity.status(201).body(convertir(nuevo));
    }   
     @Operation(summary = "Rastrear un envío")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío encontrado"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") 
    })
    @GetMapping("/rastreo/{pedidoId}")
    public ResponseEntity<EnvioDTO> rastrear(@PathVariable Integer pedidoId) {
        Optional<Envio> envioOpt = service.rastrear(pedidoId);
        return envioOpt.map(envio -> ResponseEntity.ok(convertir(envio)))
        
        .orElse(ResponseEntity.notFound().build());
    }

    private EnvioDTO convertir(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setPedidoId(envio.getPedidoId());
        dto.setDireccion(envio.getDireccion());
        dto.setCourier(envio.getCourier());
        dto.setEstado(envio.getEstado());
        return dto;
    }
}