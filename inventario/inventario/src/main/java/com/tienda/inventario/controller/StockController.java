package com.tienda.inventario.controller;

import com.tienda.inventario.dto.StockDTO;
import com.tienda.inventario.model.Stock;
import com.tienda.inventario.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class StockController {
    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @PostMapping("/agregar")
    public ResponseEntity<StockDTO> agregarStock(@Valid @RequestBody Stock stock) {
        Stock nuevo = service.inicializarStock(stock);
        return ResponseEntity.status(201).body(convertirADTO(nuevo));
    }

    @GetMapping("/verificar/{prendaId}/{cantidad}")
    public ResponseEntity<Boolean> verificar(@PathVariable Integer prendaId, @PathVariable Integer cantidad) {
        return ResponseEntity.ok(service.verificarDisponibilidad(prendaId, cantidad));
    }

    @PutMapping("/descontar/{prendaId}/{cantidad}")
    public ResponseEntity<StockDTO> descontar(@PathVariable Integer prendaId, @PathVariable Integer cantidad) {
        Stock actualizado = service.descontarStock(prendaId, cantidad);
        if (actualizado != null) {
            return ResponseEntity.ok(convertirADTO(actualizado));
        }
        return ResponseEntity.badRequest().build();
    }

    private StockDTO convertirADTO(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setPrendaId(stock.getPrendaId());
        dto.setCantidadDisponible(stock.getCantidadDisponible());
        return dto;
    }
}