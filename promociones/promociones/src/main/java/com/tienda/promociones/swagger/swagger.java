package com.tienda.promociones.swagger;

import org.springframework.context.annotation.Bean;// Clase principal de OpenAPI que representa la documentación completa de la API
import org.springframework.context.annotation.Configuration;// Permite definir información de contacto (nombre, correo, sitio web, etc.)

import io.swagger.v3.oas.models.OpenAPI;// Permite definir información general de la API (título, descripción, versión)
import io.swagger.v3.oas.models.info.Contact;// Permite registrar métodos como Beans administrados por Spring
import io.swagger.v3.oas.models.info.Info;// Indica que esta clase contiene configuraciones de Spring

@Configuration // Indica que esta clase contiene configuración de Spring
public class swagger{
    @Bean // Registra el objeto OpenAPI como un Bean de Spring
    public OpenAPI promocionesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        
                        // Nombre de la API que aparecerá en Swagger
                        .title("API de Promociones")

                        // Descripción general del proyecto
                        .description("API REST para la gestión de promociones")

                        // Versión de la API
                        .version("1.0")

                        // Información de contacto
                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")
                        )
                );
    }
}