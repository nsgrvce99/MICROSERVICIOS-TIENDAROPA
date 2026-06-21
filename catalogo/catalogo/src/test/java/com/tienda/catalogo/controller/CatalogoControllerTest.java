package com.tienda.catalogo.controller;

import com.tienda.catalogo.model.Prenda;

import com.tienda.catalogo.service.PrendaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;




import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@WebMvcTest(PrendaController.class) // Levanta solamente el Controller.
public class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc; //Sirve para simular peticiones HTTP

    @MockitoBean //Crea un servicio falso.No se conecta a BD.No ejecuta lógica real.
    private PrendaService service;


    @Test
    void buscarPorID() throws Exception {

    Prenda prendas = new Prenda(1, "Camisa", "Hombre", "M", 2000.0);

    //when(service.verCarrito()).thenReturn(carritos);
    when(service.buscarPorId(anyInt())).thenReturn(prendas);

    mockMvc.perform(get("/prendas/1"))//mockMvc.perform(get("/carrito"))
            .andExpect(status().isOk());//verifica que el endpoint respondió:
}
    @Test
    void crearPrenda() throws Exception{
        String prendaJson = """
                {
                    "nombre": "Camisa",
                    "categoria": "Hombre",
                    "talla": "M",
                    "precio": 2000
                }
                """;
    Prenda prendaCreada = new Prenda(1, "Camisa", "Hombre", "M", 2000.0);
    when(service.guardarPrenda(any(Prenda.class))).thenReturn(prendaCreada);
    mockMvc.perform(post("/prendas/agregar")
                    .contentType(APPLICATION_JSON)
                    .content(prendaJson)
    ).andExpect(status().isCreated());
    }

                


    @Test
    void eliminarPrenda() throws Exception {
    Prenda prenda = new Prenda(1, "Camisa", "Hombre", "M", 2000.0);
    when(service.buscarPorId(anyInt())).thenReturn(prenda);
    mockMvc.perform(delete("/prendas/1")
                    .contentType(APPLICATION_JSON)
    ).andExpect(status().isMethodNotAllowed());
    }
    @Test
    void actualizarPrenda() throws Exception {
    
    String prendaJson = """
            {   
                "id": 1,
                "nombre": "Camisa Manga Larga",
                "categoria": "Hombre",
                "talla": "L",
                "precio": 2500
            }
            """;

    when(service.actualizar(any(Prenda.class))).thenReturn("Prenda actualizada");


    mockMvc.perform(put("/prendas/actualizar")
                    .contentType(APPLICATION_JSON)
                    .content(prendaJson)
    ).andExpect(status().isOk())
    .andExpect(content().string("Prenda actualizada"));
}
    @Test
    void actualizarPrendaNotFound() throws Exception {
    
    String prendaJson = """
            {   
                "id": 1,
                "nombre": "Camisa Manga Larga",
                "categoria": "Hombre",
                "talla": "L",
                "precio": 2500
            }
            """;

    when(service.actualizar(any(Prenda.class))).thenReturn("Prenda no encontrada");


    mockMvc.perform(put("/prendas/actualizar")
                    .contentType(APPLICATION_JSON)
                    .content(prendaJson)
    ).andExpect(status().isNotFound())
    .andExpect(content().string("Prenda no encontrada"));
}

}