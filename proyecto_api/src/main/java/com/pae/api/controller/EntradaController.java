package com.pae.api.controller;

import com.pae.api.entity.Entrada;
import com.pae.api.repository.EntradaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo Entrada (ingreso de mercancia a una bodega).
 * Expone el CRUD (Create, Read, Update, Delete) generado por Spring Data JPA
 * a traves de endpoints REST bajo /api/**.
 */
@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    private final EntradaRepository repositorio;

    public EntradaController(EntradaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Entrada> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Entrada buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/bodega/{idBodega}")
    public List<Entrada> buscarPorBodega(@PathVariable Long idBodega) {
        return repositorio.findByBodega_IdBodega(idBodega);
    }

    @PostMapping
    public Entrada crear(@RequestBody Entrada entrada) {
        return repositorio.save(entrada);
    }

    @PutMapping("/{id}")
    public Entrada actualizar(@PathVariable Long id, @RequestBody Entrada datos) {
        datos.setIdEntrada(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
