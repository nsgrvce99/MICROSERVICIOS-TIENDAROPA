package com.tienda.pagos.controller;

import com.tienda.pagos.dto.PagoRespuestaDTO;
import com.tienda.pagos.model.Pago;
import com.tienda.pagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @PostMapping("/procesar")
    public ResponseEntity<PagoRespuestaDTO> procesar(@Valid @RequestBody Pago pago) {
        Pago nuevoPago = service.procesarPago(pago);
        
        PagoRespuestaDTO dto = new PagoRespuestaDTO();
        dto.setPedidoId(nuevoPago.getPedidoId());
        dto.setMonto(nuevoPago.getMonto());
        dto.setEstado(nuevoPago.getEstado());
        dto.setFechaPago(nuevoPago.getFechaPago());

        return ResponseEntity.status(201).body(dto);
    }
}