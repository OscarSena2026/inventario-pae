package com.pae.api.exception;

/**
 * Excepcion para reglas de negocio del inventario PAE que no son un simple
 * error de formato (por ejemplo: producto duplicado, stock insuficiente).
 * El GlobalExceptionHandler la convierte en una respuesta HTTP 400 con
 * un mensaje claro para el cliente (Postman, la app web o movil).
 */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
