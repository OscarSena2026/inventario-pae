package com.pae.api.repository;

import com.pae.api.entity.DetalleEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA de la entidad DetalleEntrada (lineas de una entrada de mercancia).
 */
public interface DetalleEntradaRepository extends JpaRepository<DetalleEntrada, Long> {

    List<DetalleEntrada> findByEntrada_IdEntrada(Long idEntrada);
}
