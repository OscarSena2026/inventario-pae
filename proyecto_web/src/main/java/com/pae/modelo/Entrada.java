package com.pae.modelo;

import java.time.LocalDate;

public class Entrada {
    private int idEntrada;
    private LocalDate fechaEntrada;
    private String numeroFactura;
    private int idBodega;
    private int idProveedor;
    private int idEmpleado;

    public Entrada() {}

    public Entrada(LocalDate fechaEntrada, String numeroFactura, int idBodega, int idProveedor, int idEmpleado) {
        this.fechaEntrada = fechaEntrada;
        this.numeroFactura = numeroFactura;
        this.idBodega = idBodega;
        this.idProveedor = idProveedor;
        this.idEmpleado = idEmpleado;
    }

    public Entrada(int idEntrada, LocalDate fechaEntrada, String numeroFactura, int idBodega, int idProveedor, int idEmpleado) {
        this.idEntrada = idEntrada;
        this.fechaEntrada = fechaEntrada;
        this.numeroFactura = numeroFactura;
        this.idBodega = idBodega;
        this.idProveedor = idProveedor;
        this.idEmpleado = idEmpleado;
    }

    public int getIdEntrada() { return idEntrada; }
    public void setIdEntrada(int idEntrada) { this.idEntrada = idEntrada; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public int getIdBodega() { return idBodega; }
    public void setIdBodega(int idBodega) { this.idBodega = idBodega; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    @Override
    public String toString() {
        return "[" + idEntrada + "] " + fechaEntrada + " factura " + numeroFactura;
    }
}
