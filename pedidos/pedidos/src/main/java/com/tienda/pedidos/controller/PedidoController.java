package com.tienda.pedidos.controller;

import com.tienda.pedidos.dto.PedidoDetalleDTO;
import com.tienda.pedidos.model.Pedido;
import com.tienda.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/agregar")
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody Pedido pedido) {
        Pedido nuevo = service.guardarPedido(pedido);
        return ResponseEntity.status(201).body(nuevo);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<PedidoDetalleDTO> obtenerDetallePedido(@PathVariable Integer id) {
        PedidoDetalleDTO dto = service.obtenerDetalleCompleto(id);
        
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        
        return ResponseEntity.notFound().build();
    }
}