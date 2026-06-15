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

    @NotNull(message = "El ID del pedido es obligatorio")
    @Column(unique = true)
    private Integer pedidoId;

    @NotNull(message = "El monto es obligatorio")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago; 

    private String estado = "APROBADO"; 
    private LocalDateTime fechaPago = LocalDateTime.now();
}