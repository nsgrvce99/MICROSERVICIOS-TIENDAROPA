package com.tienda.pedidos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.pedidos.dto.CheckoutRequestDTO;
import com.tienda.pedidos.service.OrquestadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final OrquestadorService orquestador;

    public CheckoutController(OrquestadorService orquestador) {
        this.orquestador = orquestador;
    }

    @Operation(
        summary = "Procesar checkout",
        description = "Procesa el checkout para un pedido específico"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Checkout procesado exitosamente")  ,
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
    @PostMapping("/procesar")
    public ResponseEntity<String> procesarCheckout(@RequestBody CheckoutRequestDTO request) {
        String resultado = orquestador.procesarCompraFinal(request);
        return ResponseEntity.ok(resultado);
    }
}