package com.tienda.carrito.service;
import com.tienda.carrito.model.Carrito;
import com.tienda.carrito.repository.CarritoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarritoService {
    private final CarritoRepository repository;
    public CarritoService(CarritoRepository repository) { this.repository = repository; }
    
    public Carrito agregarItem(Carrito carrito) { return repository.save(carrito); }
    public List<Carrito> verCarrito(Integer usuarioId) { return repository.findByUsuarioId(usuarioId); }
    public void vaciarCarrito(Integer usuarioId) { repository.deleteAll(verCarrito(usuarioId)); }
}