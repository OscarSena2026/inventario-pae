package com.pae.modelo;

import java.time.LocalDate;

public class Lote {
    private int idLote;
    private LocalDate fechaFabricacion;
    private LocalDate fechaVencimiento;
    private double cantidadRecibida;
    private int idProducto;
    private int idProveedor;

    public Lote() {}

    public Lote(LocalDate fechaFabricacion, LocalDate fechaVencimiento, double cantidadRecibida,
                int idProducto, int idProveedor) {
        this.fechaFabricacion = fechaFabricacion;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidadRecibida = cantidadRecibida;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
    }

    public Lote(int idLote, LocalDate fechaFabricacion, LocalDate fechaVencimiento, double cantidadRecibida,
                int idProducto, int idProveedor) {
        this.idLote = idLote;
        this.fechaFabricacion = fechaFabricacion;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidadRecibida = cantidadRecibida;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
    }

    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }

    public LocalDate getFechaFabricacion() { return fechaFabricacion; }
    public void setFechaFabricacion(LocalDate fechaFabricacion) { this.fechaFabricacion = fechaFabricacion; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public double getCantidadRecibida() { return cantidadRecibida; }
    public void setCantidadRecibida(double cantidadRecibida) { this.cantidadRecibida = cantidadRecibida; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    @Override
    public String toString() {
        return "[" + idLote + "] vence " + fechaVencimiento + " - cant. " + cantidadRecibida;
    }
}
