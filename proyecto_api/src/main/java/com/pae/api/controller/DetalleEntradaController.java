package com.pae.api.controller;

import com.pae.api.entity.DetalleEntrada;
import com.pae.api.entity.Entrada;
import com.pae.api.entity.Existencia;
import com.pae.api.exception.ReglaNegocioException;
import com.pae.api.repository.DetalleEntradaRepository;
import com.pae.api.repository.EntradaRepository;
import com.pae.api.repository.ExistenciaRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo DetalleEntrada (lineas de una entrada de mercancia).
 * Implementa HU-03 (registrar entrada de stock): valida cantidad/valor unitario
 * mayores a cero y ACTUALIZA LA EXISTENCIA sumando la cantidad recibida
 * ("Stock se actualiza sumando la cantidad" - criterio de aceptacion HU-03).
 */
@RestController
@RequestMapping("/api/detalles-entrada")
public class DetalleEntradaController {

    private final DetalleEntradaRepository repositorio;
    private final EntradaRepository entradaRepositorio;
    private final ExistenciaRepository existenciaRepositorio;

    public DetalleEntradaController(DetalleEntradaRepository repositorio,
                                     EntradaRepository entradaRepositorio,
                                     ExistenciaRepository existenciaRepositorio) {
        this.repositorio = repositorio;
        this.entradaRepositorio = entradaRepositorio;
        this.existenciaRepositorio = existenciaRepositorio;
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
    public DetalleEntrada crear(@Valid @RequestBody DetalleEntrada detalle) {
        Entrada entrada = entradaRepositorio.findById(detalle.getEntrada().getIdEntrada())
                .orElseThrow(() -> new ReglaNegocioException("La entrada indicada no existe"));

        DetalleEntrada guardado = repositorio.save(detalle);

        // HU-03: la existencia (stock) de esa bodega+lote suma la cantidad recibida
        Existencia existencia = existenciaRepositorio.findByBodega_IdBodegaAndLote_IdLote(
                entrada.getBodega().getIdBodega(), detalle.getLote().getIdLote());

        if (existencia == null) {
            existencia = new Existencia();
            existencia.setBodega(entrada.getBodega());
            existencia.setLote(detalle.getLote());
            existencia.setCantidadDisponible(detalle.getCantidad());
        } else {
            existencia.setCantidadDisponible(existencia.getCantidadDisponible().add(detalle.getCantidad()));
        }
        existenciaRepositorio.save(existencia);

        return guardado;
    }

    @PutMapping("/{id}")
    public DetalleEntrada actualizar(@PathVariable Long id, @Valid @RequestBody DetalleEntrada datos) {
        datos.setIdDetalleEntrada(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}


