package com.pae.modelo;

public class DetalleSalida {
    private int idDetalleSalida;
    private int idSalida;
    private int idLote;
    private double cantidad;

    public DetalleSalida() {}

    public DetalleSalida(int idSalida, int idLote, double cantidad) {
        this.idSalida = idSalida;
        this.idLote = idLote;
        this.cantidad = cantidad;
    }

    public DetalleSalida(int idDetalleSalida, int idSalida, int idLote, double cantidad) {
        this.idDetalleSalida = idDetalleSalida;
        this.idSalida = idSalida;
        this.idLote = idLote;
        this.cantidad = cantidad;
    }

    public int getIdDetalleSalida() { return idDetalleSalida; }
    public void setIdDetalleSalida(int idDetalleSalida) { this.idDetalleSalida = idDetalleSalida; }

    public int getIdSalida() { return idSalida; }
    public void setIdSalida(int idSalida) { this.idSalida = idSalida; }

    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return "[" + idDetalleSalida + "] lote " + idLote + " x " + cantidad;
    }
}
