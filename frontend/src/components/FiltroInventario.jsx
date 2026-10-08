import { useState } from 'react';
import PropTypes from 'prop-types';
import SelectCatalogo from './SelectCatalogo';

/**
 * FiltroInventario (HU-05)
 * Permite filtrar las existencias por bodega o por nombre de producto.
 *
 * @param {Array}    bodegas   Bodegas que entrega la API.
 * @param {Function} alFiltrar Se ejecuta con { idBodega, texto } cada vez que cambia un filtro.
 */
function FiltroInventario({ bodegas, alFiltrar }) {
  // Estado: bodega y texto elegidos. En cada cambio se avisa al padre para que filtre la lista al instante.
  const [idBodega, setIdBodega] = useState('');
  const [texto, setTexto] = useState('');

  // Convierte las bodegas de la API en opciones { valor, texto } para la lista desplegable.
  const opcionesBodega = bodegas.map((b) => ({ valor: b.idBodega, texto: b.nombreBodega }));

  // Evento onChange de la lista de bodegas.
  const cambiarBodega = (evento) => {
    setIdBodega(evento.target.value);
    alFiltrar({ idBodega: evento.target.value, texto });
  };

  // Evento onChange del campo de texto.
  const cambiarTexto = (evento) => {
    setTexto(evento.target.value);
    alFiltrar({ idBodega, texto: evento.target.value });
  };

  return (
    <div className="card card-body mb-4">
      <div className="row align-items-end">
        <div className="col-md-6">
          <SelectCatalogo nombre="filtroBodega" etiqueta="Bodega" opciones={opcionesBodega} valor={idBodega}
            alCambiar={cambiarBodega} vacio="Todas las bodegas" />
        </div>
        <div className="col-md-6 mb-3">
          <label htmlFor="filtroTexto" className="form-label fw-semibold">Producto</label>
          <input id="filtroTexto" className="form-control" placeholder="Buscar por nombre..." value={texto} onChange={cambiarTexto} />
        </div>
      </div>
    </div>
  );
}

FiltroInventario.propTypes = {
  bodegas: PropTypes.arrayOf(PropTypes.object).isRequired,
  alFiltrar: PropTypes.func.isRequired,
};

export default FiltroInventario;
