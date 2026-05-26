package com.tienda.inventario.dto;
import lombok.Data;

@Data
public class StockDTO {
    private Integer prendaId;
    private Integer cantidadDisponible;
}