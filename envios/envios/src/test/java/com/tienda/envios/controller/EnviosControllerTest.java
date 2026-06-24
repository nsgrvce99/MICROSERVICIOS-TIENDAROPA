package com.tienda.envios.controller;

import com.tienda.envios.model.Envio;
import com.tienda.envios.service.EnvioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnvioController.class)
class EnviosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService service;

    @Test
    void generarEnvio() throws Exception {
        String envioJson = """
                {   "id": 1,
                    "pedidoId": 105,
                    "direccion": "Las flores 742",
                    "courier": "STARKEN",
                    "estado": "PREPARANDO"
                }
                """;
        
      
        Envio envioCreado = new Envio(1, 105, "Las flores 742", "STARKEN", "PREPARANDO");

        when(service.generarEnvio(any(Envio.class))).thenReturn(envioCreado);

       
        mockMvc.perform(post("/envios/generar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(envioJson)
        )
        .andExpect(status().isCreated());
    }

    @Test
    void rastrearEnvio() throws Exception {
        Integer pedidoId = 100;
        Envio envioExistente = new Envio(1, 100, "Las flores 742", "STARKEN", "EN_CAMINO");

        when(service.rastrear(pedidoId)).thenReturn(Optional.of(envioExistente));

        mockMvc.perform(get("/envios/rastreo/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(envioExistente.toString())
        )
        .andExpect(status().isOk());
    }

    @Test
    void rastrearEnvioNotFound() throws Exception {
        Integer pedidoId = 200;
        String envioJson = """
                {   "id": 1,
                    "pedidoId": 105,
                    "direccion": "Las flores 742",
                    "courier": "STARKEN",
                    "estado": "PREPARANDO"
                }
                """;
        when(service.rastrear(pedidoId)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/envios/rastreo/200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(envioJson)
        )
        .andExpect(status().isNotFound());
    }
}
