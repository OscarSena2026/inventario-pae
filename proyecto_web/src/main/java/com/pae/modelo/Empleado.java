package com.pae.modelo;

public class Empleado {
    private int idEmpleado;
    private String nombres;
    private String cargo;
    private String telefono;

    public Empleado() {}

    public Empleado(String nombres, String cargo, String telefono) {
        this.nombres = nombres;
        this.cargo = cargo;
        this.telefono = telefono;
    }

    public Empleado(int idEmpleado, String nombres, String cargo, String telefono) {
        this.idEmpleado = idEmpleado;
        this.nombres = nombres;
        this.cargo = cargo;
        this.telefono = telefono;
    }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "[" + idEmpleado + "] " + nombres + " (" + cargo + ")";
    }
}
