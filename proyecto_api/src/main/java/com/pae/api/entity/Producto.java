package com.pae.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entidad JPA que representa la tabla "producto".
 * "perecedero" se maneja como String ("SI"/"NO") igual que en el ENUM de MySQL.
 * Incluye Bean Validation (HU-02, HU-08): campos obligatorios y formato de "perecedero".
 */
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 80, message = "El nombre del producto no puede superar 80 caracteres")
    @Column(name = "nombre_producto", nullable = false, length = 80)
    private String nombreProducto;

    @Size(max = 40, message = "La categoria no puede superar 40 caracteres")
    @Column(name = "categoria", length = 40)
    private String categoria;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 15, message = "La unidad de medida no puede superar 15 caracteres")
    @Column(name = "unidad_medida", nullable = false, length = 15)
    private String unidadMedida;

    @NotBlank(message = "El campo perecedero es obligatorio")
    @Pattern(regexp = "SI|NO", message = "El campo perecedero solo acepta 'SI' o 'NO'")
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
