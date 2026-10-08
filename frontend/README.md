# Sistema de Inventario PAE — Front-end (React + Vite)

Evidencia **GA7-220501096-AA4-EV03** · SENA ADSO · Aprendiz: Oscar Fabian Estrada Arango

Interfaz web del módulo de inventario del Programa de Alimentación Escolar. Consume la API REST
de Spring Boot construida en AA3 (`proyecto_api`, paquete `com.pae.api`).

## Historias de usuario cubiertas

| Historia | Pantalla | Ruta |
|---|---|---|
| HU-02 Registrar producto | Productos | `/productos` |
| HU-08 Modificar producto | Productos | `/productos` |
| HU-03 Registrar entrada (suma stock) | Entradas | `/entradas` |
| HU-04 Registrar salida (valida y descuenta stock) | Salidas | `/salidas` |
| HU-05 Consultar inventario | Inventario | `/inventario` |

Las demás historias del diseño (HU-01, 06, 07, 09 y 10) quedan fuera del alcance de este módulo,
igual que en AA3-EV02.

## Requisitos

- Node.js 18 o superior.
- La API de Spring Boot en ejecución en `http://localhost:8080` con la base de datos `inventario_pae`.
- **CORS habilitado en la API** para `http://localhost:5173`: copiar `cambios_en_la_api/CorsConfig.java`
  al paquete `com.pae.api.config` de `proyecto_api` y reiniciar la API.

## Cómo ejecutarlo

```bash
cd frontend
npm install
npm run dev        # abre http://localhost:5173
```

Si la API usa otra dirección, copiar `.env.example` como `.env` y cambiar `VITE_API_URL`.

Otros comandos: `npm run build` (compilar) y `npm test` (pruebas).

## Estructura

```
src/
├── main.jsx                 punto de entrada (React Router)
├── App.jsx                  componente raíz: menú, rutas y pie
├── components/              piezas reutilizables y formularios
├── pages/                   una página por pantalla (manejan el estado y llaman a la API)
├── services/api.js          TODAS las llamadas a la API REST (Axios)
├── utils/                   mensajes de error y formato de fechas, moneda y lotes
└── __tests__/               pruebas de los flujos principales
```

## Componentes (17, definidos en AA4-EV02)

| Tipo | Componentes |
|---|---|
| Generales | `App`, `Encabezado`, `PiePagina` |
| Productos (HU-02, HU-08) | `PaginaProductos`, `FormularioProducto`, `TablaProductos` |
| Entradas (HU-03) | `PaginaEntradas`, `FormularioEntrada`, `FormularioDetalleEntrada` |
| Salidas (HU-04) | `PaginaSalidas`, `FormularioSalida`, `FormularioDetalleSalida` |
| Inventario (HU-05) | `PaginaInventario`, `FiltroInventario`, `TablaExistencias` |
| Reutilizables | `SelectCatalogo`, `Mensaje` |

Diferencia con el documento de AA4-EV02: en los formularios de detalle se recibe solo `lotes`
(sin `productos`), porque en la API cada lote ya trae su producto.

## Reglas de la API que respeta el front-end

- Producto: nombre obligatorio y único, unidad de medida obligatoria, perecedero solo `SI` o `NO`.
- Detalle de entrada y de salida: cantidad (y valor unitario) mayores que 0.
- Entrada: al guardar un detalle, la API suma el stock; la pantalla Inventario lo refleja.
- Salida: si no hay stock, la API responde 400 «Stock insuficiente…»; el formulario muestra el mensaje.
- Los errores 400 (`GlobalExceptionHandler`) se leen en `utils/mensajes.js` y se muestran con `Mensaje`.

## Estándares de codificación

- Un componente por archivo, con nombre en **PascalCase** y extensión `.jsx`.
- Variables y funciones en **camelCase** (en español, igual que los campos de la API); constantes en `MAYUSCULAS_CON_GUION_BAJO`.
- Componentes **funcionales con Hooks** (`useState`, `useEffect`), como recomienda react.dev.
- Cada componente lleva un comentario de documentación (qué hace, historia de usuario y parámetros) y valida sus props con `PropTypes`.
- Las llamadas a la API están solo en `services/api.js`; los componentes no usan Axios directamente.
- Formato: 2 espacios de sangría, comillas simples y punto y coma (ver `.editorconfig`).

## Versionamiento

El proyecto vive en la carpeta `frontend/` del mismo repositorio del sistema:
https://github.com/OscarSena2026/inventario-pae
