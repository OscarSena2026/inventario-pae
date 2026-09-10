package com.pae.api.controller;

import com.pae.api.entity.Salida;
import com.pae.api.repository.SalidaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/salidas")
public class SalidaController {

    private final SalidaRepository repositorio;

    public SalidaController(SalidaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Salida> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Salida buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/sede/{idSede}")
    public List<Salida> buscarPorSede(@PathVariable Long idSede) {
        return repositorio.findBySede_IdSede(idSede);
    }

    @PostMapping
    public Salida crear(@RequestBody Salida salida) {
        return repositorio.save(salida);
    }

    @PutMapping("/{id}")
    public Salida actualizar(@PathVariable Long id, @RequestBody Salida datos) {
        datos.setIdSalida(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
