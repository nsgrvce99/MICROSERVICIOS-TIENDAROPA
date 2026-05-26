package com.tienda.pedidos.controller;

import com.tienda.pedidos.dto.CheckoutRequestDTO;
import com.tienda.pedidos.service.OrquestadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final OrquestadorService orquestador;

    public CheckoutController(OrquestadorService orquestador) {
        this.orquestador = orquestador;
    }

    @PostMapping("/procesar")
    public ResponseEntity<String> procesarCheckout(@RequestBody CheckoutRequestDTO request) {
        String resultado = orquestador.procesarCompraFinal(request);
        return ResponseEntity.ok(resultado);
    }
}