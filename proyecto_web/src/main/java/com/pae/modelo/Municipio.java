package com.pae.modelo;

public class Municipio {
    private int idMunicipio;
    private String nombreMunicipio;

    public Municipio() {}

    public Municipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public Municipio(int idMunicipio, String nombreMunicipio) {
        this.idMunicipio = idMunicipio;
        this.nombreMunicipio = nombreMunicipio;
    }

    public int getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(int idMunicipio) { this.idMunicipio = idMunicipio; }

    public String getNombreMunicipio() { return nombreMunicipio; }
    public void setNombreMunicipio(String nombreMunicipio) { this.nombreMunicipio = nombreMunicipio; }

    @Override
    public String toString() {
        return "[" + idMunicipio + "] " + nombreMunicipio;
    }
}
