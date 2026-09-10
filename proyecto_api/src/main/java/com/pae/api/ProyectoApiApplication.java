package com.pae.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la API REST del Sistema de Inventario PAE (GA7-AA3-EV01).
 *
 * Esta misma API la consumen dos clientes distintos:
 *  - El modulo WEB (proyecto_web, en un futuro un frontend que llame a /api/**)
 *  - El modulo MOVIL (app Android/Kotlin consumiendo /api/** por HTTP/Retrofit)
 *
 * Spring Boot arranca un servidor Tomcat embebido en el puerto 8080.
 */
@SpringBootApplication
public class ProyectoApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProyectoApiApplication.class, args);
    }
}
