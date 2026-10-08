import { useState } from 'react';
import PropTypes from 'prop-types';
import SelectCatalogo from './SelectCatalogo';

// Valores de un formulario vacío (producto nuevo).
const VALORES_INICIALES = { nombreProducto: '', categoria: '', unidadMedida: '', perecedero: 'NO' };

// Opciones del campo perecedero: la API solo acepta 'SI' o 'NO'.
const OPCIONES_PERECEDERO = [
  { valor: 'NO', texto: 'NO' },
  { valor: 'SI', texto: 'SI' },
];

/**
 * FormularioProducto
 * Sirve para registrar (HU-02) y para modificar (HU-08) un producto: un solo componente, sin duplicar código.
 * Valida los mismos campos obligatorios que la API (Bean Validation) antes de enviar.
 *
 * @param {object|null} productoInicial Producto a editar; null si es uno nuevo.
 * @param {Function}    alGuardar       Función async que recibe los datos y devuelve true si se guardó.
 * @param {Function}    [alCancelar]    Se ejecuta al cancelar la edición.
 */
function FormularioProducto({ productoInicial = null, alGuardar, alCancelar = undefined }) {
  // Estado: valores de los campos y errores de validación.
  const [valores, setValores] = useState(
    productoInicial
      ? {
          nombreProducto: productoInicial.nombreProducto,
          categoria: productoInicial.categoria || '',
          unidadMedida: productoInicial.unidadMedida,
          perecedero: productoInicial.perecedero,
        }
      : VALORES_INICIALES
  );
  const [errores, setErrores] = useState({});

  // Evento onChange: guarda en el estado lo que el usuario escribe.
  const manejarCambio = (evento) => {
    const { name, value } = evento.target;
    setValores({ ...valores, [name]: value });
  };

  // Reglas de validación (iguales a las de la entidad Producto de la API).
  const validar = () => {
    const nuevos = {};
    if (!valores.nombreProducto.trim()) nuevos.nombreProducto = 'El nombre del producto es obligatorio';
    else if (valores.nombreProducto.length > 80) nuevos.nombreProducto = 'Máximo 80 caracteres';
    if (valores.categoria.length > 40) nuevos.categoria = 'Máximo 40 caracteres';
    if (!valores.unidadMedida.trim()) nuevos.unidadMedida = 'La unidad de medida es obligatoria';
    else if (valores.unidadMedida.length > 15) nuevos.unidadMedida = 'Máximo 15 caracteres';
    if (!['SI', 'NO'].includes(valores.perecedero)) nuevos.perecedero = "Solo se acepta 'SI' o 'NO'";
    setErrores(nuevos);
    return Object.keys(nuevos).length === 0;
  };

  // Evento onSubmit: evita recargar la página, valida y envía los datos al padre.
  const manejarEnvio = async (evento) => {
    evento.preventDefault();
    if (!validar()) return;
    const guardado = await alGuardar({
      nombreProducto: valores.nombreProducto.trim(),
      categoria: valores.categoria.trim(),
      unidadMedida: valores.unidadMedida.trim(),
      perecedero: valores.perecedero,
    });
    // Si era un producto nuevo y se guardó bien, se limpia el formulario.
    if (guardado && !productoInicial) setValores(VALORES_INICIALES);
  };

  return (
    <form onSubmit={manejarEnvio} noValidate className="card card-body mb-4">
      <h5 className="card-title">{productoInicial ? 'Modificar producto' : 'Registrar producto'}</h5>
      <div className="row">
        <div className="col-md-6 mb-3">
          <label htmlFor="nombreProducto" className="form-label fw-semibold">Nombre del producto</label>
          <input
            id="nombreProducto"
            name="nombreProducto"
            className={`form-control ${errores.nombreProducto ? 'is-invalid' : ''}`}
            value={valores.nombreProducto}
            onChange={manejarCambio}
          />
          {errores.nombreProducto && <div className="invalid-feedback">{errores.nombreProducto}</div>}
        </div>
        <div className="col-md-6 mb-3">
          <label htmlFor="categoria" className="form-label fw-semibold">Categoría (opcional)</label>
          <input
            id="categoria"
            name="categoria"
            className={`form-control ${errores.categoria ? 'is-invalid' : ''}`}
            value={valores.categoria}
            onChange={manejarCambio}
          />
          {errores.categoria && <div className="invalid-feedback">{errores.categoria}</div>}
        </div>
        <div className="col-md-6 mb-3">
          <label htmlFor="unidadMedida" className="form-label fw-semibold">Unidad de medida</label>
          <input
            id="unidadMedida"
            name="unidadMedida"
            placeholder="kg, litro, unidad..."
            className={`form-control ${errores.unidadMedida ? 'is-invalid' : ''}`}
            value={valores.unidadMedida}
            onChange={manejarCambio}
          />
          {errores.unidadMedida && <div className="invalid-feedback">{errores.unidadMedida}</div>}
        </div>
        <div className="col-md-6">
          <SelectCatalogo
            nombre="perecedero"
            etiqueta="¿Es perecedero?"
            opciones={OPCIONES_PERECEDERO}
            valor={valores.perecedero}
            alCambiar={manejarCambio}
            error={errores.perecedero}
          />
        </div>
      </div>
      <div className="d-flex gap-2">
        <button type="submit" className="btn btn-success">
          {productoInicial ? 'Guardar cambios' : 'Registrar producto'}
        </button>
        {productoInicial && alCancelar && (
          <button type="button" className="btn btn-outline-secondary" onClick={alCancelar}>
            Cancelar
          </button>
        )}
      </div>
    </form>
  );
}

FormularioProducto.propTypes = {
  productoInicial: PropTypes.shape({
    idProducto: PropTypes.number,
    nombreProducto: PropTypes.string,
    categoria: PropTypes.string,
    unidadMedida: PropTypes.string,
    perecedero: PropTypes.string,
  }),
  alGuardar: PropTypes.func.isRequired,
  alCancelar: PropTypes.func,
};

export default FormularioProducto;
