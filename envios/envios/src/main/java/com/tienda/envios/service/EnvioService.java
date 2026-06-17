package com.tienda.envios.service;
import com.tienda.envios.model.Envio;
import com.tienda.envios.repository.EnvioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EnvioService {
    private final EnvioRepository repository;
    public EnvioService(EnvioRepository repository) { this.repository = repository; }
    
    public Envio generarEnvio(Envio envio) {
        return repository.save(envio); }
    public Optional<Envio> rastrear(Integer pedidoId) { return repository.findByPedidoId(pedidoId); }
}