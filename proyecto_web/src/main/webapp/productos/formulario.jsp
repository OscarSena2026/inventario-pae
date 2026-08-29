<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario Producto - Inventario PAE</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 500px; margin: 40px auto; color: #333; }
        h1 { color: #1F4E78; }
        label { display: block; margin-top: 12px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; margin-top: 4px; box-sizing: border-box; }
        button { margin-top: 18px; padding: 10px 18px; background: #2E7D32; color: white;
                 border: none; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/productos">&larr; Volver al listado</a></p>
    <h1>${producto == null ? "Nuevo producto" : "Editar producto"}</h1>

    <form action="${pageContext.request.contextPath}/productos" method="post">
        <c:if test="${producto != null}">
        </c:if>
        <input type="hidden" name="idProducto" value="${producto.idProducto}">

        <label for="nombreProducto">Nombre del producto</label>
        <input type="text" id="nombreProducto" name="nombreProducto"
               value="${producto.nombreProducto}" required>

        <label for="categoria">Categoria</label>
        <select id="categoria" name="categoria">
            <option value="PROTEINA" ${producto.categoria == 'PROTEINA' ? 'selected' : ''}>PROTEINA</option>
            <option value="CEREAL" ${producto.categoria == 'CEREAL' ? 'selected' : ''}>CEREAL</option>
            <option value="FRUTA" ${producto.categoria == 'FRUTA' ? 'selected' : ''}>FRUTA</option>
            <option value="LACTEO" ${producto.categoria == 'LACTEO' ? 'selected' : ''}>LACTEO</option>
            <option value="VERDURA" ${producto.categoria == 'VERDURA' ? 'selected' : ''}>VERDURA</option>
            <option value="INSUMO" ${producto.categoria == 'INSUMO' ? 'selected' : ''}>INSUMO</option>
        </select>

        <label for="unidadMedida">Unidad de medida</label>
        <input type="text" id="unidadMedida" name="unidadMedida"
               value="${producto.unidadMedida}" placeholder="kg, lt, unidad..." required>

        <label for="perecedero">Perecedero</label>
        <select id="perecedero" name="perecedero">
            <option value="SI" ${producto.perecedero == 'SI' ? 'selected' : ''}>SI</option>
            <option value="NO" ${producto.perecedero == 'NO' ? 'selected' : ''}>NO</option>
        </select>

        <button type="submit">Guardar</button>
    </form>
</body>
</html>
