package com.pae;

import com.pae.dao.*;
import com.pae.modelo.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Menu de consola para probar el CRUD de cada entidad del modulo de inventario.
 * GA7-220501096-AA2-EV01 - Codificacion de modulos del software (Java + JDBC)
 */
public class
Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private static final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private static final MunicipioDAO municipioDAO = new MunicipioDAO();
    private static final BodegaDAO bodegaDAO = new BodegaDAO();
    private static final SedeDAO sedeDAO = new SedeDAO();
    private static final LoteDAO loteDAO = new LoteDAO();
    private static final EntradaDAO entradaDAO = new EntradaDAO();
    private static final DetalleEntradaDAO detalleEntradaDAO = new DetalleEntradaDAO();
    private static final SalidaDAO salidaDAO = new SalidaDAO();
    private static final DetalleSalidaDAO detalleSalidaDAO = new DetalleSalidaDAO();
    private static final ExistenciaDAO existenciaDAO = new ExistenciaDAO();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1 -> menuProductos();
                    case 2 -> menuProveedores();
                    case 3 -> registrarEntradaCompleta();
                    case 4 -> registrarSalidaCompleta();
                    case 5 -> consultarExistencias();
                    case 6 -> menuMunicipios();
                    case 7 -> menuBodegas();
                    case 8 -> menuSedes();
                    case 9 -> menuEmpleados();
                    case 10 -> menuLotes();
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE INVENTARIO PAE (Java + JDBC + MySQL) ===");
        System.out.println("1. Gestionar productos (CRUD)");
        System.out.println("2. Gestionar proveedores (CRUD)");
        System.out.println("3. Registrar entrada de inventario (con detalle y existencia)");
        System.out.println("4. Registrar salida de inventario (con detalle y existencia)");
        System.out.println("5. Consultar existencias actuales");
        System.out.println("6. Gestionar municipios (CRUD)");
        System.out.println("7. Gestionar bodegas (CRUD)");
        System.out.println("8. Gestionar sedes / instituciones educativas (CRUD)");
        System.out.println("9. Gestionar empleados (CRUD)");
        System.out.println("10. Gestionar lotes (CRUD)");
        System.out.println("0. Salir");
    }

    // ---------- Modulo Productos (CRUD completo de demostracion) ----------
    private static void menuProductos() throws SQLException {
        System.out.println("\n-- Productos --");
        System.out.println("1.Insertar 2.Consultar todos 3.Actualizar 4.Eliminar");
        int op = leerEntero("Opcion: ");
        switch (op) {
            case 1 -> {
                String nombre = leerTexto("Nombre del producto: ");
                String categoria = leerTexto("Categoria (PROTEINA/CEREAL/FRUTA/LACTEO/VERDURA/INSUMO): ");
                String unidad = leerTexto("Unidad de medida (kg, lt, unidad...): ");
                String perecedero = leerTexto("Perecedero (SI/NO): ");
                productoDAO.insertar(new Producto(nombre, categoria, unidad, perecedero));
                System.out.println("Producto insertado.");
            }
            case 2 -> {
                List<Producto> lista = productoDAO.consultarTodos();
                lista.forEach(System.out::println);
            }
            case 3 -> {
                int id = leerEntero("ID del producto a actualizar: ");
                Producto p = productoDAO.buscarPorId(id);
                if (p == null) { System.out.println("No existe ese producto."); return; }
                p.setNombreProducto(leerTexto("Nuevo nombre (" + p.getNombreProducto() + "): "));
                p.setCategoria(leerTexto("Nueva categoria (" + p.getCategoria() + "): "));
                p.setUnidadMedida(leerTexto("Nueva unidad (" + p.getUnidadMedida() + "): "));
                p.setPerecedero(leerTexto("Perecedero SI/NO (" + p.getPerecedero() + "): "));
                productoDAO.actualizar(p);
                System.out.println("Producto actualizado.");
            }
            case 4 -> {
                int id = leerEntero("ID del producto a eliminar: ");
                productoDAO.eliminar(id);
                System.out.println("Producto eliminado.");
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    // ---------- Modulo Proveedores (CRUD completo de demostracion) ----------
    private static void menuProveedores() throws SQLException {
        System.out.println("\n-- Proveedores --");
        System.out.println("1.Insertar 2.Consultar todos 3.Actualizar 4.Eliminar");
        int op = leerEntero("Opcion: ");
        switch (op) {
            case 1 -> {
                String nombre = leerTexto("Nombre del proveedor: ");
                String nit = leerTexto("NIT: ");
                String tel = leerTexto("Telefono: ");
                String dir = leerTexto("Direccion: ");
                proveedorDAO.insertar(new Proveedor(nombre, nit, tel, dir));
                System.out.println("Proveedor insertado.");
            }
            case 2 -> proveedorDAO.consultarTodos().forEach(System.out::println);
            case 3 -> {
                int id = leerEntero("ID del proveedor a actualizar: ");
                Proveedor p = proveedorDAO.buscarPorId(id);
                if (p == null) { System.out.println("No existe ese proveedor."); return; }
                p.setNombreProveedor(leerTexto("Nuevo nombre (" + p.getNombreProveedor() + "): "));
                p.setTelefono(leerTexto("Nuevo telefono (" + p.getTelefono() + "): "));
                p.setDireccion(leerTexto("Nueva direccion (" + p.getDireccion() + "): "));
                proveedorDAO.actualizar(p);
                System.out.println("Proveedor actualizado.");
            }
            case 4 -> {
                int id = leerEntero("ID del proveedor a eliminar: ");
                proveedorDAO.eliminar(id);
                System.out.println("Proveedor eliminado.");
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    // ---------- Modulo Municipios (CRUD completo) ----------
    private static void menuMunicipios() throws SQLException {
        System.out.println("\n-- Municipios --");
        System.out.println("1.Insertar 2.Consultar todos 3.Actualizar 4.Eliminar");
        int op = leerEntero("Opcion: ");
        switch (op) {
            case 1 -> {
                String nombre = leerTexto("Nombre del municipio: ");
                municipioDAO.insertar(new Municipio(nombre));
                System.out.println("Municipio insertado.");
            }
            case 2 -> municipioDAO.consultarTodos().forEach(System.out::println);
            case 3 -> {
                int id = leerEntero("ID del municipio a actualizar: ");
                Municipio m = municipioDAO.buscarPorId(id);
                if (m == null) { System.out.println("No existe ese municipio."); return; }
                m.setNombreMunicipio(leerTexto("Nuevo nombre (" + m.getNombreMunicipio() + "): "));
                municipioDAO.actualizar(m);
                System.out.println("Municipio actualizado.");
            }
            case 4 -> {
                int id = leerEntero("ID del municipio a eliminar: ");
                municipioDAO.eliminar(id);
                System.out.println("Municipio eliminado.");
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    // ---------- Modulo Bodegas (CRUD completo) ----------
    private static void menuBodegas() throws SQLException {
        System.out.println("\n-- Bodegas --");
        System.out.println("1.Insertar 2.Consultar todos 3.Actualizar 4.Eliminar");
        int op = leerEntero("Opcion: ");
        switch (op) {
            case 1 -> {
                String nombre = leerTexto("Nombre de la bodega: ");
                String dir = leerTexto("Direccion: ");
                int idMunicipio = leerEntero("ID municipio (debe existir): ");
                bodegaDAO.insertar(new Bodega(nombre, dir, idMunicipio));
                System.out.println("Bodega insertada.");
            }
            case 2 -> bodegaDAO.consultarTodos().forEach(System.out::println);
            case 3 -> {
                int id = leerEntero("ID de la bodega a actualizar: ");
                Bodega b = bodegaDAO.buscarPorId(id);
                if (b == null) { System.out.println("No existe esa bodega."); return; }
                b.setNombreBodega(leerTexto("Nuevo nombre (" + b.getNombreBodega() + "): "));
                b.setDireccion(leerTexto("Nueva direccion (" + b.getDireccion() + "): "));
                b.setIdMunicipio(leerEntero("ID municipio (" + b.getIdMunicipio() + "): "));
                bodegaDAO.actualizar(b);
                System.out.println("Bodega actualizada.");
            }
            case 4 -> {
                int id = leerEntero("ID de la bodega a eliminar: ");
                bodegaDAO.eliminar(id);
                System.out.println("Bodega eliminada.");
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    // ---------- Modulo Sedes / Instituciones educativas (CRUD completo) ----------
    private static void menuSedes() throws SQLException {
        System.out.println("\n-- Sedes --");
        System.out.println("1.Insertar 2.Consultar todos 3.Actualizar 4.Eliminar");
        int op = leerEntero("Opcion: ");
        switch (op) {
            case 1 -> {
                String nombre = leerTexto("Nombre de la sede/institucion: ");
                String dir = leerTexto("Direccion: ");
                int idMunicipio = leerEntero("ID municipio (debe existir): ");
                sedeDAO.insertar(new Sede(nombre, dir, idMunicipio));
                System.out.println("Sede insertada.");
            }
            case 2 -> sedeDAO.consultarTodos().forEach(System.out::println);
            case 3 -> {
                int id = leerEntero("ID de la sede a actualizar: ");
                Sede s = sedeDAO.buscarPorId(id);
                if (s == null) { System.out.println("No existe esa sede."); return; }
                s.setNombreSede(leerTexto("Nuevo nombre (" + s.getNombreSede() + "): "));
                s.setDireccion(leerTexto("Nueva direccion (" + s.getDireccion() + "): "));
                s.setIdMunicipio(leerEntero("ID municipio (" + s.getIdMunicipio() + "): "));
                sedeDAO.actualizar(s);
                System.out.println("Sede actualizada.");
            }
            case 4 -> {
                int id = leerEntero("ID de la sede a eliminar: ");
                sedeDAO.eliminar(id);
                System.out.println("Sede eliminada.");
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    // ---------- Modulo Empleados (CRUD completo) ----------
    private static void menuEmpleados() throws SQLException {
        System.out.println("\n-- Empleados --");
        System.out.println("1.Insertar 2.Consultar todos 3.Actualizar 4.Eliminar");
        int op = leerEntero("Opcion: ");
        switch (op) {
            case 1 -> {
                String nombres = leerTexto("Nombres del empleado: ");
                String cargo = leerTexto("Cargo: ");
                String tel = leerTexto("Telefono: ");
                empleadoDAO.insertar(new Empleado(nombres, cargo, tel));
                System.out.println("Empleado insertado.");
            }
            case 2 -> empleadoDAO.consultarTodos().forEach(System.out::println);
            case 3 -> {
                int id = leerEntero("ID del empleado a actualizar: ");
                Empleado e = empleadoDAO.buscarPorId(id);
                if (e == null) { System.out.println("No existe ese empleado."); return; }
                e.setNombres(leerTexto("Nuevos nombres (" + e.getNombres() + "): "));
                e.setCargo(leerTexto("Nuevo cargo (" + e.getCargo() + "): "));
                e.setTelefono(leerTexto("Nuevo telefono (" + e.getTelefono() + "): "));
                empleadoDAO.actualizar(e);
                System.out.println("Empleado actualizado.");
            }
            case 4 -> {
                int id = leerEntero("ID del empleado a eliminar: ");
                empleadoDAO.eliminar(id);
                System.out.println("Empleado eliminado.");
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    // ---------- Modulo Lotes (CRUD completo) ----------
    private static void menuLotes() throws SQLException {
        System.out.println("\n-- Lotes --");
        System.out.println("1.Insertar 2.Consultar todos 3.Actualizar 4.Eliminar");
        int op = leerEntero("Opcion: ");
        switch (op) {
            case 1 -> {
                int idProducto = leerEntero("ID producto (debe existir): ");
                int idProveedor = leerEntero("ID proveedor (debe existir): ");
                double cantidad = leerDecimal("Cantidad recibida: ");
                String fechaVenceStr = leerTexto("Fecha de vencimiento (AAAA-MM-DD): ");
                loteDAO.insertar(new Lote(LocalDate.now(), LocalDate.parse(fechaVenceStr), cantidad, idProducto, idProveedor));
                System.out.println("Lote insertado.");
            }
            case 2 -> loteDAO.consultarTodos().forEach(System.out::println);
            case 3 -> {
                int id = leerEntero("ID del lote a actualizar: ");
                Lote l = loteDAO.buscarPorId(id);
                if (l == null) { System.out.println("No existe ese lote."); return; }
                l.setCantidadRecibida(leerDecimal("Nueva cantidad (" + l.getCantidadRecibida() + "): "));
                String nuevaFecha = leerTexto("Nueva fecha vencimiento AAAA-MM-DD (" + l.getFechaVencimiento() + "): ");
                l.setFechaVencimiento(LocalDate.parse(nuevaFecha));
                loteDAO.actualizar(l);
                System.out.println("Lote actualizado.");
            }
            case 4 -> {
                int id = leerEntero("ID del lote a eliminar: ");
                loteDAO.eliminar(id);
                System.out.println("Lote eliminado.");
            }
            default -> System.out.println("Opcion no valida.");
        }
    }

    // ---------- Flujo transaccional: entrada -> detalle_entrada -> existencia ----------
    private static void registrarEntradaCompleta() throws SQLException {
        System.out.println("\n-- Registrar entrada de inventario --");
        int idBodega = leerEntero("ID bodega destino: ");
        int idProveedor = leerEntero("ID proveedor: ");
        int idEmpleado = leerEntero("ID empleado que recibe: ");
        String factura = leerTexto("Numero de factura: ");

        Entrada entrada = new Entrada(LocalDate.now(), factura, idBodega, idProveedor, idEmpleado);
        int idEntrada = entradaDAO.insertarYObtenerId(entrada);
        System.out.println("Entrada #" + idEntrada + " creada.");

        int idLote = leerEntero("ID de lote a ingresar (debe existir en la tabla lote): ");
        double cantidad = leerDecimal("Cantidad: ");
        double valorUnitario = leerDecimal("Valor unitario: ");

        detalleEntradaDAO.insertar(new DetalleEntrada(idEntrada, idLote, cantidad, valorUnitario));
        existenciaDAO.ajustarCantidad(idBodega, idLote, cantidad);
        System.out.println("Detalle de entrada registrado y existencia actualizada.");
    }

    // ---------- Flujo transaccional: salida -> detalle_salida -> existencia ----------
    private static void registrarSalidaCompleta() throws SQLException {
        System.out.println("\n-- Registrar salida de inventario --");
        int idBodega = leerEntero("ID bodega origen: ");
        int idSede = leerEntero("ID sede (institucion educativa) destino: ");
        int idEmpleado = leerEntero("ID empleado que despacha: ");
        String motivo = leerTexto("Motivo (ej: racion diaria): ");

        Salida salida = new Salida(LocalDate.now(), motivo, idBodega, idSede, idEmpleado);
        int idSalida = salidaDAO.insertarYObtenerId(salida);
        System.out.println("Salida #" + idSalida + " creada.");

        int idLote = leerEntero("ID de lote a despachar: ");
        double cantidad = leerDecimal("Cantidad: ");

        detalleSalidaDAO.insertar(new DetalleSalida(idSalida, idLote, cantidad));
        existenciaDAO.ajustarCantidad(idBodega, idLote, -cantidad);
        System.out.println("Detalle de salida registrado y existencia descontada.");
    }

    private static void consultarExistencias() throws SQLException {
        System.out.println("\n-- Existencias actuales --");
        existenciaDAO.consultarTodos().forEach(System.out::println);
    }

    // ---------- Utilidades de lectura de consola ----------
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    private static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Ingrese un numero valido: ");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }
}
