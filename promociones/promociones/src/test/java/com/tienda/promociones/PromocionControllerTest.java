package com.tienda.promociones;

import com.tienda.promociones.controller.PromocionController;
import com.tienda.promociones.model.Cupon;
import com.tienda.promociones.service.CuponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromocionController.class)
public class PromocionControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockitoBean
    private CuponService service;


    @Test
    void crear() throws Exception {
        String cuponJson = """
                {
                    "codigo": "PROMO2024",
                    "porcentajeDescuento": 15.5
                }
                """;

        Cupon cuponCreado = new Cupon();
        when(service.crearCupon(any(Cupon.class))).thenReturn(cuponCreado);

        mockMvc.perform(post("/promociones/crear")
                .contentType(APPLICATION_JSON)
                .content(cuponJson))
                .andExpect(status().isCreated());
    }

    @Test
    void validarExitoso() throws Exception {
        String codigo = "PROMO2024";
        Cupon cuponExistente = new Cupon();
        
        when(service.validarCupon(codigo)).thenReturn(Optional.of(cuponExistente));

        mockMvc.perform(get("/promociones/validar/{codigo}", codigo))
                .andExpect(status().isOk());
    }


    @Test
    void validarNoEncontrado() throws Exception {
        String codigo = "INVALIDO";


        when(service.validarCupon(codigo)).thenReturn(Optional.empty());

        mockMvc.perform(get("/promociones/validar/{codigo}", codigo))
                .andExpect(status().isNotFound());
    }
}