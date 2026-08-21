package com.pae.dao;

import com.pae.modelo.Empleado;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public void insertar(Empleado e) throws SQLException {
        String sql = "INSERT INTO empleado (nombres, cargo, telefono) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, e.getNombres());
            stmt.setString(2, e.getCargo());
            stmt.setString(3, e.getTelefono());
            stmt.executeUpdate();
        }
    }

    public List<Empleado> consultarTodos() throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleado ORDER BY id_empleado";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Empleado buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE id_empleado = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Empleado e) throws SQLException {
        String sql = "UPDATE empleado SET nombres = ?, cargo = ?, telefono = ? WHERE id_empleado = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, e.getNombres());
            stmt.setString(2, e.getCargo());
            stmt.setString(3, e.getTelefono());
            stmt.setInt(4, e.getIdEmpleado());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM empleado WHERE id_empleado = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Empleado mapear(ResultSet rs) throws SQLException {
        return new Empleado(rs.getInt("id_empleado"), rs.getString("nombres"), rs.getString("cargo"), rs.getString("telefono"));
    }
}
