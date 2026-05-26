package com.tienda.inventario.service;

import com.tienda.inventario.model.Stock;
import com.tienda.inventario.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StockService {
    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public Stock inicializarStock(Stock stock) {
        return repository.save(stock);
    }

    public boolean verificarDisponibilidad(Integer prendaId, Integer cantidad) {
        Optional<Stock> stock = repository.findByPrendaId(prendaId);
        return stock.isPresent() && stock.get().getCantidadDisponible() >= cantidad;
    }

    public Stock descontarStock(Integer prendaId, Integer cantidad) {
        Optional<Stock> stockOpt = repository.findByPrendaId(prendaId);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            if (stock.getCantidadDisponible() >= cantidad) {
                stock.setCantidadDisponible(stock.getCantidadDisponible() - cantidad);
                return repository.save(stock);
            }
        }
        return null;
    }
}