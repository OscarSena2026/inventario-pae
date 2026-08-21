package com.pae.dao;

import com.pae.modelo.Sede;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SedeDAO {

    public void insertar(Sede s) throws SQLException {
        String sql = "INSERT INTO sede (nombre_sede, direccion, id_municipio) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, s.getNombreSede());
            stmt.setString(2, s.getDireccion());
            stmt.setInt(3, s.getIdMunicipio());
            stmt.executeUpdate();
        }
    }

    public List<Sede> consultarTodos() throws SQLException {
        List<Sede> lista = new ArrayList<>();
        String sql = "SELECT * FROM sede ORDER BY id_sede";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Sede buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM sede WHERE id_sede = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void actualizar(Sede s) throws SQLException {
        String sql = "UPDATE sede SET nombre_sede = ?, direccion = ?, id_municipio = ? WHERE id_sede = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, s.getNombreSede());
            stmt.setString(2, s.getDireccion());
            stmt.setInt(3, s.getIdMunicipio());
            stmt.setInt(4, s.getIdSede());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM sede WHERE id_sede = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Sede mapear(ResultSet rs) throws SQLException {
        return new Sede(rs.getInt("id_sede"), rs.getString("nombre_sede"), rs.getString("direccion"), rs.getInt("id_municipio"));
    }
}
