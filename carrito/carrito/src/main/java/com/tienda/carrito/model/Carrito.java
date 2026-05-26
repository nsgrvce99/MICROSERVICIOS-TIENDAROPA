package com.tienda.carrito.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El ID de la prenda es obligatorio")
    private Integer prendaId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Debe agregar al menos 1 prenda")
    private Integer cantidad;
}