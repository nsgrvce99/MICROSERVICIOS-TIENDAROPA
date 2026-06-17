package com.tienda.usuarios.controller;

import com.tienda.usuarios.dto.UsuarioSimpleDTO;
import com.tienda.usuarios.model.Usuario;
import com.tienda.usuarios.service.UsuarioService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/agregar")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevo = service.guardarUsuario(usuario);
        return ResponseEntity.status(201).body(nuevo); 
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSimpleDTO> buscarPorId(@PathVariable Integer id) {
        UsuarioSimpleDTO dto = service.buscarPorIdSimple(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try {
            String mensaje = service.eliminar(id);
            if (mensaje.contains("Usuario no encontrado")) {
                    return ResponseEntity.status(404).body(mensaje);
                }
                    return ResponseEntity.ok(mensaje);
            } catch (Exception e) {

                    return ResponseEntity.status(500).body("error");
            }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            String mensaje = service.actualizar(usuario);
            if (mensaje.contains("Usuario no encontrado")) {
                return ResponseEntity.status(404).body(mensaje);
            }
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error");
        }
    }
}
