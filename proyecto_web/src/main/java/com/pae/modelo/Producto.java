package com.pae.modelo;

public class Producto {
    private int idProducto;
    private String nombreProducto;
    private String categoria;
    private String unidadMedida;
    private String perecedero; // "SI" o "NO"

    public Producto() {}

    public Producto(String nombreProducto, String categoria, String unidadMedida, String perecedero) {
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.perecedero = perecedero;
    }

    public Producto(int idProducto, String nombreProducto, String categoria, String unidadMedida, String perecedero) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.perecedero = perecedero;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public String getPerecedero() { return perecedero; }
    public void setPerecedero(String perecedero) { this.perecedero = perecedero; }

    @Override
    public String toString() {
        return "[" + idProducto + "] " + nombreProducto + " (" + categoria + ", " + unidadMedida + ", perecedero=" + perecedero + ")";
    }
}
