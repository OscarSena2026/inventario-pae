package com.pae.api.controller;

import com.pae.api.entity.DetalleEntrada;
import com.pae.api.repository.DetalleEntradaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalles-entrada")
public class DetalleEntradaController {

    private final DetalleEntradaRepository repositorio;

    public DetalleEntradaController(DetalleEntradaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<DetalleEntrada> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public DetalleEntrada buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/entrada/{idEntrada}")
    public List<DetalleEntrada> buscarPorEntrada(@PathVariable Long idEntrada) {
        return repositorio.findByEntrada_IdEntrada(idEntrada);
    }

    @PostMapping
    public DetalleEntrada crear(@RequestBody DetalleEntrada detalle) {
        return repositorio.save(detalle);
    }

    @PutMapping("/{id}")
    public DetalleEntrada actualizar(@PathVariable Long id, @RequestBody DetalleEntrada datos) {
        datos.setIdDetalleEntrada(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
