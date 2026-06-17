package com.tienda.pagos.service;

import com.tienda.pagos.model.Pago;
import com.tienda.pagos.repository.PagoRepository;
import org.springframework.stereotype.Service;

@Service
public class PagoService {
    private final PagoRepository repository;

    public PagoService(PagoRepository repository) {
        this.repository = repository;
    }

    public Pago procesarPago(Pago pago) {
        pago.setEstado("APROBADO");
        return repository.save(pago);
    }
}