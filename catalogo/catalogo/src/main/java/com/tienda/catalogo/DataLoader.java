package com.tienda.catalogo;

import com.tienda.catalogo.model.Prenda;
import com.tienda.catalogo.repository.PrendaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(PrendaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                String[] categorias = {"Boxy Fit Polera", "Cargo Pantalones", "Baggy Jeans", "Hoodie Oversize", "Chaqueta"};
                String[] colores = {"Negro", "Gris", "Blanco", "Verde Olivo", "Azul Marino"};
                String[] tallas = {"S", "M", "L"};

                for (String categoria : categorias) {
                    for (String color : colores) {
                        for (String talla : tallas) {
                            String nombre = categoria + " " + color;
                            Double precio = categoria.contains("Chaqueta") ? 45000.0 : 25000.0;
                            repository.save(new Prenda(null, nombre, categoria, talla, precio));
                        }
                    }
                }
            }
        };
    }
}