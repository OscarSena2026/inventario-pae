package com.pae.modelo;

public class DetalleEntrada {
    private int idDetalleEntrada;
    private int idEntrada;
    private int idLote;
    private double cantidad;
    private double valorUnitario;

    public DetalleEntrada() {}

    public DetalleEntrada(int idEntrada, int idLote, double cantidad, double valorUnitario) {
        this.idEntrada = idEntrada;
        this.idLote = idLote;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
    }

    public DetalleEntrada(int idDetalleEntrada, int idEntrada, int idLote, double cantidad, double valorUnitario) {
        this.idDetalleEntrada = idDetalleEntrada;
        this.idEntrada = idEntrada;
        this.idLote = idLote;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
    }

    public int getIdDetalleEntrada() { return idDetalleEntrada; }
    public void setIdDetalleEntrada(int idDetalleEntrada) { this.idDetalleEntrada = idDetalleEntrada; }

    public int getIdEntrada() { return idEntrada; }
    public void setIdEntrada(int idEntrada) { this.idEntrada = idEntrada; }

    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }

    @Override
    public String toString() {
        return "[" + idDetalleEntrada + "] lote " + idLote + " x " + cantidad + " @ " + valorUnitario;
    }
}
