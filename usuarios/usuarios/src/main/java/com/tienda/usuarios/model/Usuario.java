package com.tienda.usuarios.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    public Usuario() {
    }
    public Usuario(Object object, Object object2, Object object3) {
        //TODO Auto-generated constructor stub
    }

    public Usuario(Object object, Object object2, Object object3, Object object4) {
        //TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Debe ser un formato de correo electrónico válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}