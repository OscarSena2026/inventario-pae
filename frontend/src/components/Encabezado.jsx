import { NavLink } from 'react-router-dom';
import PropTypes from 'prop-types';

/**
 * Encabezado
 * Muestra el nombre del sistema y el menú para pasar entre las cuatro pantallas.
 * Aparece en todas las páginas (se escribe una sola vez).
 *
 * @param {Array} enlaces Lista de { ruta, texto } del menú.
 */
function Encabezado({ enlaces }) {
  return (
    <nav className="navbar navbar-expand navbar-dark barra-pae mb-4">
      <div className="container">
        <span className="navbar-brand fw-bold">Inventario PAE</span>
        <ul className="navbar-nav ms-auto">
          {enlaces.map((enlace) => (
            <li className="nav-item" key={enlace.ruta}>
              {/* NavLink cambia de pantalla al hacer clic (onClick interno) sin recargar la página */}
              <NavLink
                to={enlace.ruta}
                className={({ isActive }) => `nav-link${isActive ? ' active fw-bold' : ''}`}
              >
                {enlace.texto}
              </NavLink>
            </li>
          ))}
        </ul>
      </div>
    </nav>
  );
}

Encabezado.propTypes = {
  enlaces: PropTypes.arrayOf(
    PropTypes.shape({ ruta: PropTypes.string.isRequired, texto: PropTypes.string.isRequired })
  ).isRequired,
};

export default Encabezado;
