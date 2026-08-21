package com.pae.dao;

import com.pae.modelo.Lote;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    public void insertar(Lote l) throws SQLException {
        String sql = "INSERT INTO lote (fecha_fabricacion, fecha_vencimiento, cantidad_recibida, id_producto, id_proveedor) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, l.getFechaFabricacion() != null ? Date.valueOf(l.getFechaFabricacion()) : null);
            stmt.setDate(2, Date.valueOf(l.getFechaVencimiento()));
            stmt.setDouble(3, l.getCantidadRecibida());
            stmt.setInt(4, l.getIdProducto());
            stmt.setInt(5, l.getIdProveedor());
            stmt.executeUpdate();
        }
    }

    public List<Lote> consultarTodos() throws SQLException {
        List<Lote> lista = new ArrayList<>();
        String sql = "SELECT * FROM lote ORDER BY id_lote";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Lote buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM lote WHERE id_lote = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Lote l) throws SQLException {
        String sql = "UPDATE lote SET fecha_fabricacion = ?, fecha_vencimiento = ?, cantidad_recibida = ?, id_producto = ?, id_proveedor = ? WHERE id_lote = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, l.getFechaFabricacion() != null ? Date.valueOf(l.getFechaFabricacion()) : null);
            stmt.setDate(2, Date.valueOf(l.getFechaVencimiento()));
            stmt.setDouble(3, l.getCantidadRecibida());
            stmt.setInt(4, l.getIdProducto());
            stmt.setInt(5, l.getIdProveedor());
            stmt.setInt(6, l.getIdLote());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM lote WHERE id_lote = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Lote mapear(ResultSet rs) throws SQLException {
        Date fabricacion = rs.getDate("fecha_fabricacion");
        return new Lote(
                rs.getInt("id_lote"),
                fabricacion != null ? fabricacion.toLocalDate() : null,
                rs.getDate("fecha_vencimiento").toLocalDate(),
                rs.getDouble("cantidad_recibida"),
                rs.getInt("id_producto"),
                rs.getInt("id_proveedor")
        );
    }
}
