package com.pae.api.repository;

import com.pae.api.entity.DetalleSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA de la entidad DetalleSalida (lineas de una salida de mercancia).
 */
public interface DetalleSalidaRepository extends JpaRepository<DetalleSalida, Long> {

    List<DetalleSalida> findBySalida_IdSalida(Long idSalida);
}
