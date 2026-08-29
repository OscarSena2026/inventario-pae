package com.pae.dao;

import com.pae.modelo.Bodega;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BodegaDAO {

    public void insertar(Bodega b) throws SQLException {
        String sql = "INSERT INTO bodega (nombre_bodega, direccion, id_municipio) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, b.getNombreBodega());
            stmt.setString(2, b.getDireccion());
            stmt.setInt(3, b.getIdMunicipio());
            stmt.executeUpdate();
        }
    }

    public List<Bodega> consultarTodos() throws SQLException {
        List<Bodega> lista = new ArrayList<>();
        String sql = "SELECT * FROM bodega ORDER BY id_bodega";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Bodega buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM bodega WHERE id_bodega = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Bodega b) throws SQLException {
        String sql = "UPDATE bodega SET nombre_bodega = ?, direccion = ?, id_municipio = ? WHERE id_bodega = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, b.getNombreBodega());
            stmt.setString(2, b.getDireccion());
            stmt.setInt(3, b.getIdMunicipio());
            stmt.setInt(4, b.getIdBodega());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM bodega WHERE id_bodega = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Bodega mapear(ResultSet rs) throws SQLException {
        return new Bodega(rs.getInt("id_bodega"), rs.getString("nombre_bodega"), rs.getString("direccion"), rs.getInt("id_municipio"));
    }
}
