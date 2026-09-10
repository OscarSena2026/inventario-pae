package com.pae.api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entidad JPA que representa la tabla "existencia" (stock disponible por bodega y lote).
 * Relaciones: pertenece a una Bodega y a un Lote (muchos a uno).
 */
@Entity
@Table(name = "existencia")
public class Existencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_existencia")
    private Long idExistencia;

    @Column(name = "cantidad_disponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadDisponible;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bodega", nullable = false)
    private Bodega bodega;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    public Existencia() {}

    public Long getIdExistencia() { return idExistencia; }
    public void setIdExistencia(Long idExistencia) { this.idExistencia = idExistencia; }

    public BigDecimal getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(BigDecimal cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public Bodega getBodega() { return bodega; }
    public void setBodega(Bodega bodega) { this.bodega = bodega; }

    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
}
