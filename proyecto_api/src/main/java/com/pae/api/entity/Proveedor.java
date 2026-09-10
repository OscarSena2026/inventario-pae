package com.pae.api.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa la tabla "proveedor".
 */
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;

    @Column(name = "nombre_proveedor", nullable = false, length = 100)
    private String nombreProveedor;

    @Column(name = "nit", nullable = false, length = 15)
    private String nit;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "direccion", length = 100)
    private String direccion;

    public Proveedor() {}

    public Long getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Long idProveedor) { this.idProveedor = idProveedor; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
