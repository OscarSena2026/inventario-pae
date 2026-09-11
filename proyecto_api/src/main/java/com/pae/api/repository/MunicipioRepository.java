package com.pae.api.repository;

import com.pae.api.entity.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA de la entidad Municipio (tabla maestra, sin llaves foraneas).
 */
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
}
