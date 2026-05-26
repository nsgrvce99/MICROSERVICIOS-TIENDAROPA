package com.tienda.catalogo.service;

import com.tienda.catalogo.dto.PrendaSimpleDTO;
import com.tienda.catalogo.model.Prenda;
import com.tienda.catalogo.repository.PrendaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrendaService {
    private final PrendaRepository repository;

    public PrendaService(PrendaRepository repository) {
        this.repository = repository;
    }

    public Prenda guardarPrenda(Prenda prenda) {
        return repository.save(prenda);
    }
    public List<PrendaSimpleDTO> listarCatalogoSimple() {
        return repository.findAll().stream().map(prenda -> {
            PrendaSimpleDTO dto = new PrendaSimpleDTO();
            dto.setNombre(prenda.getNombre());
            dto.setPrecio(prenda.getPrecio());
            return dto;
        }).collect(Collectors.toList());
    }
    
    public Prenda buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

       public String eliminar(Integer id) {
       List<Prenda> lista = repository.findAll();
       for (Prenda p : lista) {
           if (p.getId().equals(id)) {
               repository.deleteById(id);
               return "Prenda eliminada correctamente";
           }

       }
       return "Prenda no encontrada";
   }

   public String actualizar(Prenda prenda) {
       List<Prenda> lista = repository.findAll();
       for (Prenda p : lista) {
           if (p.getId().equals(prenda.getId())) {
               repository.save(prenda);
               return "Prenda actualizada";
           }
       }
       return "Prenda no encontrada";

   }
}
