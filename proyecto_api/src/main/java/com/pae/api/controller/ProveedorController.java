package com.pae.api.controller;

import com.pae.api.entity.Proveedor;
import com.pae.api.repository.ProveedorRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo Proveedor.
 * Expone el CRUD (Create, Read, Update, Delete) generado por Spring Data JPA
 * a traves de endpoints REST bajo /api/**.
 */
@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorRepository repositorio;

    public ProveedorController(ProveedorRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Proveedor> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Proveedor buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/nit/{nit}")
    public Proveedor buscarPorNit(@PathVariable String nit) {
        return repositorio.findByNit(nit);
    }

    @PostMapping
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return repositorio.save(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable Long id, @RequestBody Proveedor datos) {
        datos.setIdProveedor(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
