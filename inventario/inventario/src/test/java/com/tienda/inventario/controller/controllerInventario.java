package com.tienda.inventario.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.inventario.dto.StockDTO;
import com.tienda.inventario.model.Stock;
import com.tienda.inventario.service.StockService;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StockService stockService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testAgregarStock_Exitoso() throws Exception {
        Stock stockRetornadoPorService = new Stock();
        stockRetornadoPorService.setPrendaId(1);
        stockRetornadoPorService.setCantidadDisponible(50);

        StockDTO requestBody = new StockDTO();
        requestBody.setPrendaId(1);
        requestBody.setCantidadDisponible(50);

        when(stockService.inicializarStock(any(Stock.class))).thenReturn(stockRetornadoPorService);
        mockMvc.perform(post("/inventario/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.prendaId").value(1))
                .andExpect(jsonPath("$.cantidadDisponible").value(50));
    }

    @Test
    public void testVerificarStock_Disponible() throws Exception {
        int prendaId = 1;
        int cantidad = 5;
        when(stockService.verificarDisponibilidad(prendaId, cantidad)).thenReturn(true);

        mockMvc.perform(get("/inventario/verificar/{prendaId}/{cantidad}", prendaId, cantidad))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testDescontarStock_Exitoso() throws Exception {
        int prendaId = 1;
        int cantidad ADoscontar = 10;
        
        Stock stockActualizado = new Stock();
        stockActualizado.setPrendaId(prendaId);
        stockActualizado.setCantidadDisponible(40);

        when(stockService.descontarStock(eq(prendaId), eq(cantidadADoscontar))).thenReturn(stockActualizado);

        mockMvc.perform(put("/inventario/descontar/{prendaId}/{cantidad}", prendaId, cantidadADoscontar))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prendaId").value(prendaId))
                .andExpect(jsonPath("$.cantidadDisponible").value(40));
    }

    @Test
    public void testDescontarStock_Insuficiente() throws Exception {
        int prendaId = 1;
        int cantidadGrandisima = 999;

        when(stockService.descontarStock(eq(prendaId), eq(cantidadGrandisima))).thenReturn(null);

        mockMvc.perform(put("/inventario/descontar/{prendaId}/{cantidad}", prendaId, cantidadGrandisima))
                .andExpect(status().isBadRequest());
    }

}
