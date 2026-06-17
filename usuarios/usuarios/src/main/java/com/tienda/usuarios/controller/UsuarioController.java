package com.tienda.usuarios.controller;

import com.tienda.usuarios.dto.UsuarioSimpleDTO;
import com.tienda.usuarios.model.Usuario;
import com.tienda.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
        summary = "Agregar nuevo usuario",
        description = "Crea un nuevo usuario en la base de datos y devuelve el usuario creado."   
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida, datos de usuario no válidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el usuario")
    })
    @PostMapping("/agregar")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevo = service.guardarUsuario(usuario);
        return ResponseEntity.status(201).body(nuevo);
    }
    @Operation(
        summary = "Buscar usuario por ID",
        description = "Busca un usuario en la base de datos utilizando su ID y devuelve los detalles del usuario encontrado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSimpleDTO> buscarPorId(@PathVariable Integer id) {
        UsuarioSimpleDTO dto = service.buscarPorIdSimple(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(
        summary = "Eliminar usuario por ID",
        description = "Elimina un usuario de la base de datos utilizando su ID y devuelve un mensaje indicando el resultado de la operación."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
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

    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente en la base de datos y devuelve un mensaje indicando el resultado de la operación."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida, datos de usuario no válidos")
    })
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
