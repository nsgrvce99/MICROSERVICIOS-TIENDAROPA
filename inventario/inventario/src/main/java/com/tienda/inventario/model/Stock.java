package com.tienda.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "stock")
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID de la prenda es obligatorio")
    @Column(unique = true)
    private Integer prendaId;

    @NotNull(message = "La cantidad en stock es obligatoria")
    private Integer cantidadDisponible;
}