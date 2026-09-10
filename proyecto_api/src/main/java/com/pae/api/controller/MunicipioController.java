package com.pae.api.controller;

import com.pae.api.entity.Municipio;
import com.pae.api.repository.MunicipioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo Municipio.
 * Estos mismos endpoints son consumidos tanto por el cliente web (fetch/axios)
 * como por la app movil (Retrofit/HTTP) -> un unico backend, dos consumidores.
 */
@RestController
@RequestMapping("/api/municipios")
public class MunicipioController {

    private final MunicipioRepository repositorio; // Inyeccion de dependencias por constructor

    public MunicipioController(MunicipioRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Municipio> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Municipio buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @PostMapping
    public Municipio crear(@RequestBody Municipio municipio) {
        return repositorio.save(municipio);
    }

    @PutMapping("/{id}")
    public Municipio actualizar(@PathVariable Long id, @RequestBody Municipio datos) {
        datos.setIdMunicipio(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
