package com.pae.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Entidad JPA que representa la tabla "detalle_entrada".
 * Relaciones: pertenece a una Entrada y a un Lote (muchos a uno).
 * Bean Validation (HU-03): cantidad y valor unitario deben ser mayores a cero.
 */
@Entity
@Table(name = "detalle_entrada")
public class DetalleEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_entrada")
    private Long idDetalleEntrada;

    @NotNull(message = "La entrada es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_entrada", nullable = false)
    private Entrada entrada;

    @NotNull(message = "El lote es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Column(name = "cantidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @NotNull(message = "El valor unitario es obligatorio")
    @Positive(message = "El valor unitario debe ser mayor a cero")
    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;


    public DetalleEntrada() {}

    public Long getIdDetalleEntrada() { return idDetalleEntrada; }
    public void setIdDetalleEntrada(Long idDetalleEntrada) { this.idDetalleEntrada = idDetalleEntrada; }

    public Entrada getEntrada() { return entrada; }
    public void setEntrada(Entrada entrada) { this.entrada = entrada; }

    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }
}
