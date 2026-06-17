package com.tienda.notificaciones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificacionDTO {
    
    @NotBlank(message = "El correo de destino no puede estar vacío")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    private String emailDestino;
    
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;
    
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;
}