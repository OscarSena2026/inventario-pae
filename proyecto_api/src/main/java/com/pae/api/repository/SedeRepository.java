package com.pae.api.repository;

import com.pae.api.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA de la entidad Sede (institucion educativa que recibe el PAE).
 */
public interface SedeRepository extends JpaRepository<Sede, Long> {

    List<Sede> findByMunicipio_IdMunicipio(Long idMunicipio);
}
