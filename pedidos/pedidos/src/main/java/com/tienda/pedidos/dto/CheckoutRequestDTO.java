package com.tienda.pedidos.dto;
import lombok.Data;

@Data
public class CheckoutRequestDTO {
    private Integer usuarioId;
    private String metodoPago;
    private String direccion;
    private String codigoCupon;
}