package com.tienda.promociones.service;
import com.tienda.promociones.model.Cupon;
import com.tienda.promociones.repository.CuponRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CuponService {
    private final CuponRepository repository;
    public CuponService(CuponRepository repository) { this.repository = repository; }
    
    public Cupon crearCupon(Cupon cupon) { return repository.save(cupon); }
    public Optional<Cupon> validarCupon(String codigo) { return repository.findByCodigo(codigo); }
}