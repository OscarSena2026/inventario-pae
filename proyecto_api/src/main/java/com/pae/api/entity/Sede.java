package com.pae.api.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa la tabla "sede".
 * Relación: una Sede pertenece a un Municipio (muchos a uno).
 */
@Entity
@Table(name = "sede")
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Long idSede;

    @Column(name = "nombre_sede", nullable = false, length = 100)
    private String nombreSede;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    public Sede() {}

    public Long getIdSede() { return idSede; }
    public void setIdSede(Long idSede) { this.idSede = idSede; }

    public String getNombreSede() { return nombreSede; }
    public void setNombreSede(String nombreSede) { this.nombreSede = nombreSede; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Municipio getMunicipio() { return municipio; }
    public void setMunicipio(Municipio municipio) { this.municipio = municipio; }
}
