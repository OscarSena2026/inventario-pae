package com.pae.dao;

import com.pae.modelo.Proveedor;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public void insertar(Proveedor p) throws SQLException {
        String sql = "INSERT INTO proveedor (nombre_proveedor, nit, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombreProveedor());
            stmt.setString(2, p.getNit());
            stmt.setString(3, p.getTelefono());
            stmt.setString(4, p.getDireccion());
            stmt.executeUpdate();
        }
    }

    public List<Proveedor> consultarTodos() throws SQLException {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor ORDER BY id_proveedor";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Proveedor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM proveedor WHERE id_proveedor = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Proveedor p) throws SQLException {
        String sql = "UPDATE proveedor SET nombre_proveedor = ?, nit = ?, telefono = ?, direccion = ? WHERE id_proveedor = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombreProveedor());
            stmt.setString(2, p.getNit());
            stmt.setString(3, p.getTelefono());
            stmt.setString(4, p.getDireccion());
            stmt.setInt(5, p.getIdProveedor());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Proveedor mapear(ResultSet rs) throws SQLException {
        return new Proveedor(
                rs.getInt("id_proveedor"),
                rs.getString("nombre_proveedor"),
                rs.getString("nit"),
                rs.getString("telefono"),
                rs.getString("direccion")
        );
    }
}
