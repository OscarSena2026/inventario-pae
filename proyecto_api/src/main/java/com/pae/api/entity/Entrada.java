package com.pae.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la tabla "entrada" (ingreso de mercancia a una bodega).
 * Relaciones: pertenece a una Bodega, un Proveedor y un Empleado (muchos a uno).
 */
@Entity
@Table(name = "entrada")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrada")
    private Long idEntrada;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "numero_factura", length = 20)
    private String numeroFactura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bodega", nullable = false)
    private Bodega bodega;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    public Entrada() {}

    public Long getIdEntrada() { return idEntrada; }
    public void setIdEntrada(Long idEntrada) { this.idEntrada = idEntrada; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public Bodega getBodega() { return bodega; }
    public void setBodega(Bodega bodega) { this.bodega = bodega; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
}
