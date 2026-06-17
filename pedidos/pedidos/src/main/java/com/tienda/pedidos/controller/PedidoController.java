package com.tienda.pedidos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.pedidos.dto.PedidoDetalleDTO;
import com.tienda.pedidos.model.Pedido;
import com.tienda.pedidos.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }
    @Operation(
        summary = "Crear un nuevo pedido",
        description = "Crea un nuevo pedido con los detalles proporcionados"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")  ,
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
    @PostMapping("/agregar")
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody Pedido pedido) {
        Pedido nuevo = service.guardarPedido(pedido);
        return ResponseEntity.status(201).body(nuevo);
    }
    @Operation(
        summary = "Obtener detalle de un pedido",
        description = "Obtiene el detalle completo de un pedido específico"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle del pedido obtenido exitosamente")  ,
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
    @GetMapping("/{id}/detalle")
    public ResponseEntity<PedidoDetalleDTO> obtenerDetallePedido(@PathVariable Integer id) {
        PedidoDetalleDTO dto = service.obtenerDetalleCompleto(id);
        
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        
        return ResponseEntity.notFound().build();
    }
}