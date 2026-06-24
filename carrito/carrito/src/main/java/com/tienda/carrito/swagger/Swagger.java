package com.tienda.carrito.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger {
    @Bean
    public OpenAPI CarritoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        

                        .title("API de Carrito")


                        .description("API REST para la gestión de Compras en carrito de compras")


                        .version("1.0")


                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")
                        )
                );
}
}