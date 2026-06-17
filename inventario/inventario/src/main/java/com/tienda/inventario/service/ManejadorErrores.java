package com.tienda.inventario.service;

import com.tienda.inventario.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarErroresValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        ErrorDTO error = new ErrorDTO(LocalDateTime.now(), 400, "Error de validación", errores, request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarBD(DataIntegrityViolationException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(LocalDateTime.now(), 400, "Violación de integridad (Dato duplicado)", null, request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }
}