package com.tienda.envios.controller;
import com.tienda.envios.dto.EnvioDTO;
import com.tienda.envios.model.Envio;
import com.tienda.envios.service.EnvioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/envios")
public class EnvioController {
    private final EnvioService service;
    public EnvioController(EnvioService service) { this.service = service; }

    @PostMapping("/generar")
    public ResponseEntity<EnvioDTO> generar(@Valid @RequestBody Envio envio) {
        Envio nuevo = service.generarEnvio(envio);
        return ResponseEntity.status(201).body(convertir(nuevo));
    }

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