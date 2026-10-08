import { useState } from 'react';
import PropTypes from 'prop-types';
import SelectCatalogo from './SelectCatalogo';
import Mensaje from './Mensaje';
import { obtenerMensajeError } from '../utils/mensajes';

/**
 * FormularioDetalleSalida (HU-04, paso 2)
 * Agrega a la salida un lote con la cantidad a despachar.
 * La API verifica el stock: si no alcanza responde 400 «Stock insuficiente...». Este componente
 * muestra ese mensaje y conserva los datos para que el usuario corrija la cantidad.
 *
 * @param {number}   salidaId  Identificador de la salida a la que se agrega el detalle.
 * @param {Array}    lotes     Opciones { valor, texto } de los lotes con existencia en la bodega.
 * @param {Function} alAgregar Función async que recibe { idLote, cantidad }.
 */
function FormularioDetalleSalida({ salidaId, lotes, alAgregar }) {
  // Estado: valores de los campos, errores de validación, error devuelto por la API y bandera de envío.
  const [valores, setValores] = useState({ idLote: '', cantidad: '' });
  const [errores, setErrores] = useState({});
  const [errorApi, setErrorApi] = useState('');
  const [enviando, setEnviando] = useState(false);

  // Evento onChange: copia lo que el usuario escribe o elige al campo que se llama igual (name) en el estado.
  const manejarCambio = (evento) => {
    const { name, value } = evento.target;
    setValores({ ...valores, [name]: value });
  };

  // Validación del lado del cliente: hay que elegir un lote y la cantidad debe ser mayor que 0.
  // El stock disponible NO se valida aquí: lo verifica la API (regla de negocio).
  const validar = () => {
    const nuevos = {};
    if (!valores.idLote) nuevos.idLote = 'Selecciona un lote';
    if (!(Number(valores.cantidad) > 0)) nuevos.cantidad = 'La cantidad debe ser mayor a 0';
    setErrores(nuevos);
    return Object.keys(nuevos).length === 0;
  };

  // Evento onSubmit: valida y llama a la página padre; si la API rechaza, muestra el error y conserva los datos.
  const manejarEnvio = async (evento) => {
    evento.preventDefault();
    setErrorApi('');
    if (!validar()) return;
    setEnviando(true);
    try {
      // Los campos del formulario son texto: se convierten a número antes de enviarlos.
      await alAgregar({ idLote: Number(valores.idLote), cantidad: Number(valores.cantidad) });
      // Salió bien: se limpian los campos para agregar otro producto.
      setValores({ idLote: '', cantidad: '' });
    } catch (error) {
      // Ejemplo: «Stock insuficiente: no hay suficiente cantidad disponible de este lote...»
      setErrorApi(obtenerMensajeError(error));
    } finally {
      // Siempre se libera el botón, haya salido bien o mal.
      setEnviando(false);
    }
  };

  return (
    <form onSubmit={manejarEnvio} noValidate className="card card-body mb-4">
      <h5 className="card-title">2. Productos de la salida #{salidaId}</h5>
      {/* Aviso rojo con el mensaje de la API (por ejemplo, «Stock insuficiente...») */}
      <Mensaje tipo="error" texto={errorApi} />
      {lotes.length === 0 && (
        <p className="text-muted">La bodega elegida no tiene existencias disponibles para despachar.</p>
      )}
      <div className="row">
        <div className="col-md-8">
          <SelectCatalogo nombre="idLote" etiqueta="Lote disponible" opciones={lotes} valor={valores.idLote}
            alCambiar={manejarCambio} error={errores.idLote} vacio="Selecciona..." />
        </div>
        <div className="col-md-4 mb-3">
          <label htmlFor="cantidad" className="form-label fw-semibold">Cantidad a despachar</label>
          <input id="cantidad" name="cantidad" type="number" min="0" step="0.01"
            className={`form-control ${errores.cantidad ? 'is-invalid' : ''}`} value={valores.cantidad} onChange={manejarCambio} />
          {errores.cantidad && <div className="invalid-feedback">{errores.cantidad}</div>}
        </div>
      </div>
      <div>
        <button type="submit" className="btn btn-success" disabled={enviando}>
          {enviando ? 'Agregando...' : 'Agregar producto'}
        </button>
      </div>
    </form>
  );
}

FormularioDetalleSalida.propTypes = {
  salidaId: PropTypes.number.isRequired,
  lotes: PropTypes.arrayOf(PropTypes.object).isRequired,
  alAgregar: PropTypes.func.isRequired,
};

export default FormularioDetalleSalida;
