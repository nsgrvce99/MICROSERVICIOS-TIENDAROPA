package com.tienda.envios.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer pedidoId;

    @NotBlank(message = "Debe indicar una dirección de entrega")
    @Column(nullable = false, length = 100)
    private String direccion;

    private String courier = "STARKEN";
    private String estado = "PREPARANDO";
}