package com.tienda.resenas;

import com.tienda.resenas.controller.ResenaController;
import com.tienda.resenas.dto.ResenaDTO;
import com.tienda.resenas.model.Resena;
import com.tienda.resenas.service.ResenaService;

import java.util.List;

import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ResenaController.class)
public class resenasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService service;

    @Test
    void agregar() throws Exception {
        String resenaJson = """
                {
                    "prendaId": 1,
                    "usuarioId": 1,
                    "estrellas": 5,
                    "comentario": "Excelente"
                }
                """;

        Resena resenaCreada = new Resena(1, 1, 1, 5, "Excelente");
        when(service.agregarResena(any(Resena.class))).thenReturn(resenaCreada);

        mockMvc.perform(post("/resenas/agregar")
                .contentType(APPLICATION_JSON)
                .content(resenaJson))
                .andExpect(status().isCreated());
    }


    @Test
    void verResenas() throws Exception {
        List<Resena> lista = List.of(
                new Resena(1, 1, 1, 5, "Excelente")
        );

        when(service.verResenasPorPrenda(any(Integer.class))).thenReturn(lista);

        mockMvc.perform(get("/resenas/resena/1")
    )               .andExpect(status().isOk());

    }

}
