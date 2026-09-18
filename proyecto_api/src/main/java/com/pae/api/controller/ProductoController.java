package com.pae.api.controller;

import com.pae.api.entity.Producto;
import com.pae.api.exception.ReglaNegocioException;
import com.pae.api.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del modulo Producto.
 * El framework (Spring Data JPA) ya nos da el CRUD basico via ProductoRepository;
 * aqui lo adaptamos a las rutas del inventario PAE.
 * Implementa HU-02 (registrar producto) y HU-08 (modificar producto), incluyendo
 * sus criterios de aceptacion: campos obligatorios y sin nombres duplicados.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository repositorio;

    public ProductoController(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Producto> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Producto buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Producto> buscarPorCategoria(@PathVariable String categoria) {
        return repositorio.findByCategoria(categoria);
    }

    @GetMapping("/perecederos/{perecedero}")
    public List<Producto> buscarPorPerecedero(@PathVariable String perecedero) {
        return repositorio.findByPerecedero(perecedero);
    }

    @PostMapping
    public Producto crear(@Valid @RequestBody Producto producto) {
        // HU-02: "No permite codigos duplicados" (adaptado a nombre_producto)
        if (repositorio.existsByNombreProductoIgnoreCase(producto.getNombreProducto())) {
            throw new ReglaNegocioException(
                    "Ya existe un producto registrado con el nombre '" + producto.getNombreProducto() + "'");
        }
        return repositorio.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @Valid @RequestBody Producto datos) {
        datos.setIdProducto(id);
        return repositorio.save(datos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repositorio.deleteById(id);
    }
}

