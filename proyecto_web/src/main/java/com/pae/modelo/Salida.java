package com.pae.modelo;

import java.time.LocalDate;

public class Salida {
    private int idSalida;
    private LocalDate fechaSalida;
    private String motivo;
    private int idBodega;
    private int idSede;
    private int idEmpleado;

    public Salida() {}

    public Salida(LocalDate fechaSalida, String motivo, int idBodega, int idSede, int idEmpleado) {
        this.fechaSalida = fechaSalida;
        this.motivo = motivo;
        this.idBodega = idBodega;
        this.idSede = idSede;
        this.idEmpleado = idEmpleado;
    }

    public Salida(int idSalida, LocalDate fechaSalida, String motivo, int idBodega, int idSede, int idEmpleado) {
        this.idSalida = idSalida;
        this.fechaSalida = fechaSalida;
        this.motivo = motivo;
        this.idBodega = idBodega;
        this.idSede = idSede;
        this.idEmpleado = idEmpleado;
    }

    public int getIdSalida() { return idSalida; }
    public void setIdSalida(int idSalida) { this.idSalida = idSalida; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public int getIdBodega() { return idBodega; }
    public void setIdBodega(int idBodega) { this.idBodega = idBodega; }

    public int getIdSede() { return idSede; }
    public void setIdSede(int idSede) { this.idSede = idSede; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    @Override
    public String toString() {
        return "[" + idSalida + "] " + fechaSalida + " - " + motivo;
    }
}
