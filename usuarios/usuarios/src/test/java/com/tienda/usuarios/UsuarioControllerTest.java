package com.tienda.usuarios;

import com.tienda.usuarios.controller.UsuarioController;
import com.tienda.usuarios.dto.UsuarioSimpleDTO;
import com.tienda.usuarios.model.Usuario;
import com.tienda.usuarios.service.UsuarioService;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService service;
    
    @Test
    void crearUsuario() throws Exception {
        String usuarioJson = """
            {
                "nombre": "Juan",
                "email":  "Juanperez@gmail.com",
                "password": "123456"
            }
            """;
        Usuario UsuarioCreado = new Usuario("Juan", "Juanperez@gmail.com", "123456");
        when(service.guardarUsuario(any(Usuario.class))).thenReturn(UsuarioCreado);
        mockMvc.perform(post("/usuarios/agregar")
                .contentType(APPLICATION_JSON)
                .content(usuarioJson)
                .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    void buscarUsuarioPorId() throws Exception{
        Integer usuarioId = 1;
        UsuarioSimpleDTO dtoMock = new UsuarioSimpleDTO();
        when(service.buscarPorIdSimple(any(Integer.class))).thenReturn(dtoMock);
        mockMvc.perform(get("/usuarios/{id}", usuarioId))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarUsuarioPorId() throws Exception {
        Integer usuarioId = 1;
        when(service.eliminar(any(Integer.class))).thenReturn("Usuario eliminado exitosamente");
        mockMvc.perform(delete("/usuarios/eliminar/{id}", usuarioId)
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarUsuario() throws Exception {
        String usuarioJson = """
            {
                "id": 1,
                "nombre": "Juan2",
                "email": "juan2@gmail.com",
                "password": "nuevapw"
            }
            """;
        when(service.actualizar(any(Usuario.class))).thenReturn("Usuario actualizado exitosamente");
        mockMvc.perform(put("/usuarios/actualizar")
                .contentType(APPLICATION_JSON)
                .content(usuarioJson)
                .with(csrf()))
                .andExpect(status().isOk());
    }
    
}


