package com.tienda.pedidos.service;

import com.tienda.pedidos.dto.CheckoutRequestDTO;
import com.tienda.pedidos.model.Pedido;
import com.tienda.pedidos.repository.PedidoRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@Service
public class OrquestadorService {

    @Data static class ItemCarrito { private Integer prendaId; private Integer cantidad; }
    @Data static class Prenda { private Double precio; }
    @Data static class Cupon { private Double porcentajeDescuento; }
    @Data static class Usuario { private String nombre; private String email; }
    
    @Data static class PagoRequest { 
        private Integer pedidoId; private Double monto; private String metodoPago;
        public PagoRequest(Integer id, Double m, String mp) { this.pedidoId = id; this.monto = m; this.metodoPago = mp; }
    }
    @Data static class EnvioRequest {
        private Integer pedidoId; private String direccion;
        public EnvioRequest(Integer id, String d) { this.pedidoId = id; this.direccion = d; }
    }
    @Data static class NotificacionRequest { private String emailDestino; private String asunto; private String mensaje; }

    private final PedidoRepository pedidoRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public OrquestadorService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public String procesarCompraFinal(CheckoutRequestDTO request) {
        System.out.println("===CHECKOUT ===");

        try {
            String urlCarrito = "http://localhost:8087/carrito/usuario/" + request.getUsuarioId();
            ItemCarrito[] items = restTemplate.getForObject(urlCarrito, ItemCarrito[].class);
            if (items == null || items.length == 0) return "Error: El carrito está vacío.";
            System.out.println("1. Carrito obtenido. Artículos: " + items.length);

            double totalAPagar = 0.0;
            for (ItemCarrito item : items) {
                Prenda p = restTemplate.getForObject("http://localhost:8081/prendas/" + item.getPrendaId(), Prenda.class);
                if (p != null) totalAPagar += (p.getPrecio() * item.getCantidad());
            }
            System.out.println("2. Total calculado: $" + totalAPagar);

            if (request.getCodigoCupon() != null && !request.getCodigoCupon().isEmpty()) {
                try {
                    Cupon c = restTemplate.getForObject("http://localhost:8088/promociones/validar/" + request.getCodigoCupon(), Cupon.class);
                    if (c != null) {
                        double descuento = totalAPagar * (c.getPorcentajeDescuento() / 100);
                        totalAPagar -= descuento;
                        System.out.println("3. Cupón aplicado! Nuevo total: $" + totalAPagar);
                    }
                } catch (Exception e) {
                    System.out.println("3. Cupón inválido, se cobrará precio normal.");
                }
            }

            PagoRequest pago = new PagoRequest(1, totalAPagar, request.getMetodoPago());
            restTemplate.postForObject("http://localhost:8085/pagos/procesar", pago, String.class);
            System.out.println("4. Pago aprobado en el banco.");

            Integer ultimoPedidoId = 0;
            for (ItemCarrito item : items) {
                restTemplate.put("http://localhost:8084/inventario/descontar/" + item.getPrendaId() + "/" + item.getCantidad(), null);
                
                Pedido nuevoPedido = new Pedido();
                nuevoPedido.setUsuarioId(request.getUsuarioId());
                nuevoPedido.setPrendaId(item.getPrendaId());
                nuevoPedido.setCantidad(item.getCantidad());
                nuevoPedido.setFecha(LocalDateTime.now());
                ultimoPedidoId = pedidoRepository.save(nuevoPedido).getId();
            }
            System.out.println("5. Inventario descontado y pedidos guardados.");

            EnvioRequest envio = new EnvioRequest(ultimoPedidoId, request.getDireccion());
            restTemplate.postForObject("http://localhost:8089/envios/generar", envio, String.class);
            System.out.println("6. Etiqueta de envío generada a " + request.getDireccion());

            restTemplate.delete("http://localhost:8087/carrito/vaciar/" + request.getUsuarioId());
            System.out.println("7. Carrito vaciado.");

            Usuario u = restTemplate.getForObject("http://localhost:8082/usuarios/" + request.getUsuarioId(), Usuario.class);
            if (u != null) {
                NotificacionRequest noti = new NotificacionRequest();
                noti.setEmailDestino(u.getEmail());
                noti.setAsunto("¡Tu compra fue un éxito!");
                noti.setMensaje("Hola " + u.getNombre() + ", tu pago de $" + totalAPagar + " fue aprobado. Tu ropa va en camino.");
                restTemplate.postForObject("http://localhost:8086/notificaciones/enviar", noti, String.class);
                System.out.println("8. Correo enviado a " + u.getEmail());
            }

            return "¡Checkout completado con éxito! Se cobraron $" + totalAPagar;

        } catch (Exception e) {
            return "Error en la orquestación: " + e.getMessage();
        }
    }
}