package com.pae.dao;

import com.pae.modelo.Municipio;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {

    public void insertar(Municipio m) throws SQLException {
        String sql = "INSERT INTO municipio (nombre_municipio) VALUES (?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, m.getNombreMunicipio());
            stmt.executeUpdate();
        }
    }

    public List<Municipio> consultarTodos() throws SQLException {
        List<Municipio> lista = new ArrayList<>();
        String sql = "SELECT id_municipio, nombre_municipio FROM municipio ORDER BY id_municipio";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Municipio(rs.getInt("id_municipio"), rs.getString("nombre_municipio")));
            }
        }
        return lista;
    }

    public Municipio buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_municipio, nombre_municipio FROM municipio WHERE id_municipio = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Municipio(rs.getInt("id_municipio"), rs.getString("nombre_municipio"));
                }
            }
        }
        return null;
    }

    public void actualizar(Municipio m) throws SQLException {
        String sql = "UPDATE municipio SET nombre_municipio = ? WHERE id_municipio = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, m.getNombreMunicipio());
            stmt.setInt(2, m.getIdMunicipio());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM municipio WHERE id_municipio = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
