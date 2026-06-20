package com.tienda.pagos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.pagos.dto.PagoRespuestaDTO;
import com.tienda.pagos.model.Pago;
import com.tienda.pagos.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
 

@RestController
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @Operation(
        summary = "Procesar un pago",
        description = "Procesa un pago para un pedido específico"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago procesado exitosamente")  ,
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
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