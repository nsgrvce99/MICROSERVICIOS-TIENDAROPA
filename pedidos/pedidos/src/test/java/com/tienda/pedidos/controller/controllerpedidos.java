package com.tienda.pedidos.controller;
import con.tienda.pedidos.controller.PedidoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.list;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
public class controllerpedidos {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Test
    public void testListarPedidos() throws Exception {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(1L, 1L, 100.0, "COMPLETADO", LocalDateTime.now()));
        when(pedidoService.listarPedidos()).thenReturn(pedidos);

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk());
    }

}
