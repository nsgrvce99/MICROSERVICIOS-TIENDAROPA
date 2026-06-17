package com.tienda.resenas.service;
import com.tienda.resenas.model.Resena;
import com.tienda.resenas.repository.ResenaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResenaService {
    private final ResenaRepository repository;
    public ResenaService(ResenaRepository repository) { this.repository = repository; }
    
    public Resena agregarResena(Resena resena) { return repository.save(resena); }
    public List<Resena> verResenasPorPrenda(Integer prendaId) { return repository.findByPrendaId(prendaId); }
}