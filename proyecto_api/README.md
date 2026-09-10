# Sistema de Inventario PAE — Módulo Spring Boot + Hibernate/JPA (GA7-220501096-AA3-EV01)

API REST construida con **Spring Boot** (framework) y **Spring Data JPA / Hibernate**
(ORM) para el mismo modelo relacional de AA1/AA2 (`inventario_pae`). Esta API es el
backend único que sirve tanto al módulo **web** como al módulo **móvil** del proyecto.

## De JDBC (AA2) a un framework (AA3)

| | AA2 (`proyecto/`) | AA3 (`proyecto_api/`) |
|---|---|---|
| Acceso a datos | JDBC a mano (`DAO` + `PreparedStatement`) | Spring Data JPA (`JpaRepository`) |
| SQL | Escrito manualmente en cada DAO | Generado por Hibernate a partir del método/entidad |
| Exposición | Menú de consola / Servlets | Endpoints REST (`@RestController`) |
| Consumidores | Solo `Main.java` / JSP | Web y app móvil, ambos vía HTTP |

## Estructura

```
src/main/java/com/pae/api/
  entity/       -> 12 entidades JPA (@Entity), una por tabla de inventario_pae
  repository/   -> 12 interfaces JpaRepository<Entidad, Long> (CRUD + consultas derivadas)
  controller/   -> 12 @RestController, exponen /api/** (GET, POST, PUT, DELETE)
  ProyectoApiApplication.java -> clase main (@SpringBootApplication)
src/main/resources/application.properties -> conexión a la BD inventario_pae
```

## Endpoints disponibles (ejemplo con `productos`, igual para las otras 11 entidades)

```
GET    /api/productos              -> listar todos
GET    /api/productos/{id}         -> buscar por id
GET    /api/productos/categoria/{categoria}  -> consulta derivada (findByCategoria)
POST   /api/productos              -> crear (recibe JSON en el body)
PUT    /api/productos/{id}         -> actualizar
DELETE /api/productos/{id}         -> eliminar
```

Otros módulos: `/api/municipios`, `/api/proveedores`, `/api/empleados`, `/api/sedes`,
`/api/bodegas`, `/api/lotes`, `/api/entradas`, `/api/detalles-entrada`, `/api/salidas`,
`/api/detalles-salida`, `/api/existencias`.

## Cómo ejecutarlo

1. Tener la base de datos creada (`../sql/schema.sql`, la misma de AA2).
2. Ajustar usuario/clave en `src/main/resources/application.properties` si no usas `root/root`.
3. Ejecutar:
   ```
   mvn spring-boot:run
   ```
4. Probar, por ejemplo: `GET http://localhost:8080/api/productos`

## Estándar de codificación aplicado

- `camelCase` para métodos/atributos, `PascalCase` para clases (mismo estándar de AA1/AA2).
- Paquetes por responsabilidad: `entity`, `repository`, `controller`.
- Inyección de dependencias por constructor (no se usa `new` para los repositorios).
- Comentarios explicando el propósito de cada clase y las decisiones de diseño (relaciones `@ManyToOne`, etc.).
