package com.tienda.promociones.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El código no puede estar vacío")
    @Column(unique = true)
    private String codigo;

    @NotNull(message = "El descuento es obligatorio")
    private Double porcentajeDescuento;
}