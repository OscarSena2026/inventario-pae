package com.pae.api.controller;

import com.pae.api.entity.Bodega;
import com.pae.api.repository.BodegaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo Bodega.
 * Expone el CRUD (Create, Read, Update, Delete) generado por Spring Data JPA
 * a traves de endpoints REST bajo /api/**.
 */
@RestController
@RequestMapping("/api/bodegas")
public class BodegaController {

    private final BodegaRepository repositorio;

    public BodegaController(BodegaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Bodega> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Bodega buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/municipio/{idMunicipio}")
    public List<Bodega> buscarPorMunicipio(@PathVariable Long idMunicipio) {
        return repositorio.findByMunicipio_IdMunicipio(idMunicipio);
    }

    @PostMapping
    public Bodega crear(@RequestBody Bodega bodega) {
        return repositorio.save(bodega);
    }

    @PutMapping("/{id}")
    public Bodega actualizar(@PathVariable Long id, @RequestBody Bodega datos) {
        datos.setIdBodega(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
