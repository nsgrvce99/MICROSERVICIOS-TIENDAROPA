package com.tienda.promociones.dto;
import lombok.Data;

@Data
public class CuponDTO {
    private String codigo;
    private Double porcentajeDescuento;
}