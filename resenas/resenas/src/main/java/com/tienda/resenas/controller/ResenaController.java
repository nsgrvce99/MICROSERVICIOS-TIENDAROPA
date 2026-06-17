package com.tienda.resenas.controller;
import com.tienda.resenas.dto.ResenaDTO;
import com.tienda.resenas.model.Resena;
import com.tienda.resenas.service.ResenaService;
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

    @PostMapping("/agregar")
    public ResponseEntity<ResenaDTO> agregar(@Valid @RequestBody Resena resena) {
        Resena nueva = service.agregarResena(resena);
        return ResponseEntity.status(201).body(convertir(nueva));
    }

    @GetMapping("/prenda/{prendaId}")
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