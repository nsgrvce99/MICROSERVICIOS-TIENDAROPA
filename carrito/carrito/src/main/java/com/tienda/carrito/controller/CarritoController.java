package com.tienda.carrito.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.carrito.dto.CarritoDTO;
import com.tienda.carrito.model.Carrito;
import com.tienda.carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    private final CarritoService service;
    public CarritoController(CarritoService service) { this.service = service; }
    @Operation(summary = "Agregar un item al carrito")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item agregado al carrito"),
        @ApiResponse(responseCode = "404", description = "Item no agregado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") 
    })
    @PostMapping("/agregar")
    public ResponseEntity<CarritoDTO> agregar(@Valid @RequestBody Carrito carrito) {
        Carrito guardado = service.agregarItem(carrito);
        return ResponseEntity.status(201).body(convertir(guardado));
    }

    @Operation(summary = "Ver el carrito de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") 
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CarritoDTO>> verCarrito(@PathVariable Integer usuarioId) {
        List<CarritoDTO> lista = service.verCarrito(usuarioId).stream()
                                        .map(this::convertir).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
    @Operation(summary = "Ver el carrito de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito vaciado correctamente"),
        @ApiResponse(responseCode = "404", description = "Carrito no vaciado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") 
    })
    @DeleteMapping("/vaciar/{usuarioId}")
    public ResponseEntity<String> vaciar(@PathVariable Integer usuarioId) {
        service.vaciarCarrito(usuarioId);
        return ResponseEntity.ok("Carrito vaciado correctamente");
    }

    private CarritoDTO convertir(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setPrendaId(carrito.getPrendaId());
        dto.setCantidad(carrito.getCantidad());
        return dto;
    }
}