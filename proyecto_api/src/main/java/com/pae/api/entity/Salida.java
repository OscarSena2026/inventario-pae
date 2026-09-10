package com.pae.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la tabla "salida" (despacho de mercancia a una sede).
 * Relaciones: pertenece a una Bodega, una Sede y un Empleado (muchos a uno).
 */
@Entity
@Table(name = "salida")
public class Salida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salida")
    private Long idSalida;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "motivo", length = 100)
    private String motivo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bodega", nullable = false)
    private Bodega bodega;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    public Salida() {}

    public Long getIdSalida() { return idSalida; }
    public void setIdSalida(Long idSalida) { this.idSalida = idSalida; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public Bodega getBodega() { return bodega; }
    public void setBodega(Bodega bodega) { this.bodega = bodega; }

    public Sede getSede() { return sede; }
    public void setSede(Sede sede) { this.sede = sede; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
}
