package com.pae.api.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa la tabla "municipio".
 * Tabla maestra: no depende de otras tablas.
 */
@Entity
@Table(name = "municipio")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long idMunicipio;

    @Column(name = "nombre_municipio", nullable = false, length = 60)
    private String nombreMunicipio;

    public Municipio() {}

    public Long getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(Long idMunicipio) { this.idMunicipio = idMunicipio; }

    public String getNombreMunicipio() { return nombreMunicipio; }
    public void setNombreMunicipio(String nombreMunicipio) { this.nombreMunicipio = nombreMunicipio; }
}
