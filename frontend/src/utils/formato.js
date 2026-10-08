/** Funciones de formato para fechas y números que se muestran en pantalla. */

/**
 * Devuelve la fecha de hoy en formato AAAA-MM-DD (el que usa la API y el input type="date").
 * @returns {string}
 */
export function fechaHoy() {
  const hoy = new Date();
  const mes = String(hoy.getMonth() + 1).padStart(2, '0');
  const dia = String(hoy.getDate()).padStart(2, '0');
  return `${hoy.getFullYear()}-${mes}-${dia}`;
}

/**
 * Formatea un valor como pesos colombianos. Ejemplo: 12500 -> "$ 12.500".
 * @param {number|string} valor
 * @returns {string}
 */
export function formatearMoneda(valor) {
  return new Intl.NumberFormat('es-CO', {
    style: 'currency',
    currency: 'COP',
    maximumFractionDigits: 0,
  }).format(Number(valor));
}

/**
 * Formatea una cantidad con separador de miles y hasta 2 decimales.
 * @param {number|string} valor
 * @returns {string}
 */
export function formatearCantidad(valor) {
  return new Intl.NumberFormat('es-CO', { maximumFractionDigits: 2 }).format(Number(valor));
}

/**
 * Arma el texto de un lote para mostrarlo en una lista desplegable.
 * @param {object} lote Lote con su producto (la API lo envía anidado).
 * @param {string} [extra] Texto adicional al final (por ejemplo, la cantidad disponible).
 * @returns {string}
 */
export function etiquetaLote(lote, extra = '') {
  const producto = lote.producto ? lote.producto.nombreProducto : 'Producto';
  const base = `${producto} · Lote #${lote.idLote} · vence ${lote.fechaVencimiento}`;
  return extra ? `${base} · ${extra}` : base;
}
