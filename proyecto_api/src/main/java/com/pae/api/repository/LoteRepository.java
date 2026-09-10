package com.pae.api.repository;

import com.pae.api.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByProducto_IdProducto(Long idProducto);

    // Util para alertas de vencimiento (se documentara/probara en EV02)
    List<Lote> findByFechaVencimientoBefore(LocalDate fecha);
}
