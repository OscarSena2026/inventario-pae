<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Inventario PAE</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 700px; margin: 40px auto; color: #333; }
        h1 { color: #1F4E78; }
        a.boton {
            display: inline-block; margin: 8px 8px 0 0; padding: 10px 18px;
            background: #2E7D32; color: white; text-decoration: none; border-radius: 4px;
        }
        a.boton:hover { background: #1b5e20; }
    </style>
</head>
<body>
    <h1>Sistema de Inventario PAE</h1>
    <p>GA7-220501096-AA2-EV02 &mdash; Modulos web con Servlets y JSP</p>

    <a class="boton" href="${pageContext.request.contextPath}/productos">Gestionar Productos</a>
    <a class="boton" href="${pageContext.request.contextPath}/proveedores">Gestionar Proveedores</a>
</body>
</html>
