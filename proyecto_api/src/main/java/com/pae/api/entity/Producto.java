package com.pae.api.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa la tabla "producto".
 * "perecedero" se maneja como String ("SI"/"NO") igual que en el ENUM de MySQL.
 */
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto", nullable = false, length = 80)
    private String nombreProducto;

    @Column(name = "categoria", length = 40)
    private String categoria;

    @Column(name = "unidad_medida", nullable = false, length = 15)
    private String unidadMedida;

    @Column(name = "perecedero", nullable = false, length = 2)
    private String perecedero; // "SI" o "NO"

    public Producto() {}

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public String getPerecedero() { return perecedero; }
    public void setPerecedero(String perecedero) { this.perecedero = perecedero; }
}
