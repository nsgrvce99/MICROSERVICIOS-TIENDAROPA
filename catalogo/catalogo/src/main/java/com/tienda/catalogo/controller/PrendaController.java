package com.tienda.catalogo.controller;

import com.tienda.catalogo.dto.PrendaSimpleDTO;
import com.tienda.catalogo.model.Prenda;
import com.tienda.catalogo.service.PrendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prendas")
public class PrendaController {
    private final PrendaService service;

    public PrendaController(PrendaService service) {
        this.service = service;
    }

    @PostMapping("/agregar")
    public ResponseEntity<Prenda> crearPrenda(@Valid @RequestBody Prenda prenda) {
        return ResponseEntity.status(201).body(service.guardarPrenda(prenda));
        
    }

    @GetMapping("/listar-dto")
    public ResponseEntity<List<PrendaSimpleDTO>> listarDTO() {
        return ResponseEntity.ok(service.listarCatalogoSimple());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Prenda> buscarPorId(@PathVariable Integer id) {
        Prenda p = service.buscarPorId(id);
        if(p != null) return ResponseEntity.ok(p);
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try {
            String mensaje = service.eliminar(id);
            if (mensaje.contains("Prenda no encontrada")) {
                    return ResponseEntity.status(404).body(mensaje);
                }
                    return ResponseEntity.ok(mensaje);
            } catch (Exception e) {

                    return ResponseEntity.status(500).body("error");
            }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Prenda prenda, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            String mensaje = service.actualizar(prenda);
            if (mensaje.contains("Prenda no encontrada")) {
                return ResponseEntity.status(404).body(mensaje);
            }
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error");
        }
    }
}

    

