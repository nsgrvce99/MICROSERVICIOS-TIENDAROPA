package com.tienda.resenas.repository;
import com.tienda.resenas.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<Resena> findByPrendaId(Integer prendaId);
}