package com.pae.dao;

import com.pae.modelo.Existencia;
import com.pae.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExistenciaDAO {

    public void insertar(Existencia ex) throws SQLException {
        String sql = "INSERT INTO existencia (cantidad_disponible, id_bodega, id_lote) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, ex.getCantidadDisponible());
            stmt.setInt(2, ex.getIdBodega());
            stmt.setInt(3, ex.getIdLote());
            stmt.executeUpdate();
        }
    }

    public List<Existencia> consultarTodos() throws SQLException {
        List<Existencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM existencia ORDER BY id_existencia";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Existencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM existencia WHERE id_existencia = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    /** Suma o resta cantidad_disponible sobre una fila existente (usado al registrar entradas/salidas). */
    public void ajustarCantidad(int idBodega, int idLote, double delta) throws SQLException {
        String sqlBuscar = "SELECT id_existencia, cantidad_disponible FROM existencia WHERE id_bodega = ? AND id_lote = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement buscar = con.prepareStatement(sqlBuscar)) {
            buscar.setInt(1, idBodega);
            buscar.setInt(2, idLote);
            try (ResultSet rs = buscar.executeQuery()) {
                if (rs.next()) {
                    double nuevaCantidad = rs.getDouble("cantidad_disponible") + delta;
                    String sqlUpdate = "UPDATE existencia SET cantidad_disponible = ? WHERE id_existencia = ?";
                    try (PreparedStatement act = con.prepareStatement(sqlUpdate)) {
                        act.setDouble(1, nuevaCantidad);
                        act.setInt(2, rs.getInt("id_existencia"));
                        act.executeUpdate();
                    }
                } else {
                    String sqlInsert = "INSERT INTO existencia (cantidad_disponible, id_bodega, id_lote) VALUES (?, ?, ?)";
                    try (PreparedStatement ins = con.prepareStatement(sqlInsert)) {
                        ins.setDouble(1, delta);
                        ins.setInt(2, idBodega);
                        ins.setInt(3, idLote);
                        ins.executeUpdate();
                    }
                }
            }
        }
    }

    public void actualizar(Existencia ex) throws SQLException {
        String sql = "UPDATE existencia SET cantidad_disponible = ?, id_bodega = ?, id_lote = ? WHERE id_existencia = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, ex.getCantidadDisponible());
            stmt.setInt(2, ex.getIdBodega());
            stmt.setInt(3, ex.getIdLote());
            stmt.setInt(4, ex.getIdExistencia());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM existencia WHERE id_existencia = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Existencia mapear(ResultSet rs) throws SQLException {
        return new Existencia(
                rs.getInt("id_existencia"),
                rs.getDouble("cantidad_disponible"),
                rs.getInt("id_bodega"),
                rs.getInt("id_lote")
        );
    }
}
