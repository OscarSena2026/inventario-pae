# Sistema de Inventario PAE — Módulo Java + JDBC (GA7-220501096-AA2-EV01)

Código fuente del módulo del proyecto, conectado a MySQL por JDBC, con CRUD completo
(inserción, consulta, actualización y eliminación) para el modelo relacional definido
en AA1 (`Diagrama_ERR_PAE.mwb` — esquema `inventario_pae`).

## ⚠️ Nota sobre la base tomada de AA1

Al revisar tus artefactos previos encontré **dos** modelos distintos:

1. `Diagrama_de_clases...GA4-220501095-AA2-EV04.pdf`: diagrama de clases UML pensado
   para una arquitectura **Node.js/TypeScript + PostgreSQL**.
2. `Diagrama_ERR_PAE.mwb`: modelo entidad-relación hecho en **MySQL Workbench**
   (esquema `inventario_pae`, con tablas `producto`, `proveedor`, `lote`, `entrada`,
   `salida`, `bodega`, `sede`, `municipio`, `empleado`, `existencia`, etc.).



## Estructura del proyecto

```
sql/schema.sql                     -> Script DDL para crear la BD en MySQL
src/main/java/com/pae/util/        -> ConexionBD (conexión JDBC)
src/main/java/com/pae/modelo/      -> Clases del modelo (POJOs, una por tabla)
src/main/java/com/pae/dao/         -> DAOs con CRUD completo por tabla (PreparedStatement)
src/main/java/com/pae/Main.java    -> Menú de consola para probar el CRUD
pom.xml                            -> Proyecto Maven (dependencia mysql-connector-j)
```

12 tablas → 12 clases modelo → 12 DAOs, cada uno con `insertar`, `consultarTodos`,
`buscarPorId`, `actualizar`, `eliminar`. `EntradaDAO`/`SalidaDAO` incluyen además
`insertarYObtenerId` para encadenar el detalle, y `ExistenciaDAO.ajustarCantidad`
suma/resta el stock cuando se registra una entrada o salida (ver `Main.java`).

## Cómo ejecutarlo

1. **Crear la base de datos**: abre MySQL Workbench o la consola de MySQL y ejecuta
   `sql/schema.sql`.
2. **Configurar credenciales**: edita `src/main/java/com/pae/util/ConexionBD.java`
   y cambia `USUARIO`/`CLAVE` si no usas `root/root`.
3. **Compilar y ejecutar** (con Maven instalado):
   ```
   mvn compile exec:java -Dexec.mainClass="com.pae.Main"
   ```
   o generar el jar:
   ```
   mvn package
   java -cp target/inventario-pae-ev01.jar:<ruta-al-mysql-connector-j.jar> com.pae.Main
   ```
   Si usas un IDE (IntelliJ/Eclipse/NetBeans), solo agrega la dependencia
   `mysql-connector-j` (o descarga el .jar) al classpath y ejecuta `Main.java`.

## Versionamiento (Git)

El repositorio de AA1 ya debería estar configurado. Para este entregable:
```
git add .
git commit -m "EV01: codificación de módulos - CRUD JDBC MySQL"
git push
```
Recuerda incluir en tu entrega el archivo comprimido **y** el enlace al repositorio,
tal como pide la guía.

## Estándar de codificación aplicado

- `camelCase` para variables y métodos, `PascalCase` para clases (ej. `ProductoDAO`).
- Paquetes por responsabilidad: `modelo`, `dao`, `util`.
- Todas las consultas usan `PreparedStatement` (evita inyección SQL).
- Manejo de conexiones con `try-with-resources` (se cierran automáticamente).
