package com.tienda.pedidos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PedidoDetalleDTO {
    private Integer pedidoId;
    private LocalDateTime fecha;
    
    private String nombreCliente;
    private String emailCliente;
    
    private String prendaComprada;
    private Double precioUnitario;
    
    private Integer cantidad;
    private Double total;
}