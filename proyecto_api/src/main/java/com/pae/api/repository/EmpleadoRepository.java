package com.pae.api.repository;

import com.pae.api.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA de la entidad Empleado.
 */
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByCargo(String cargo);
}
