package com.pae.api.controller;

import com.pae.api.entity.Empleado;
import com.pae.api.repository.EmpleadoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoRepository repositorio;

    public EmpleadoController(EmpleadoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Empleado> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Empleado buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/cargo/{cargo}")
    public List<Empleado> buscarPorCargo(@PathVariable String cargo) {
        return repositorio.findByCargo(cargo);
    }

    @PostMapping
    public Empleado crear(@RequestBody Empleado empleado) {
        return repositorio.save(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @RequestBody Empleado datos) {
        datos.setIdEmpleado(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
