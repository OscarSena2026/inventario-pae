import { useState } from 'react';
import PropTypes from 'prop-types';
import SelectCatalogo from './SelectCatalogo';
import { fechaHoy } from '../utils/formato';

/**
 * FormularioSalida (HU-04, paso 1)
 * Captura los datos generales de una salida (despacho): fecha, motivo, bodega, sede de destino y empleado.
 *
 * @param {Array}    bodegas   Bodegas que entrega la API.
 * @param {Array}    sedes     Sedes (instituciones educativas) que entrega la API.
 * @param {Array}    empleados Empleados que entrega la API.
 * @param {Function} alCrear   Función async que recibe los datos y crea la salida.
 */
function FormularioSalida({ bodegas, sedes, empleados, alCrear }) {
  // Estado: valores de los campos (la fecha arranca en hoy), errores de validación y bandera de envío.
  const [valores, setValores] = useState({
    fechaSalida: fechaHoy(),
    motivo: '',
    idBodega: '',
    idSede: '',
    idEmpleado: '',
  });
  const [errores, setErrores] = useState({});
  const [enviando, setEnviando] = useState(false);

  // Convierte los datos de la API en opciones { valor, texto } para las listas desplegables.
  const opcionesBodega = bodegas.map((b) => ({ valor: b.idBodega, texto: b.nombreBodega }));
  const opcionesSede = sedes.map((s) => ({ valor: s.idSede, texto: s.nombreSede }));
  const opcionesEmpleado = empleados.map((e) => ({ valor: e.idEmpleado, texto: `${e.nombres} (${e.cargo})` }));

  // Evento onChange: copia lo que el usuario escribe o elige al campo que se llama igual (name) en el estado.
  const manejarCambio = (evento) => {
    const { name, value } = evento.target;
    setValores({ ...valores, [name]: value });
  };

  // Validación del lado del cliente: fecha, bodega, sede y empleado son obligatorios; el motivo, máximo 100 caracteres.
  const validar = () => {
    const nuevos = {};
    if (!valores.fechaSalida) nuevos.fechaSalida = 'La fecha es obligatoria';
    if (!valores.idBodega) nuevos.idBodega = 'Selecciona una bodega';
    if (!valores.idSede) nuevos.idSede = 'Selecciona la sede de destino';
    if (!valores.idEmpleado) nuevos.idEmpleado = 'Selecciona el empleado responsable';
    if (valores.motivo.length > 100) nuevos.motivo = 'Máximo 100 caracteres';
    setErrores(nuevos);
    return Object.keys(nuevos).length === 0;
  };

  // Evento onSubmit: valida, bloquea el botón mientras la página padre crea la salida y luego lo libera.
  const manejarEnvio = async (evento) => {
    evento.preventDefault();
    if (!validar()) return;
    setEnviando(true);
    await alCrear(valores);
    setEnviando(false);
  };

  // Formulario controlado: cada campo muestra el valor del estado y su error (si lo hay) bajo el campo.
  return (
    <form onSubmit={manejarEnvio} noValidate className="card card-body mb-4">
      <h5 className="card-title">1. Datos generales de la salida</h5>
      <div className="row">
        <div className="col-md-4 mb-3">
          <label htmlFor="fechaSalida" className="form-label fw-semibold">Fecha de salida</label>
          <input id="fechaSalida" name="fechaSalida" type="date"
            className={`form-control ${errores.fechaSalida ? 'is-invalid' : ''}`} value={valores.fechaSalida} onChange={manejarCambio} />
          {errores.fechaSalida && <div className="invalid-feedback">{errores.fechaSalida}</div>}
        </div>
        <div className="col-md-8 mb-3">
          <label htmlFor="motivo" className="form-label fw-semibold">Motivo (opcional)</label>
          <input id="motivo" name="motivo" placeholder="Ej.: Entrega semanal de víveres"
            className={`form-control ${errores.motivo ? 'is-invalid' : ''}`} value={valores.motivo} onChange={manejarCambio} />
          {errores.motivo && <div className="invalid-feedback">{errores.motivo}</div>}
        </div>
        <div className="col-md-4">
          <SelectCatalogo nombre="idBodega" etiqueta="Bodega de origen" opciones={opcionesBodega} valor={valores.idBodega}
            alCambiar={manejarCambio} error={errores.idBodega} vacio="Selecciona..." />
        </div>
        <div className="col-md-4">
          <SelectCatalogo nombre="idSede" etiqueta="Sede de destino" opciones={opcionesSede} valor={valores.idSede}
            alCambiar={manejarCambio} error={errores.idSede} vacio="Selecciona..." />
        </div>
        <div className="col-md-4">
          <SelectCatalogo nombre="idEmpleado" etiqueta="Empleado responsable" opciones={opcionesEmpleado} valor={valores.idEmpleado}
            alCambiar={manejarCambio} error={errores.idEmpleado} vacio="Selecciona..." />
        </div>
      </div>
      <div>
        {/* Se deshabilita mientras se envía para evitar crear dos salidas con doble clic */}
        <button type="submit" className="btn btn-success" disabled={enviando}>
          {enviando ? 'Creando...' : 'Crear salida'}
        </button>
      </div>
    </form>
  );
}

FormularioSalida.propTypes = {
  bodegas: PropTypes.arrayOf(PropTypes.object).isRequired,
  sedes: PropTypes.arrayOf(PropTypes.object).isRequired,
  empleados: PropTypes.arrayOf(PropTypes.object).isRequired,
  alCrear: PropTypes.func.isRequired,
};

export default FormularioSalida;
