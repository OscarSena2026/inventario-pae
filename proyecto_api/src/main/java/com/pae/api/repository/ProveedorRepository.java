package com.pae.api.repository;

import com.pae.api.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Spring Data JPA construye la consulta a partir del nombre del metodo
    Proveedor findByNit(String nit);
}
