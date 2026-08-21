package com.pae.modelo;

public class Proveedor {
    private int idProveedor;
    private String nombreProveedor;
    private String nit;
    private String telefono;
    private String direccion;

    public Proveedor() {}

    public Proveedor(String nombreProveedor, String nit, String telefono, String direccion) {
        this.nombreProveedor = nombreProveedor;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Proveedor(int idProveedor, String nombreProveedor, String nit, String telefono, String direccion) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return "[" + idProveedor + "] " + nombreProveedor + " - NIT " + nit;
    }
}
