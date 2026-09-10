package com.pae.api.controller;

import com.pae.api.entity.Entrada;
import com.pae.api.repository.EntradaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
