package com.tienda.inventario.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class swagger{
    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        

                        .title("API de Inventario")


                        .description("API REST para la gestión de inventario")


                        .version("1.0")


                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")
                        )
                );
    }
}
