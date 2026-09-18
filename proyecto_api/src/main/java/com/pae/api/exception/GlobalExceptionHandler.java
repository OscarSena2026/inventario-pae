package com.pae.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manejador global de errores de la API.
 * Convierte las excepciones de validacion (Bean Validation) y de reglas de
 * negocio (ReglaNegocioException) en respuestas JSON claras con codigo 400,
 * en vez del stacktrace generico que Spring Boot devuelve por defecto.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Se dispara cuando @Valid encuentra campos invalidos (@NotBlank, @Positive, @Pattern, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400);
        body.put("error", "Datos invalidos");
        body.put("detalles", errores);

        return ResponseEntity.badRequest().body(body);
    }

    // Se dispara cuando una regla de negocio personalizada falla
    // (producto duplicado, stock insuficiente, etc.)
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Map<String, Object>> manejarReglaNegocio(ReglaNegocioException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400);
        body.put("error", "Regla de negocio violada");
        body.put("detalles", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }
}
