package com.tienda.pedidos.service;

import com.tienda.pedidos.dto.PedidoDetalleDTO;
import com.tienda.pedidos.model.Pedido;
import com.tienda.pedidos.repository.PedidoRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public Pedido guardarPedido(Pedido pedido) {
        return repository.save(pedido);
    }

    public PedidoDetalleDTO obtenerDetalleCompleto(Integer pedidoId) {
        Optional<Pedido> pedidoOpt = repository.findById(pedidoId);
        
        if (pedidoOpt.isEmpty()) {
            return null;
        }

        Pedido pedido = pedidoOpt.get();
        RestTemplate restTemplate = new RestTemplate();
        
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setPedidoId(pedido.getId());
        dto.setFecha(pedido.getFecha());
        dto.setCantidad(pedido.getCantidad());

        try {
            String urlUsuario = "http://localhost:8082/usuarios/" + pedido.getUsuarioId();
            UsuarioResponse user = restTemplate.getForObject(urlUsuario, UsuarioResponse.class);
            if (user != null) {
                dto.setNombreCliente(user.getNombre());
                dto.setEmailCliente(user.getEmail());
            }

            String urlPrenda = "http://localhost:8081/prendas/" + pedido.getPrendaId();
            PrendaResponse prenda = restTemplate.getForObject(urlPrenda, PrendaResponse.class);
            if (prenda != null) {
                dto.setPrendaComprada(prenda.getNombre());
                dto.setPrecioUnitario(prenda.getPrecio());
                dto.setTotal(prenda.getPrecio() * pedido.getCantidad());
            }
        } catch (Exception e) {
            System.out.println("Error al conectar con otros microservicios: " + e.getMessage());
        }

        return dto;
    }

    
    
    
    @Data
    static class UsuarioResponse {
        private String nombre;
        private String email;
    }

    @Data
    static class PrendaResponse {
        private String nombre;
        private Double precio;
    }
}