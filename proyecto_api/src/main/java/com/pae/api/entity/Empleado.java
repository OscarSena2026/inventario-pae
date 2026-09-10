package com.pae.api.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa la tabla "empleado".
 */
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long idEmpleado;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "cargo", length = 40)
    private String cargo;

    @Column(name = "telefono", length = 15)
    private String telefono;

    public Empleado() {}

    public Long getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Long idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
