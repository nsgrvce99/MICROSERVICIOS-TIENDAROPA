package com.tienda.catalogo.swagger;

import io.swagger.v3.oas.model.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Catalogo API")
                        .version("1.0")
                        .description("API para gestionar el catálogo de productos"));
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@tienda.com")
                        )
                );              
    }

}