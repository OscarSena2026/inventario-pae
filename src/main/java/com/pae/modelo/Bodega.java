package com.pae.modelo;

public class Bodega {
    private int idBodega;
    private String nombreBodega;
    private String direccion;
    private int idMunicipio;

    public Bodega() {}

    public Bodega(String nombreBodega, String direccion, int idMunicipio) {
        this.nombreBodega = nombreBodega;
        this.direccion = direccion;
        this.idMunicipio = idMunicipio;
    }

    public Bodega(int idBodega, String nombreBodega, String direccion, int idMunicipio) {
        this.idBodega = idBodega;
        this.nombreBodega = nombreBodega;
        this.direccion = direccion;
        this.idMunicipio = idMunicipio;
    }

    public int getIdBodega() { return idBodega; }
    public void setIdBodega(int idBodega) { this.idBodega = idBodega; }

    public String getNombreBodega() { return nombreBodega; }
    public void setNombreBodega(String nombreBodega) { this.nombreBodega = nombreBodega; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(int idMunicipio) { this.idMunicipio = idMunicipio; }

    @Override
    public String toString() {
        return "[" + idBodega + "] " + nombreBodega;
    }
}
