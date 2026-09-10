package com.pae.api.repository;

import com.pae.api.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByPerecedero(String perecedero);
}
