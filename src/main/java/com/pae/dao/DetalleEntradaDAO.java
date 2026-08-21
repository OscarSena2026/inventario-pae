package com.pae.dao;

import com.pae.modelo.DetalleEntrada;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleEntradaDAO {

    public void insertar(DetalleEntrada d) throws SQLException {
        String sql = "INSERT INTO detalle_entrada (id_entrada, id_lote, cantidad, valor_unitario) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, d.getIdEntrada());
            stmt.setInt(2, d.getIdLote());
            stmt.setDouble(3, d.getCantidad());
            stmt.setDouble(4, d.getValorUnitario());
            stmt.executeUpdate();
        }
    }

    public List<DetalleEntrada> consultarPorEntrada(int idEntrada) throws SQLException {
        List<DetalleEntrada> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_entrada WHERE id_entrada = ? ORDER BY id_detalle_entrada";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idEntrada);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<DetalleEntrada> consultarTodos() throws SQLException {
        List<DetalleEntrada> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_entrada ORDER BY id_detalle_entrada";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public DetalleEntrada buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM detalle_entrada WHERE id_detalle_entrada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(DetalleEntrada d) throws SQLException {
        String sql = "UPDATE detalle_entrada SET id_entrada = ?, id_lote = ?, cantidad = ?, valor_unitario = ? WHERE id_detalle_entrada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, d.getIdEntrada());
            stmt.setInt(2, d.getIdLote());
            stmt.setDouble(3, d.getCantidad());
            stmt.setDouble(4, d.getValorUnitario());
            stmt.setInt(5, d.getIdDetalleEntrada());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM detalle_entrada WHERE id_detalle_entrada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private DetalleEntrada mapear(ResultSet rs) throws SQLException {
        return new DetalleEntrada(
                rs.getInt("id_detalle_entrada"),
                rs.getInt("id_entrada"),
                rs.getInt("id_lote"),
                rs.getDouble("cantidad"),
                rs.getDouble("valor_unitario")
        );
    }
}
