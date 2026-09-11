package com.pae.api.controller;

import com.pae.api.entity.DetalleSalida;
import com.pae.api.repository.DetalleSalidaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo DetalleSalida (lineas de una salida de mercancia).
 * Expone el CRUD (Create, Read, Update, Delete) generado por Spring Data JPA
 * a traves de endpoints REST bajo /api/**.
 */
@RestController
@RequestMapping("/api/detalles-salida")
public class DetalleSalidaController {

    private final DetalleSalidaRepository repositorio;

    public DetalleSalidaController(DetalleSalidaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<DetalleSalida> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public DetalleSalida buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/salida/{idSalida}")
    public List<DetalleSalida> buscarPorSalida(@PathVariable Long idSalida) {
        return repositorio.findBySalida_IdSalida(idSalida);
    }

    @PostMapping
    public DetalleSalida crear(@RequestBody DetalleSalida detalle) {
        return repositorio.save(detalle);
    }

    @PutMapping("/{id}")
    public DetalleSalida actualizar(@PathVariable Long id, @RequestBody DetalleSalida datos) {
        datos.setIdDetalleSalida(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
