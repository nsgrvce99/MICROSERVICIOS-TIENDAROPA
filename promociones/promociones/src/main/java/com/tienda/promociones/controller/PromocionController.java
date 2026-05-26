package com.tienda.promociones.controller;
import com.tienda.promociones.dto.CuponDTO;
import com.tienda.promociones.model.Cupon;
import com.tienda.promociones.service.CuponService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/promociones")
public class PromocionController {
    private final CuponService service;
    public PromocionController(CuponService service) { this.service = service; }

    @PostMapping("/crear")
    public ResponseEntity<CuponDTO> crear(@Valid @RequestBody Cupon cupon) {
        Cupon nuevo = service.crearCupon(cupon);
        return ResponseEntity.status(201).body(convertir(nuevo));
    }

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<CuponDTO> validar(@PathVariable String codigo) {
        Optional<Cupon> cuponOpt = service.validarCupon(codigo);
        return cuponOpt.map(cupon -> ResponseEntity.ok(convertir(cupon)))
                    .orElse(ResponseEntity.notFound().build());
    }

    private CuponDTO convertir(Cupon cupon) {
        CuponDTO dto = new CuponDTO();
        dto.setCodigo(cupon.getCodigo());
        dto.setPorcentajeDescuento(cupon.getPorcentajeDescuento());
        return dto;
    }
}