package com.tienda.resenas.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El ID de la prenda es obligatorio")
    private Integer prendaId;

    @NotBlank(message = "El ID del usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "Las estrellas son obligatorias")
    @Min(value = 1, message = "Mínimo 1 estrella")
    @Max(value = 5, message = "Máximo 5 estrellas")
    private Integer estrellas;

    @NotBlank(message = "El comentario no puede estar vacío")
    private String comentario;
}