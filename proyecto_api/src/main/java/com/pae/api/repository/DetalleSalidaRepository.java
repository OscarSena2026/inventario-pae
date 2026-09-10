package com.pae.api.repository;

import com.pae.api.entity.DetalleSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleSalidaRepository extends JpaRepository<DetalleSalida, Long> {

    List<DetalleSalida> findBySalida_IdSalida(Long idSalida);
}
