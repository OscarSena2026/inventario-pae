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
  const [valores, setValores] = useState({
    fechaSalida: fechaHoy(),
    motivo: '',
    idBodega: '',
    idSede: '',
    idEmpleado: '',
  });
  const [errores, setErrores] = useState({});
  const [enviando, setEnviando] = useState(false);

  const opcionesBodega = bodegas.map((b) => ({ valor: b.idBodega, texto: b.nombreBodega }));
  const opcionesSede = sedes.map((s) => ({ valor: s.idSede, texto: s.nombreSede }));
  const opcionesEmpleado = empleados.map((e) => ({ valor: e.idEmpleado, texto: `${e.nombres} (${e.cargo})` }));

  const manejarCambio = (evento) => {
    const { name, value } = evento.target;
    setValores({ ...valores, [name]: value });
  };

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

  const manejarEnvio = async (evento) => {
    evento.preventDefault();
    if (!validar()) return;
    setEnviando(true);
    await alCrear(valores);
    setEnviando(false);
  };

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
