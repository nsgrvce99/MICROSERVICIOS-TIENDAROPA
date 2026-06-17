package com.tienda.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.inventario.dto.StockDTO;
import com.tienda.inventario.model.Stock;
import com.tienda.inventario.service.StockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class StockController {
    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }
    @Operation(
        summary = "Agregar stock de una prenda",
        description = "Agrega stock a una prenda específica"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Stock agregado exitosamente")  ,
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
    @PostMapping("/agregar")
    public ResponseEntity<StockDTO> agregarStock(@Valid @RequestBody Stock stock) {
        Stock nuevo = service.inicializarStock(stock);
        return ResponseEntity.status(201).body(convertirADTO(nuevo));
    }

    @Operation(
        summary = "Verificar disponibilidad de stock",
        description = "Verifica si hay suficiente stock disponible para una prenda específica"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Disponibilidad verificada correctamente")  ,
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
    @GetMapping("/verificar/{prendaId}/{cantidad}")
    public ResponseEntity<Boolean> verificar(@PathVariable Integer prendaId, @PathVariable Integer cantidad) {
        return ResponseEntity.ok(service.verificarDisponibilidad(prendaId, cantidad));
    }
        @Operation(
            summary = "Descontar stock de una prenda",
            description = "Descuenta una cantidad específica del stock de una prenda"
            )
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock descontado exitosamente")  ,
            @ApiResponse(responseCode = "400", description = "Datos invalidos o stock insuficiente")  ,
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
            })
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