package com.tienda.envios.repository;
import com.tienda.envios.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    Optional<Envio> findByPedidoId(Integer pedidoId);
}