package com.tienda.catalogo.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI catalogoOpenAPI(){
                return new OpenAPI()
                        .info(new Info()
                                .title("API de Catálogo")
                                .description("API REST para la gestión del catálogo de productos")
                                .version("3.0")
                                .contact(new Contact()
                                        .name("Coreplay")
                                        .email("coreplay@tienda.com")
                                )
                        );
        }

}