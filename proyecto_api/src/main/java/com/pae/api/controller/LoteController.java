package com.pae.api.controller;

import com.pae.api.entity.Lote;
import com.pae.api.repository.LoteRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    private final LoteRepository repositorio;

    public LoteController(LoteRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Lote> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Lote buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/producto/{idProducto}")
    public List<Lote> buscarPorProducto(@PathVariable Long idProducto) {
        return repositorio.findByProducto_IdProducto(idProducto);
    }

    // Lotes que ya vencieron o vencen antes de la fecha dada (ej: hoy)
    @GetMapping("/vencidos")
    public List<Lote> vencidosAntesDe(@RequestParam("fecha") String fechaIso) {
        return repositorio.findByFechaVencimientoBefore(LocalDate.parse(fechaIso));
    }

    @PostMapping
    public Lote crear(@RequestBody Lote lote) {
        return repositorio.save(lote);
    }

    @PutMapping("/{id}")
    public Lote actualizar(@PathVariable Long id, @RequestBody Lote datos) {
        datos.setIdLote(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}
