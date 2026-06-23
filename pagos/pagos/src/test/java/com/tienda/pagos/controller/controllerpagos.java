package com.tienda.pagos.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.pagos.model.Pago;
import com.tienda.pagos.service.PagoService;

@WebMvcTest(PagoController.class)
public class PagoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PagoService pagoService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testProcesarPago_Exitoso() throws Exception {
        Pago pagoRequest = new Pago();
        pagoRequest.setPedidoId(100L);
        pagoRequest.setMonto(150.50);

        Pago pagoSimuladoResponse = new Pago();
        pagoSimuladoResponse.setPedidoId(100L);
        pagoSimuladoResponse.setMonto(150.50);
        pagoSimuladoResponse.setEstado("APROBADO");
        pagoSimuladoResponse.setFechaPago(LocalDateTime.now());

        when(pagoService.procesarPago(any(Pago.class))).thenReturn(pagoSimuladoResponse);

        mockMvc.perform(post("/pagos/procesar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pedidoId").value(100))
                .andExpect(jsonPath("$.monto").value(150.50))
                .andExpect(jsonPath("$.estado").value("APROBADO"))
                .andExpect(jsonPath("$.fechaPago").exists());
    }

    @Test
    public void testProcesarPago_Rechazado() throws Exception {
        Pago pagoRequest = new Pago();
        pagoRequest.setPedidoId(101L);
        pagoRequest.setMonto(9999.99);

        Pago pagoSimuladoResponse = new Pago();
        pagoSimuladoResponse.setPedidoId(101L);
        pagoSimuladoResponse.setMonto(9999.99);
        pagoSimuladoResponse.setEstado("RECHAZADO");
        pagoSimuladoResponse.setFechaPago(LocalDateTime.now());

        when(pagoService.procesarPago(any(Pago.class))).thenReturn(pagoSimuladoResponse);
        mockMvc.perform(post("/pagos/procesar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("RECHAZADO"));
    }
}
