/**
 * Capa de servicios: aquí se concentran TODAS las llamadas a la API REST de Spring Boot (AA3).
 * Los componentes no usan Axios directamente; llaman a estas funciones.
 */
import axios from 'axios';

// Dirección base de la API. Se puede cambiar con la variable VITE_API_URL (archivo .env).
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
});

// Todas las funciones devuelven directamente los datos (response.data).
const obtener = (ruta) => api.get(ruta).then((respuesta) => respuesta.data);
const enviar = (ruta, datos) => api.post(ruta, datos).then((respuesta) => respuesta.data);
const modificar = (ruta, datos) => api.put(ruta, datos).then((respuesta) => respuesta.data);

// ---------- Productos (HU-02 registrar, HU-08 modificar)
export const listarProductos = () => obtener('/productos');
export const crearProducto = (producto) => enviar('/productos', producto);
export const actualizarProducto = (id, producto) => modificar(`/productos/${id}`, producto);

// ---------- Catálogos que alimentan las listas desplegables
export const listarBodegas = () => obtener('/bodegas');
export const listarProveedores = () => obtener('/proveedores');
export const listarEmpleados = () => obtener('/empleados');
export const listarSedes = () => obtener('/sedes');
export const listarLotes = () => obtener('/lotes');

// ---------- Entradas (HU-03)
export const crearEntrada = (entrada) => enviar('/entradas', entrada);
// Al guardar un detalle de entrada, la API SUMA la cantidad a la existencia (bodega + lote).
export const crearDetalleEntrada = (detalle) => enviar('/detalles-entrada', detalle);

// ---------- Salidas (HU-04)
export const crearSalida = (salida) => enviar('/salidas', salida);
// Al guardar un detalle de salida, la API verifica el stock y RESTA la cantidad.
// Si no alcanza, responde 400 con el mensaje "Stock insuficiente...".
export const crearDetalleSalida = (detalle) => enviar('/detalles-salida', detalle);

// ---------- Inventario (HU-05)
export const listarExistencias = () => obtener('/existencias');
export const listarExistenciasPorBodega = (idBodega) => obtener(`/existencias/bodega/${idBodega}`);
