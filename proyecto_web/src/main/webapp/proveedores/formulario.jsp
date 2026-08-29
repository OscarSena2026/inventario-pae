<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Proveedor - Inventario PAE</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 500px; margin: 40px auto; color: #333; }
        h1 { color: #1F4E78; }
        label { display: block; margin-top: 12px; font-weight: bold; }
        input { width: 100%; padding: 8px; margin-top: 4px; box-sizing: border-box; }
        button { margin-top: 18px; padding: 10px 18px; background: #2E7D32; color: white;
                 border: none; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/proveedores">&larr; Volver al listado</a></p>
    <h1>Nuevo proveedor</h1>

    <form action="${pageContext.request.contextPath}/proveedores" method="post">
        <label for="nombreProveedor">Nombre del proveedor</label>
        <input type="text" id="nombreProveedor" name="nombreProveedor" required>

        <label for="nit">NIT</label>
        <input type="text" id="nit" name="nit" required>

        <label for="telefono">Telefono</label>
        <input type="text" id="telefono" name="telefono">

        <label for="direccion">Direccion</label>
        <input type="text" id="direccion" name="direccion">

        <button type="submit">Guardar</button>
    </form>
</body>
</html>
