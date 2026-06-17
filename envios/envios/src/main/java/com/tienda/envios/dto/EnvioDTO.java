package com.tienda.envios.dto;
import lombok.Data;
@Data
public class EnvioDTO {
    private Integer pedidoId;
    private String direccion;
    private String courier;
    private String estado;
}