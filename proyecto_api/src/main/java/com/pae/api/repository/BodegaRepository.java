package com.pae.api.repository;

import com.pae.api.entity.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    List<Bodega> findByMunicipio_IdMunicipio(Long idMunicipio);
}
