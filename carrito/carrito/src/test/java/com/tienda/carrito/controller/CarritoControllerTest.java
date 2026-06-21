package com.tienda.carrito.controller;


import com.tienda.carrito.model.Carrito;
import com.tienda.carrito.service.CarritoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;



import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@WebMvcTest(CarritoController.class) // Levanta solamente el Controller.
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc; //Sirve para simular peticiones HTTP

    @MockitoBean //Crea un servicio falso.No se conecta a BD.No ejecuta lógica real.
    private CarritoService service;


    @Test
    void listarCarritoPorID() throws Exception {

    List<Carrito> carritos = List.of(
            new Carrito(
                    1,
                    10,
                    30,
                    2000)
    );

    //when(service.verCarrito()).thenReturn(carritos);
    when(service.verCarrito(anyInt())).thenReturn(carritos);

    mockMvc.perform(get("/carrito/usuario/10"))//mockMvc.perform(get("/carrito"))
            .andExpect(status().isOk());//verifica que el endpoint respondió:
}
    @Test
    void crearCarrito() throws Exception{
        String carritoJson = """
                {
                    "usuarioId": 10,
                    "prendaId": 30,
                    "cantidad": 2000
                }
                """;
    Carrito carritoCreado = new Carrito(1, 10, 30, 2000);
    when(service.agregarItem(any(Carrito.class))).thenReturn(carritoCreado);
    mockMvc.perform(post("/carrito/agregar")
                    .contentType(APPLICATION_JSON)
                    .content(carritoJson)
    ).andExpect(status().isCreated());
    }

    @Test
    void vaciarCarrito() throws Exception {
    Carrito carritoCreado = new Carrito(1, 10, 30, 2000);
    when(service.agregarItem(any(Carrito.class))).thenReturn(carritoCreado);
    mockMvc.perform(delete("/carrito/vaciar/10")
                    .contentType(APPLICATION_JSON)
    ).andExpect(status().isOk());
    }

}