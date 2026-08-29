<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Proveedores - Inventario PAE</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 800px; margin: 40px auto; color: #333; }
        h1 { color: #1F4E78; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background: #f5f7fa; }
        a { color: #1F4E78; margin-right: 8px; }
        a.eliminar { color: #B00020; }
        .nuevo { display: inline-block; margin-top: 12px; padding: 10px 18px;
                 background: #2E7D32; color: white; text-decoration: none; border-radius: 4px; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/index.jsp">&larr; Menu principal</a></p>
    <h1>Proveedores</h1>

    <table>
        <tr>
            <th>ID</th><th>Nombre</th><th>NIT</th><th>Telefono</th><th>Direccion</th><th>Acciones</th>
        </tr>
        <c:forEach var="pr" items="${proveedores}">
            <tr>
                <td>${pr.idProveedor}</td>
                <td>${pr.nombreProveedor}</td>
                <td>${pr.nit}</td>
                <td>${pr.telefono}</td>
                <td>${pr.direccion}</td>
                <td>
                    <a class="eliminar" href="${pageContext.request.contextPath}/proveedores?accion=eliminar&id=${pr.idProveedor}"
                       onclick="return confirm('¿Eliminar este proveedor?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <a class="nuevo" href="${pageContext.request.contextPath}/proveedores?accion=nuevo">+ Nuevo proveedor</a>
</body>
</html>
