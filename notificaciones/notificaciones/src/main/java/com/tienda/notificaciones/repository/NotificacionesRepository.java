package com.tienda.notificaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.notificaciones.model.notificaciones;

public interface NotificacionesRepository extends JpaRepository<notificaciones, Integer> {
    Optional<notificaciones> findById(Integer Id);
}
