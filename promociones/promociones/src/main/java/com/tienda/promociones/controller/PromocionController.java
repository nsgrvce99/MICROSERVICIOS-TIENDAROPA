package com.tienda.promociones.controller;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.promociones.dto.CuponDTO;
import com.tienda.promociones.model.Cupon;
import com.tienda.promociones.service.CuponService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/promociones")
public class PromocionController {
    private final CuponService service;
    public PromocionController(CuponService service) { this.service = service; }

    @Operation(
        summary = "Crear un nuevo cupón",
        description = "Crea un nuevo cupón con los detalles proporcionados"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cupón creado exitosamente")  ,
        @ApiResponse(responseCode = "400", description = "Datos invalidos")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
    @PostMapping("/crear")
    public ResponseEntity<CuponDTO> crear(@Valid @RequestBody Cupon cupon) {
        Cupon nuevo = service.crearCupon(cupon);
        return ResponseEntity.status(201).body(convertir(nuevo));
    }

    @Operation(
        summary = "Validar un cupón",
        description = "Valida la existencia y vigencia de un cupón"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupón válido")  ,
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado")  ,
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")        
        })
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