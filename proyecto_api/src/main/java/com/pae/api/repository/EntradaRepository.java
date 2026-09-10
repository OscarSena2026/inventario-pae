package com.pae.api.repository;

import com.pae.api.entity.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    List<Entrada> findByBodega_IdBodega(Long idBodega);
}
