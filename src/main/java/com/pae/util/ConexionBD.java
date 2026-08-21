package com.pae.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria encargada de abrir la conexion JDBC hacia MySQL.
 * Ajusta URL, USUARIO y CLAVE segun tu entorno local antes de ejecutar el proyecto.
 */
public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/inventario_pae?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "root";

    private ConexionBD() {
        // Clase utilitaria: no se instancia
    }

    /**
     * Abre y retorna una nueva conexion a la base de datos.
     * Cada DAO obtiene su propia conexion y la cierra con try-with-resources.
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
    }
}
