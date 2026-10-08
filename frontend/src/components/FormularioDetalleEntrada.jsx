import { useState } from 'react';
import PropTypes from 'prop-types';
import SelectCatalogo from './SelectCatalogo';
import Mensaje from './Mensaje';
import { obtenerMensajeError } from '../utils/mensajes';

/**
 * FormularioDetalleEntrada (HU-03, paso 2)
 * Agrega a la entrada un lote con su cantidad y valor unitario.
 * Al guardar, la API suma la cantidad a la existencia de la bodega.
 * Valida que cantidad y valor unitario sean mayores que 0, igual que el back-end.
 *
 * @param {number}   entradaId Identificador de la entrada a la que se agrega el detalle.
 * @param {Array}    lotes     Opciones { valor, texto } de lotes (cada lote ya incluye su producto).
 * @param {Function} alAgregar Función async que recibe { idLote, cantidad, valorUnitario }.
 */
function FormularioDetalleEntrada({ entradaId, lotes, alAgregar }) {
  // Estado: valores de los campos, errores de validación, error devuelto por la API y bandera de envío.
  const [valores, setValores] = useState({ idLote: '', cantidad: '', valorUnitario: '' });
  const [errores, setErrores] = useState({});
  const [errorApi, setErrorApi] = useState('');
  const [enviando, setEnviando] = useState(false);

  // Evento onChange: copia lo que el usuario escribe o elige al campo que se llama igual (name) en el estado.
  const manejarCambio = (evento) => {
    const { name, value } = evento.target;
    setValores({ ...valores, [name]: value });
  };

  // Validaciones del lado del cliente (la API vuelve a validar).
  const validar = () => {
    const nuevos = {};
    if (!valores.idLote) nuevos.idLote = 'Selecciona un lote';
    if (!(Number(valores.cantidad) > 0)) nuevos.cantidad = 'La cantidad debe ser mayor a 0';
    if (!(Number(valores.valorUnitario) > 0)) nuevos.valorUnitario = 'El valor unitario debe ser mayor a 0';
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
      await alAgregar({
        idLote: Number(valores.idLote),
        cantidad: Number(valores.cantidad),
        valorUnitario: Number(valores.valorUnitario),
      });
      // Se limpian los campos para poder agregar otro producto.
      setValores({ idLote: '', cantidad: '', valorUnitario: '' });
    } catch (error) {
      // Se muestra el mensaje de la API y se conservan los datos para corregirlos.
      setErrorApi(obtenerMensajeError(error));
    } finally {
      // Siempre se libera el botón, haya salido bien o mal.
      setEnviando(false);
    }
  };

  return (
    <form onSubmit={manejarEnvio} noValidate className="card card-body mb-4">
      <h5 className="card-title">2. Productos de la entrada #{entradaId}</h5>
      <Mensaje tipo="error" texto={errorApi} />
      <div className="row">
        <div className="col-md-6">
          <SelectCatalogo nombre="idLote" etiqueta="Lote (producto)" opciones={lotes} valor={valores.idLote}
            alCambiar={manejarCambio} error={errores.idLote} vacio="Selecciona..." />
        </div>
        <div className="col-md-3 mb-3">
          <label htmlFor="cantidad" className="form-label fw-semibold">Cantidad</label>
          <input id="cantidad" name="cantidad" type="number" min="0" step="0.01"
            className={`form-control ${errores.cantidad ? 'is-invalid' : ''}`} value={valores.cantidad} onChange={manejarCambio} />
          {errores.cantidad && <div className="invalid-feedback">{errores.cantidad}</div>}
        </div>
        <div className="col-md-3 mb-3">
          <label htmlFor="valorUnitario" className="form-label fw-semibold">Valor unitario ($)</label>
          <input id="valorUnitario" name="valorUnitario" type="number" min="0" step="0.01"
            className={`form-control ${errores.valorUnitario ? 'is-invalid' : ''}`} value={valores.valorUnitario} onChange={manejarCambio} />
          {errores.valorUnitario && <div className="invalid-feedback">{errores.valorUnitario}</div>}
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

FormularioDetalleEntrada.propTypes = {
  entradaId: PropTypes.number.isRequired,
  lotes: PropTypes.arrayOf(PropTypes.object).isRequired,
  alAgregar: PropTypes.func.isRequired,
};

export default FormularioDetalleEntrada;
