package com.tienda.carrito.controller;
import com.tienda.carrito.dto.CarritoDTO;
import com.tienda.carrito.model.Carrito;
import com.tienda.carrito.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    private final CarritoService service;
    public CarritoController(CarritoService service) { this.service = service; }

    @PostMapping("/agregar")
    public ResponseEntity<CarritoDTO> agregar(@Valid @RequestBody Carrito carrito) {
        Carrito guardado = service.agregarItem(carrito);
        return ResponseEntity.status(201).body(convertir(guardado));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CarritoDTO>> verCarrito(@PathVariable Integer usuarioId) {
        List<CarritoDTO> lista = service.verCarrito(usuarioId).stream()
                                        .map(this::convertir).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

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