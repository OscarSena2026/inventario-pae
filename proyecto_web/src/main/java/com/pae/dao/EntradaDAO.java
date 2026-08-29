package com.pae.dao;

import com.pae.modelo.Entrada;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntradaDAO {

    public void insertar(Entrada e) throws SQLException {
        String sql = "INSERT INTO entrada (fecha_entrada, numero_factura, id_bodega, id_proveedor, id_empleado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(e.getFechaEntrada()));
            stmt.setString(2, e.getNumeroFactura());
            stmt.setInt(3, e.getIdBodega());
            stmt.setInt(4, e.getIdProveedor());
            stmt.setInt(5, e.getIdEmpleado());
            stmt.executeUpdate();
        }
    }

    public List<Entrada> consultarTodos() throws SQLException {
        List<Entrada> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrada ORDER BY id_entrada";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Entrada buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM entrada WHERE id_entrada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Entrada e) throws SQLException {
        String sql = "UPDATE entrada SET fecha_entrada = ?, numero_factura = ?, id_bodega = ?, id_proveedor = ?, id_empleado = ? WHERE id_entrada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(e.getFechaEntrada()));
            stmt.setString(2, e.getNumeroFactura());
            stmt.setInt(3, e.getIdBodega());
            stmt.setInt(4, e.getIdProveedor());
            stmt.setInt(5, e.getIdEmpleado());
            stmt.setInt(6, e.getIdEntrada());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM entrada WHERE id_entrada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /** Devuelve el id autogenerado; util para insertar el detalle en la misma operacion. */
    public int insertarYObtenerId(Entrada e) throws SQLException {
        String sql = "INSERT INTO entrada (fecha_entrada, numero_factura, id_bodega, id_proveedor, id_empleado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(e.getFechaEntrada()));
            stmt.setString(2, e.getNumeroFactura());
            stmt.setInt(3, e.getIdBodega());
            stmt.setInt(4, e.getIdProveedor());
            stmt.setInt(5, e.getIdEmpleado());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    private Entrada mapear(ResultSet rs) throws SQLException {
        return new Entrada(
                rs.getInt("id_entrada"),
                rs.getDate("fecha_entrada").toLocalDate(),
                rs.getString("numero_factura"),
                rs.getInt("id_bodega"),
                rs.getInt("id_proveedor"),
                rs.getInt("id_empleado")
        );
    }
}
