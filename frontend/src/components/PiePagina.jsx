import PropTypes from 'prop-types';

/**
 * PiePagina
 * Muestra los créditos y la versión del sistema en todas las páginas.
 *
 * @param {string} version Versión del sistema.
 */
function PiePagina({ version }) {
  return (
    <footer className="text-center text-muted small py-4 mt-5 border-top">
      Sistema de Inventario PAE · SENA ADSO · GA7-220501096-AA4-EV03 · v{version}
    </footer>
  );
}

PiePagina.propTypes = { version: PropTypes.string.isRequired };

export default PiePagina;
