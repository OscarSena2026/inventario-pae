package com.pae.dao;

import com.pae.modelo.Producto;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (nombre_producto, categoria, unidad_medida, perecedero) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombreProducto());
            stmt.setString(2, p.getCategoria());
            stmt.setString(3, p.getUnidadMedida());
            stmt.setString(4, p.getPerecedero());
            stmt.executeUpdate();
        }
    }

    public List<Producto> consultarTodos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto ORDER BY id_producto";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Producto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Producto p) throws SQLException {
        String sql = "UPDATE producto SET nombre_producto = ?, categoria = ?, unidad_medida = ?, perecedero = ? WHERE id_producto = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombreProducto());
            stmt.setString(2, p.getCategoria());
            stmt.setString(3, p.getUnidadMedida());
            stmt.setString(4, p.getPerecedero());
            stmt.setInt(5, p.getIdProducto());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre_producto"),
                rs.getString("categoria"),
                rs.getString("unidad_medida"),
                rs.getString("perecedero")
        );
    }
}
