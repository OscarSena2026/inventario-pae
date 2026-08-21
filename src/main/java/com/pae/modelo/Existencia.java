package com.pae.modelo;

public class Existencia {
    private int idExistencia;
    private double cantidadDisponible;
    private int idBodega;
    private int idLote;

    public Existencia() {}

    public Existencia(double cantidadDisponible, int idBodega, int idLote) {
        this.cantidadDisponible = cantidadDisponible;
        this.idBodega = idBodega;
        this.idLote = idLote;
    }

    public Existencia(int idExistencia, double cantidadDisponible, int idBodega, int idLote) {
        this.idExistencia = idExistencia;
        this.cantidadDisponible = cantidadDisponible;
        this.idBodega = idBodega;
        this.idLote = idLote;
    }

    public int getIdExistencia() { return idExistencia; }
    public void setIdExistencia(int idExistencia) { this.idExistencia = idExistencia; }

    public double getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(double cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public int getIdBodega() { return idBodega; }
    public void setIdBodega(int idBodega) { this.idBodega = idBodega; }

    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }

    @Override
    public String toString() {
        return "[" + idExistencia + "] bodega " + idBodega + " lote " + idLote + " -> " + cantidadDisponible;
    }
}
