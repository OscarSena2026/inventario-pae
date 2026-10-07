package com.pae.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración CORS de la API (GA7-220501096-AA4-EV03).
 *
 * El front-end (React + Vite) se ejecuta en http://localhost:5173 y la API en http://localhost:8080.
 * Como son orígenes distintos, el navegador bloquea las peticiones si la API no las permite.
 * Esta clase autoriza al front-end a usar los endpoints /api/**.
 *
 * Instalación: copiar este archivo en
 *   proyecto_api/src/main/java/com/pae/api/config/CorsConfig.java
 * y reiniciar la API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
