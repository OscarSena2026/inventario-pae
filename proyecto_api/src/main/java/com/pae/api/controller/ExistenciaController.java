package com.pae.api.controller;

import com.pae.api.entity.Existencia;
import com.pae.api.repository.ExistenciaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo Existencia (stock disponible).
 * Es el endpoint mas consultado tanto desde la web (panel de inventario)
 * como desde la app movil (consulta rapida de stock en bodega).
 */
@RestController
@RequestMapping("/api/existencias")
public class ExistenciaController {

    private final ExistenciaRepository repositorio;

    public ExistenciaController(ExistenciaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Existencia> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Existencia buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/bodega/{idBodega}")
    public List<Existencia> buscarPorBodega(@PathVariable Long idBodega) {
        return repositorio.findByBodega_IdBodega(idBodega);
    }

    @PostMapping
    public Existencia crear(@RequestBody Existencia existencia) {
        return repositorio.save(existencia);
    }

    @PutMapping("/{id}")
    public Existencia actualizar(@PathVariable Long id, @RequestBody Existencia datos) {
        datos.setIdExistencia(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
