/**
 * Convierte un error de Axios en un texto claro para el usuario.
 *
 * La API (GlobalExceptionHandler) responde los errores 400 con un JSON así:
 *   { status: 400, error: "...", detalles: "texto" }            -> regla de negocio
 *   { status: 400, error: "...", detalles: { campo: "texto" } } -> validación de campos
 *
 * @param {Error} error Error recibido en el catch de una petición Axios.
 * @returns {string} Mensaje listo para mostrar en pantalla.
 */
export function obtenerMensajeError(error) {
  // Sin respuesta: la API está apagada, la red falló o el navegador bloqueó la petición por CORS.
  if (!error.response) {
    return 'No se pudo conectar con la API. Verifica que el back-end esté en ejecución (puerto 8080) y que CORS esté habilitado.';
  }

  const datos = error.response.data;

  // Regla de negocio: "detalles" es un texto (por ejemplo, "Stock insuficiente...").
  if (datos && typeof datos.detalles === 'string') {
    return datos.detalles;
  }
  // Validación de campos: "detalles" es un objeto { campo: mensaje }.
  if (datos && datos.detalles && typeof datos.detalles === 'object') {
    return Object.values(datos.detalles).join('. ');
  }
  if (datos && datos.message) {
    return datos.message;
  }
  return `Error ${error.response.status}: no se pudo completar la operación.`;
}
