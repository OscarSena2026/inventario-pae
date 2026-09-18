package com.pae.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Entidad JPA que representa la tabla "detalle_salida".
 * Relaciones: pertenece a una Salida y a un Lote (muchos a uno).
 * Bean Validation (HU-04): la cantidad debe ser mayor a cero.
 * La verificacion de stock disponible se hace en el controlador (regla de negocio).
 */
@Entity
@Table(name = "detalle_salida")
public class DetalleSalida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_salida")
    private Long idDetalleSalida;

    @NotNull(message = "La salida es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_salida", nullable = false)
    private Salida salida;

    @NotNull(message = "El lote es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(name = "cantidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;


    public DetalleSalida() {}

    public Long getIdDetalleSalida() { return idDetalleSalida; }
    public void setIdDetalleSalida(Long idDetalleSalida) { this.idDetalleSalida = idDetalleSalida; }

    public Salida getSalida() { return salida; }
    public void setSalida(Salida salida) { this.salida = salida; }

    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
}
