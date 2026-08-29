package com.pae.dao;

import com.pae.modelo.Salida;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalidaDAO {

    public void insertar(Salida s) throws SQLException {
        String sql = "INSERT INTO salida (fecha_salida, motivo, id_bodega, id_sede, id_empleado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(s.getFechaSalida()));
            stmt.setString(2, s.getMotivo());
            stmt.setInt(3, s.getIdBodega());
            stmt.setInt(4, s.getIdSede());
            stmt.setInt(5, s.getIdEmpleado());
            stmt.executeUpdate();
        }
    }

    public int insertarYObtenerId(Salida s) throws SQLException {
        String sql = "INSERT INTO salida (fecha_salida, motivo, id_bodega, id_sede, id_empleado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(s.getFechaSalida()));
            stmt.setString(2, s.getMotivo());
            stmt.setInt(3, s.getIdBodega());
            stmt.setInt(4, s.getIdSede());
            stmt.setInt(5, s.getIdEmpleado());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public List<Salida> consultarTodos() throws SQLException {
        List<Salida> lista = new ArrayList<>();
        String sql = "SELECT * FROM salida ORDER BY id_salida";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Salida buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM salida WHERE id_salida = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Salida s) throws SQLException {
        String sql = "UPDATE salida SET fecha_salida = ?, motivo = ?, id_bodega = ?, id_sede = ?, id_empleado = ? WHERE id_salida = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(s.getFechaSalida()));
            stmt.setString(2, s.getMotivo());
            stmt.setInt(3, s.getIdBodega());
            stmt.setInt(4, s.getIdSede());
            stmt.setInt(5, s.getIdEmpleado());
            stmt.setInt(6, s.getIdSalida());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM salida WHERE id_salida = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Salida mapear(ResultSet rs) throws SQLException {
        return new Salida(
                rs.getInt("id_salida"),
                rs.getDate("fecha_salida").toLocalDate(),
                rs.getString("motivo"),
                rs.getInt("id_bodega"),
                rs.getInt("id_sede"),
                rs.getInt("id_empleado")
        );
    }
}
