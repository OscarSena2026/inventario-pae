package com.pae.api.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa la tabla "bodega".
 * Relación: una Bodega pertenece a un Municipio (muchos a uno).
 */
@Entity
@Table(name = "bodega")
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bodega")
    private Long idBodega;

    @Column(name = "nombre_bodega", nullable = false, length = 80)
    private String nombreBodega;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    public Bodega() {}

    public Long getIdBodega() { return idBodega; }
    public void setIdBodega(Long idBodega) { this.idBodega = idBodega; }

    public String getNombreBodega() { return nombreBodega; }
    public void setNombreBodega(String nombreBodega) { this.nombreBodega = nombreBodega; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Municipio getMunicipio() { return municipio; }
    public void setMunicipio(Municipio municipio) { this.municipio = municipio; }
}
