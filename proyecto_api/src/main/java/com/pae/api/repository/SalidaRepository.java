package com.pae.api.repository;

import com.pae.api.entity.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA de la entidad Salida (despacho de mercancia hacia una sede).
 */
public interface SalidaRepository extends JpaRepository<Salida, Long> {

    List<Salida> findBySede_IdSede(Long idSede);
}
