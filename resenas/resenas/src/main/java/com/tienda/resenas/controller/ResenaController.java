package com.tienda.resenas.controller;
import com.tienda.resenas.dto.ResenaDTO;
import com.tienda.resenas.model.Resena;
import com.tienda.resenas.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/resenas")
public class ResenaController {
    private final ResenaService service;
    public ResenaController(ResenaService service) { this.service = service; }
    @Operation(
        summary = "Agregar una nueva reseña",
        description = "Permite agregar una nueva reseña para una prenda específica. Se requiere la cantidad de estrellas y un comentario opcional."
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida, datos incorrectos o faltantes")
    })
    @PostMapping("/agregar")
    public ResponseEntity<ResenaDTO> agregar(@Valid @RequestBody Resena resena) {
        Resena nueva = service.agregarResena(resena);
        return ResponseEntity.status(201).body(convertir(nueva));
    }
    @Operation(
        summary = "Ver reseñas por prenda",
        description = "Permite obtener todas las reseñas asociadas a una prenda específica utilizando su ID."
    )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Reseñas obtenidas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Prenda no encontrada")
    })
    @GetMapping("/resena/{prendaId}")
    public ResponseEntity<List<ResenaDTO>> verResenas(@PathVariable Integer prendaId) {
        List<ResenaDTO> lista = service.verResenasPorPrenda(prendaId).stream()
        .map(this::convertir).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    private ResenaDTO convertir(Resena resena) {
        ResenaDTO dto = new ResenaDTO();
        dto.setUsuarioId(resena.getUsuarioId());
        dto.setEstrellas(resena.getEstrellas());
        dto.setComentario(resena.getComentario());
        return dto;
    }
}