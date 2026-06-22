package com.tienda.notificaciones.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class notificaciones {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El correo de destino no puede estar vacío")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    private String emailDestino;
    
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;
    
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;
}

