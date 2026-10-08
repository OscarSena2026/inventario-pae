import PropTypes from 'prop-types';

/**
 * Mensaje (componente reutilizable)
 * Muestra un aviso de éxito o de error, por ejemplo el «Stock insuficiente» que devuelve la API.
 * Usado en: HU-02, HU-03, HU-04 y HU-08.
 *
 * @param {string} tipo  'exito' o 'error'. Define el color del aviso.
 * @param {string} texto Texto a mostrar. Si está vacío, no se dibuja nada.
 */
function Mensaje({ tipo = 'exito', texto = '' }) {
  if (!texto) {
    return null;
  }
  const clase = tipo === 'error' ? 'alert-danger' : 'alert-success';
  return (
    <div className={`alert ${clase}`} role="alert">
      {texto}
    </div>
  );
}

Mensaje.propTypes = {
  tipo: PropTypes.oneOf(['exito', 'error']),
  texto: PropTypes.string,
};

export default Mensaje;
