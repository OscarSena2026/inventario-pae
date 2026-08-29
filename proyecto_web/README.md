# Sistema de Inventario PAE — Módulo Web (GA7-220501096-AA2-EV02)

Aplicación web con Servlets y JSP, formularios HTML, métodos GET/POST, reutilizando
la capa `modelo`/`dao`/`util` construida en EV01 (conexión JDBC a MySQL con
PreparedStatement).

## Qué cumple de la lista de chequeo

1. **Formularios HTML con Servlets** → `productos/formulario.jsp` y
   `proveedores/formulario.jsp`, procesados por `ProductoServlet` y `ProveedorServlet`.
2. **Métodos GET y POST** → `doGet` muestra (listar / mostrar formulario),
   `doPost` guarda (inserta o actualiza) — exactamente el patrón del ejemplo de la guía.
3. **Elementos JSP** → uso de JSTL (`<c:forEach>`, `<c:if>`) y Expression Language
   (`${...}`) en las vistas, en vez de mezclar Java crudo en el HTML.
4. **Herramienta de versionamiento** → mismo repositorio Git de EV01
   (agrega este módulo como una carpeta nueva o un commit adicional).

## Requisitos

- **NetBeans** (con soporte Java Web — ya viene incluido en la descarga actual).
- **Apache Tomcat 10 u 11** instalado y agregado como servidor en NetBeans
  (Tools > Servers > Add Server > Apache Tomcat or TomEE).
- La base de datos `inventario_pae` ya creada (la misma de EV01 — corre
  `sql/schema.sql` de ese proyecto si aún no la tienes).

⚠️ Importante: este proyecto usa **Jakarta EE** (`jakarta.servlet.*`), que es lo que
requieren Tomcat 10 y 11. Si tu NetBeans/Tomcat es una versión más antigua que solo
soporta `javax.servlet.*`, avísame para adaptar los imports.

## Cómo abrirlo en NetBeans

1. File > Open Project... y selecciona esta carpeta (`proyecto_web`), la que tiene el `pom.xml`.
2. NetBeans debería reconocerlo como proyecto Maven de tipo `war`.
3. Clic derecho sobre el proyecto > Properties > Run, y confirma que el servidor
   asignado sea el Tomcat que agregaste.
4. Clic derecho sobre el proyecto > Run (o el botón ▶️ verde). NetBeans compila,
   despliega en Tomcat y abre el navegador automáticamente en algo como
   `http://localhost:8080/inventario-pae-ev02/`.

## Estructura

```
src/main/java/com/pae/modelo/   -> Clases del modelo (reutilizadas de EV01)
src/main/java/com/pae/dao/      -> DAOs con CRUD JDBC (reutilizados de EV01)
src/main/java/com/pae/util/     -> ConexionBD (reutilizada de EV01)
src/main/java/com/pae/web/      -> Servlets: ProductoServlet, ProveedorServlet
src/main/webapp/index.jsp       -> Menu principal
src/main/webapp/productos/      -> lista.jsp, formulario.jsp
src/main/webapp/proveedores/    -> lista.jsp, formulario.jsp
pom.xml                         -> Empaquetado WAR + dependencias (Servlet API, JSTL, MySQL)
```

## Flujo de cada módulo

- **GET** `/productos` → lista todos los productos (`doGet`, acción por defecto "listar").
- **GET** `/productos?accion=nuevo` → muestra formulario vacío.
- **GET** `/productos?accion=editar&id=N` → muestra formulario con los datos del producto N.
- **GET** `/productos?accion=eliminar&id=N` → elimina el producto N y redirige al listado.
- **POST** `/productos` → recibe los datos del formulario; si `idProducto` viene vacío
  inserta, si trae un valor actualiza. Redirige al listado (patrón Post-Redirect-Get,
  evita reenvíos duplicados al refrescar el navegador).

`proveedores` sigue el mismo patrón, simplificado a listar + insertar + eliminar.
