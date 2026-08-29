package com.pae.web;

import com.pae.dao.ProductoDAO;
import com.pae.modelo.Producto;

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
 * Servlet del modulo Producto.
 * GA7-220501096-AA2-EV02 - GET muestra (listar/formulario), POST guarda (insertar/actualizar).
 */
@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();

    // GET -> decide que vista mostrar segun el parametro "accion"
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        try {
            switch (accion) {
                case "nuevo" -> {
                    // Formulario vacio para insertar
                    req.setAttribute("producto", null);
                    RequestDispatcher rd = req.getRequestDispatcher("/productos/formulario.jsp");
                    rd.forward(req, resp);
                }
                case "editar" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Producto p = productoDAO.buscarPorId(id);
                    req.setAttribute("producto", p);
                    RequestDispatcher rd = req.getRequestDispatcher("/productos/formulario.jsp");
                    rd.forward(req, resp);
                }
                case "eliminar" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    productoDAO.eliminar(id);
                    resp.sendRedirect(req.getContextPath() + "/productos");
                }
                default -> {
                    // listar
                    List<Producto> lista = productoDAO.consultarTodos();
                    req.setAttribute("productos", lista);
                    RequestDispatcher rd = req.getRequestDispatcher("/productos/lista.jsp");
                    rd.forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Error de base de datos: " + e.getMessage(), e);
        }
    }

    // POST -> recibe los datos del formulario y los guarda (inserta o actualiza)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("idProducto");
        String nombre = req.getParameter("nombreProducto");
        String categoria = req.getParameter("categoria");
        String unidad = req.getParameter("unidadMedida");
        String perecedero = req.getParameter("perecedero");

        try {
            if (idParam == null || idParam.isBlank()) {
                // Insertar
                Producto nuevo = new Producto(nombre, categoria, unidad, perecedero);
                productoDAO.insertar(nuevo);
            } else {
                // Actualizar
                Producto existente = new Producto(Integer.parseInt(idParam), nombre, categoria, unidad, perecedero);
                productoDAO.actualizar(existente);
            }
            // Patron Post-Redirect-Get: evita reenviar el formulario al refrescar
            resp.sendRedirect(req.getContextPath() + "/productos");
        } catch (SQLException e) {
            throw new ServletException("Error de base de datos: " + e.getMessage(), e);
        }
    }
}
