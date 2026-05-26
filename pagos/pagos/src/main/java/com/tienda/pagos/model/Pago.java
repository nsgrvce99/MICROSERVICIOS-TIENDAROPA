package com.tienda.pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer pedidoId;

    @NotNull
    private Double monto;

    @NotBlank
    private String metodoPago; 

    private String estado = "APROBADO"; 
    private LocalDateTime fechaPago = LocalDateTime.now();
}