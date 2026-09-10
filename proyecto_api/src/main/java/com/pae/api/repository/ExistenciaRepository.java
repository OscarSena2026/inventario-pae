package com.pae.api.repository;

import com.pae.api.entity.Existencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExistenciaRepository extends JpaRepository<Existencia, Long> {

    // Consulta clave del inventario: existencias de una bodega
    List<Existencia> findByBodega_IdBodega(Long idBodega);

    // Existencia puntual de un lote en una bodega (para sumar/restar stock)
    Existencia findByBodega_IdBodegaAndLote_IdLote(Long idBodega, Long idLote);
}
