package cl.duocuc.demo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
         System.out.println("================================================");
        System.out.println(" API Gateway iniciado correctamente");
        System.out.println(" URL: http://localhost:8091");
        System.out.println("------------------------------------------------");
        System.out.println(" /api/carrito/** -> CARRITO");
        System.out.println(" /api/catalogo/**  -> CATALOGO");
        System.out.println(" /api/envios/** -> ENVIOS");
        System.out.println(" /api/inventario/** -> INVENTARIO");
        System.out.println(" /api/notificaciones/** -> NOTIFICACIONES");
        System.out.println(" /api/pagos/** -> PAGOS");
        System.out.println(" /api/pedidos/** -> PEDIDOS");
        System.out.println(" /api/checkout/** -> CHECKOUT");
        System.out.println(" /api/promociones/** -> PROMOCIONES");
        System.out.println(" /api/resenas/** -> RESENAS");
        System.out.println(" /api/usuarios/** -> USUARIOS");
        System.out.println("------------------------------------------------");
        System.out.println(" Eureka: http://localhost:8761");
        System.out.println("================================================");
    }

}
