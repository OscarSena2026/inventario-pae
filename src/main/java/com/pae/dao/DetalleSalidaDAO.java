package com.pae.dao;

import com.pae.modelo.DetalleSalida;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleSalidaDAO {

    public void insertar(DetalleSalida d) throws SQLException {
        String sql = "INSERT INTO detalle_salida (id_salida, id_lote, cantidad) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, d.getIdSalida());
            stmt.setInt(2, d.getIdLote());
            stmt.setDouble(3, d.getCantidad());
            stmt.executeUpdate();
        }
    }

    public List<DetalleSalida> consultarPorSalida(int idSalida) throws SQLException {
        List<DetalleSalida> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_salida WHERE id_salida = ? ORDER BY id_detalle_salida";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idSalida);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<DetalleSalida> consultarTodos() throws SQLException {
        List<DetalleSalida> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_salida ORDER BY id_detalle_salida";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public DetalleSalida buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM detalle_salida WHERE id_detalle_salida = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(DetalleSalida d) throws SQLException {
        String sql = "UPDATE detalle_salida SET id_salida = ?, id_lote = ?, cantidad = ? WHERE id_detalle_salida = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, d.getIdSalida());
            stmt.setInt(2, d.getIdLote());
            stmt.setDouble(3, d.getCantidad());
            stmt.setInt(4, d.getIdDetalleSalida());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM detalle_salida WHERE id_detalle_salida = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private DetalleSalida mapear(ResultSet rs) throws SQLException {
        return new DetalleSalida(
                rs.getInt("id_detalle_salida"),
                rs.getInt("id_salida"),
                rs.getInt("id_lote"),
                rs.getDouble("cantidad")
        );
    }
}
