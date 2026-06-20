package com.tienda.pagos.controller;
import con.tienda.pagos.controller.PagoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.list;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
public class controllerpagos {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Test
    public void testListarPagos() throws Exception {
        List<Pago> pagos = new ArrayList<>();
        pagos.add(new Pago(1L, 100.0, "COMPLETADO", LocalDateTime.now()));
        when(pagoService.listarPagos()).thenReturn(pagos);

        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk());
    }

}
