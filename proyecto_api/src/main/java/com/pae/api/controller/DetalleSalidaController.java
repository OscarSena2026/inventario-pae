package com.pae.api.controller;

import com.pae.api.entity.DetalleSalida;
import com.pae.api.entity.Existencia;
import com.pae.api.entity.Salida;
import com.pae.api.exception.ReglaNegocioException;
import com.pae.api.repository.DetalleSalidaRepository;
import com.pae.api.repository.ExistenciaRepository;
import com.pae.api.repository.SalidaRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo DetalleSalida (lineas de una salida de mercancia).
 * Implementa HU-04 (registrar salida de stock), incluyendo su criterio de
 * aceptacion mas importante: "Bloquea si la cantidad supera el stock
 * disponible" y "Stock se descuenta al confirmar la salida".
 */
@RestController
@RequestMapping("/api/detalles-salida")
public class DetalleSalidaController {

    private final DetalleSalidaRepository repositorio;
    private final SalidaRepository salidaRepositorio;
    private final ExistenciaRepository existenciaRepositorio;

    public DetalleSalidaController(DetalleSalidaRepository repositorio,
                                    SalidaRepository salidaRepositorio,
                                    ExistenciaRepository existenciaRepositorio) {
        this.repositorio = repositorio;
        this.salidaRepositorio = salidaRepositorio;
        this.existenciaRepositorio = existenciaRepositorio;
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
    public DetalleSalida crear(@Valid @RequestBody DetalleSalida detalle) {
        Salida salida = salidaRepositorio.findById(detalle.getSalida().getIdSalida())
                .orElseThrow(() -> new ReglaNegocioException("La salida indicada no existe"));

        Existencia existencia = existenciaRepositorio.findByBodega_IdBodegaAndLote_IdLote(
                salida.getBodega().getIdBodega(), detalle.getLote().getIdLote());

        // HU-04: "Bloquea si la cantidad supera el stock disponible"
        if (existencia == null || existencia.getCantidadDisponible().compareTo(detalle.getCantidad()) < 0) {
            throw new ReglaNegocioException(
                    "Stock insuficiente: no hay suficiente cantidad disponible de este lote en la bodega para despachar "
                            + detalle.getCantidad());
        }

        DetalleSalida guardado = repositorio.save(detalle);

        // HU-04: "Stock se descuenta al confirmar la salida"
        existencia.setCantidadDisponible(existencia.getCantidadDisponible().subtract(detalle.getCantidad()));
        existenciaRepositorio.save(existencia);

        return guardado;
    }

    @PutMapping("/{id}")
    public DetalleSalida actualizar(@PathVariable Long id, @Valid @RequestBody DetalleSalida datos) {
        datos.setIdDetalleSalida(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}

