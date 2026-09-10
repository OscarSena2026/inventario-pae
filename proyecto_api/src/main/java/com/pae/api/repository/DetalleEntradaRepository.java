package com.pae.api.repository;

import com.pae.api.entity.DetalleEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleEntradaRepository extends JpaRepository<DetalleEntrada, Long> {

    List<DetalleEntrada> findByEntrada_IdEntrada(Long idEntrada);
}
