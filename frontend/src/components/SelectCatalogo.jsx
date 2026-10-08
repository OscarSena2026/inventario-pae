import PropTypes from 'prop-types';

/**
 * SelectCatalogo (componente reutilizable)
 * Lista desplegable genérica: se llena con las opciones que reciba
 * (proveedor, bodega, empleado, sede, lote, SI/NO...). Se usa en todos los formularios y filtros.
 *
 * @param {string}   nombre     Nombre del campo (atributo name/id).
 * @param {string}   etiqueta   Texto de la etiqueta.
 * @param {Array}    opciones   Lista de { valor, texto }.
 * @param {string}   valor      Valor seleccionado actualmente.
 * @param {Function} alCambiar  Se ejecuta con el evento onChange.
 * @param {string}   [error]    Mensaje de validación para mostrar bajo el campo.
 * @param {string}   [vacio]    Texto de la opción vacía; si no se envía, no se muestra esa opción.
 */
function SelectCatalogo({ nombre, etiqueta, opciones, valor, alCambiar, error = '', vacio = undefined }) {
  return (
    <div className="mb-3">
      <label htmlFor={nombre} className="form-label fw-semibold">
        {etiqueta}
      </label>
      <select
        id={nombre}
        name={nombre}
        className={`form-select ${error ? 'is-invalid' : ''}`}
        value={valor}
        onChange={alCambiar}
      >
        {vacio !== undefined && <option value="">{vacio}</option>}
        {opciones.map((opcion) => (
          <option key={opcion.valor} value={opcion.valor}>
            {opcion.texto}
          </option>
        ))}
      </select>
      {error && <div className="invalid-feedback">{error}</div>}
    </div>
  );
}

SelectCatalogo.propTypes = {
  nombre: PropTypes.string.isRequired,
  etiqueta: PropTypes.string.isRequired,
  opciones: PropTypes.arrayOf(
    PropTypes.shape({ valor: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired, texto: PropTypes.string.isRequired })
  ).isRequired,
  valor: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  alCambiar: PropTypes.func.isRequired,
  error: PropTypes.string,
  vacio: PropTypes.string,
};

export default SelectCatalogo;
