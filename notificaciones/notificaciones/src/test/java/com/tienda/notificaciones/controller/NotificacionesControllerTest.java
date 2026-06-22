package com.tienda.notificaciones.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import com.tienda.notificaciones.model.notificaciones;
import com.tienda.notificaciones.service.NotificacionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@WebMvcTest(NotificacionController.class)
public class NotificacionesControllerTest {
    @Autowired
    private MockMvc mockMvc; //Sirve para simular peticiones HTTP

    @MockitoBean //Crea un servicio falso.No se conecta a BD.No ejecuta lógica real.
    private NotificacionService service;


    @Test
    void listarNotificaciones() throws Exception {

    List<notificaciones> notificaciones = List.of(
            new notificaciones(
                    1,
                    "destinoparaprueba@gmail.com",
                    "Su compra a sido entregada con exito",
                    "Gracias por comprar en nuestra tienda, esperamos que disfrute su compra"
            )
    );

    when(service.listar()).thenReturn(notificaciones);

    mockMvc.perform(get("/notificaciones/listar"))//mockMvc.perform(get("/notificaciones"))
            .andExpect(status().isOk());//verifica que el endpoint respondió:
}
    @Test
    void agregarNotificacion() throws Exception{
        String NotificacionJson = """
                {
                    "id":10,
                    "emailDestino": "destino@gmail.com",
                    "asunto": "tuvimos un problema en su entrega",
                    "mensaje": "Lo sentimos, tuvimos un problema con su entrega. Estamos trabajando para resolverlo lo antes posible."
                }
                """;
    notificaciones notificacionesCreada = new notificaciones(10, "destino@gmail.com", "tuvimos un problema en su entrega", "Lo sentimos, tuvimos un problema con su entrega. Estamos trabajando para resolverlo lo antes posible.");
    when(service.guardarNotificacion(any(notificaciones.class))).thenReturn(notificacionesCreada);
    mockMvc.perform(post("/notificaciones/agregar")
                    .contentType(APPLICATION_JSON)
                    .content(NotificacionJson)
    ).andExpect(status().isCreated());
    }

    @Test
    void eliminarNotificacion() throws Exception {
        String NotificacionJson = """
                {
                    "id":10,
                    "emailDestino": "destino@gmail.com",
                    "asunto": "tuvimos un problema en su entrega",
                    "mensaje": "Lo sentimos, tuvimos un problema con su entrega. Estamos trabajando para resolverlo lo antes posible."
                }
                """;

    when(service.eliminarPorId(10)).thenReturn("Prenda eliminada correctamente");
    mockMvc.perform(delete("/notificaciones/eliminar/10")
                    .contentType(APPLICATION_JSON)
                    .content(NotificacionJson)
    ).andExpect(status().isOk());
    }


}
