package com.tienda.inventario.controller;
import con.tienda.inventario.controller.InventarioController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.list;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventarioController.class)
public class controllerInventario {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Test
    public void testListarInventario() throws Exception {
        List<Inventario> inventarios = new ArrayList<>();
        inventarios.add(new Inventario(1L, 1L, 10));
        when(inventarioService.listarInventario()).thenReturn(inventarios);

        mockMvc.perform(get("/inventario"))
                .andExpect(status().isOk());
    }
    

}
