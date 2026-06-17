package com.tienda.pagos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PagoRespuestaDTO {
    private Integer pedidoId;
    private Double monto;
    private String estado;
    private LocalDateTime fechaPago;
}