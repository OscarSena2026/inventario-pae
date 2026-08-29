package com.pae.web;

import com.pae.dao.ProveedorDAO;
import com.pae.modelo.Proveedor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet del modulo Proveedor.
 * GA7-220501096-AA2-EV02 - segundo ejemplo de GET (listar/formulario) y POST (guardar).
 */
@WebServlet("/proveedores")
public class ProveedorServlet extends HttpServlet {

    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        try {
            switch (accion) {
                case "nuevo" -> {
                    RequestDispatcher rd = req.getRequestDispatcher("/proveedores/formulario.jsp");
                    rd.forward(req, resp);
                }
                case "eliminar" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    proveedorDAO.eliminar(id);
                    resp.sendRedirect(req.getContextPath() + "/proveedores");
                }
                default -> {
                    List<Proveedor> lista = proveedorDAO.consultarTodos();
                    req.setAttribute("proveedores", lista);
                    RequestDispatcher rd = req.getRequestDispatcher("/proveedores/lista.jsp");
                    rd.forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Error de base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nombre = req.getParameter("nombreProveedor");
        String nit = req.getParameter("nit");
        String telefono = req.getParameter("telefono");
        String direccion = req.getParameter("direccion");

        try {
            proveedorDAO.insertar(new Proveedor(nombre, nit, telefono, direccion));
            resp.sendRedirect(req.getContextPath() + "/proveedores");
        } catch (SQLException e) {
            throw new ServletException("Error de base de datos: " + e.getMessage(), e);
        }
    }
}
