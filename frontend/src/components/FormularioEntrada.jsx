import { useState } from 'react';
import PropTypes from 'prop-types';
import SelectCatalogo from './SelectCatalogo';
import { fechaHoy } from '../utils/formato';

/**
 * FormularioEntrada (HU-03, paso 1)
 * Captura los datos generales de una entrada de productos: fecha, factura, bodega, proveedor y empleado.
 * La API maneja la entrada y su detalle por separado, por eso este formulario solo crea la «cabecera».
 *
 * @param {Array}    bodegas      Bodegas que entrega la API.
 * @param {Array}    proveedores  Proveedores que entrega la API.
 * @param {Array}    empleados    Empleados que entrega la API.
 * @param {Function} alCrear      Función async que recibe los datos y crea la entrada.
 */
function FormularioEntrada({ bodegas, proveedores, empleados, alCrear }) {
  const [valores, setValores] = useState({
    fechaEntrada: fechaHoy(),
    numeroFactura: '',
    idBodega: '',
    idProveedor: '',
    idEmpleado: '',
  });
  const [errores, setErrores] = useState({});
  const [enviando, setEnviando] = useState(false);

  // Convierte los datos de la API en opciones { valor, texto } para las listas desplegables.
  const opcionesBodega = bodegas.map((b) => ({ valor: b.idBodega, texto: b.nombreBodega }));
  const opcionesProveedor = proveedores.map((p) => ({ valor: p.idProveedor, texto: p.nombreProveedor }));
  const opcionesEmpleado = empleados.map((e) => ({ valor: e.idEmpleado, texto: `${e.nombres} (${e.cargo})` }));

  // Evento onChange de todos los campos.
  const manejarCambio = (evento) => {
    const { name, value } = evento.target;
    setValores({ ...valores, [name]: value });
  };

  // Todos los campos, excepto la factura, son obligatorios en la API.
  const validar = () => {
    const nuevos = {};
    if (!valores.fechaEntrada) nuevos.fechaEntrada = 'La fecha es obligatoria';
    if (!valores.idBodega) nuevos.idBodega = 'Selecciona una bodega';
    if (!valores.idProveedor) nuevos.idProveedor = 'Selecciona un proveedor';
    if (!valores.idEmpleado) nuevos.idEmpleado = 'Selecciona el empleado responsable';
    if (valores.numeroFactura.length > 20) nuevos.numeroFactura = 'Máximo 20 caracteres';
    setErrores(nuevos);
    return Object.keys(nuevos).length === 0;
  };

  // Evento onSubmit.
  const manejarEnvio = async (evento) => {
    evento.preventDefault();
    if (!validar()) return;
    setEnviando(true);
    await alCrear(valores);
    setEnviando(false);
  };

  return (
    <form onSubmit={manejarEnvio} noValidate className="card card-body mb-4">
      <h5 className="card-title">1. Datos generales de la entrada</h5>
      <div className="row">
        <div className="col-md-6 mb-3">
          <label htmlFor="fechaEntrada" className="form-label fw-semibold">Fecha de entrada</label>
          <input
            id="fechaEntrada"
            name="fechaEntrada"
            type="date"
            className={`form-control ${errores.fechaEntrada ? 'is-invalid' : ''}`}
            value={valores.fechaEntrada}
            onChange={manejarCambio}
          />
          {errores.fechaEntrada && <div className="invalid-feedback">{errores.fechaEntrada}</div>}
        </div>
        <div className="col-md-6 mb-3">
          <label htmlFor="numeroFactura" className="form-label fw-semibold">Número de factura (opcional)</label>
          <input
            id="numeroFactura"
            name="numeroFactura"
            className={`form-control ${errores.numeroFactura ? 'is-invalid' : ''}`}
            value={valores.numeroFactura}
            onChange={manejarCambio}
          />
          {errores.numeroFactura && <div className="invalid-feedback">{errores.numeroFactura}</div>}
        </div>
        <div className="col-md-4">
          <SelectCatalogo nombre="idBodega" etiqueta="Bodega" opciones={opcionesBodega} valor={valores.idBodega}
            alCambiar={manejarCambio} error={errores.idBodega} vacio="Selecciona..." />
        </div>
        <div className="col-md-4">
          <SelectCatalogo nombre="idProveedor" etiqueta="Proveedor" opciones={opcionesProveedor} valor={valores.idProveedor}
            alCambiar={manejarCambio} error={errores.idProveedor} vacio="Selecciona..." />
        </div>
        <div className="col-md-4">
          <SelectCatalogo nombre="idEmpleado" etiqueta="Empleado responsable" opciones={opcionesEmpleado} valor={valores.idEmpleado}
            alCambiar={manejarCambio} error={errores.idEmpleado} vacio="Selecciona..." />
        </div>
      </div>
      <div>
        <button type="submit" className="btn btn-success" disabled={enviando}>
          {enviando ? 'Creando...' : 'Crear entrada'}
        </button>
      </div>
    </form>
  );
}

FormularioEntrada.propTypes = {
  bodegas: PropTypes.arrayOf(PropTypes.object).isRequired,
  proveedores: PropTypes.arrayOf(PropTypes.object).isRequired,
  empleados: PropTypes.arrayOf(PropTypes.object).isRequired,
  alCrear: PropTypes.func.isRequired,
};

export default FormularioEntrada;
