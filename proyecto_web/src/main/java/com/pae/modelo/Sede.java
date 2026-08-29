package com.pae.modelo;

public class Sede {
    private int idSede;
    private String nombreSede;
    private String direccion;
    private int idMunicipio;

    public Sede() {}

    public Sede(String nombreSede, String direccion, int idMunicipio) {
        this.nombreSede = nombreSede;
        this.direccion = direccion;
        this.idMunicipio = idMunicipio;
    }

    public Sede(int idSede, String nombreSede, String direccion, int idMunicipio) {
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.direccion = direccion;
        this.idMunicipio = idMunicipio;
    }

    public int getIdSede() { return idSede; }
    public void setIdSede(int idSede) { this.idSede = idSede; }

    public String getNombreSede() { return nombreSede; }
    public void setNombreSede(String nombreSede) { this.nombreSede = nombreSede; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(int idMunicipio) { this.idMunicipio = idMunicipio; }

    @Override
    public String toString() {
        return "[" + idSede + "] " + nombreSede;
    }
}
