package com.tienda.pedidos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.pedidos.dto.CheckoutRequestDTO;
import com.tienda.pedidos.service.OrquestadorService;

@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrquestadorService orquestadorService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testProcesarCheckout_Exitoso() throws Exception {

        CheckoutRequestDTO requestDTO = new CheckoutRequestDTO();
        String mensajeExito = "Compra procesada con éxito. Pedido N° 12345.";
        when(orquestadorService.procesarCompraFinal(any(CheckoutRequestDTO.class))).thenReturn(mensajeExito);

        mockMvc.perform(post("/checkout/procesar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(mensajeExito));
    }
    @Test
    public void testProcesarCheckout_FallidoPorStock() throws Exception {
        CheckoutRequestDTO requestDTO = new CheckoutRequestDTO();
        String mensajeError = "Error al procesar la compra: Stock insuficiente para el producto.";
        when(orquestadorService.procesarCompraFinal(any(CheckoutRequestDTO.class))).thenReturn(mensajeError);
        mockMvc.perform(post("/checkout/procesar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk()) 
                .andExpect(content().string(mensajeError));
    }
}
