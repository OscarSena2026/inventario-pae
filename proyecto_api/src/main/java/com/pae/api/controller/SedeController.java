package com.pae.api.controller;

import com.pae.api.entity.Sede;
import com.pae.api.repository.SedeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo Sede (institucion educativa que recibe el PAE).
 * Expone el CRUD (Create, Read, Update, Delete) generado por Spring Data JPA
 * a traves de endpoints REST bajo /api/**.
 */
@RestController
@RequestMapping("/api/sedes")
public class SedeController {

    private final SedeRepository repositorio;

    public SedeController(SedeRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Sede> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Sede buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/municipio/{idMunicipio}")
    public List<Sede> buscarPorMunicipio(@PathVariable Long idMunicipio) {
        return repositorio.findByMunicipio_IdMunicipio(idMunicipio);
    }

    @PostMapping
    public Sede crear(@RequestBody Sede sede) {
        return repositorio.save(sede);
    }

    @PutMapping("/{id}")
    public Sede actualizar(@PathVariable Long id, @RequestBody Sede datos) {
        datos.setIdSede(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
