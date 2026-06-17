package com.tienda.usuarios.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI usuariosOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API de Usuarios")
                        .description("API REST para la gestión de usuarios")
                        .version("3.0")
                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")
    )
);
    }
}
