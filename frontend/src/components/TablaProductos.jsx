import PropTypes from 'prop-types';

/**
 * TablaProductos
 * Lista los productos registrados (HU-02) y permite elegir uno para modificarlo (HU-08).
 * Solo muestra datos: no tiene estado propio ni habla con la API.
 *
 * @param {Array}    productos Lista de productos que entrega la API.
 * @param {Function} alEditar  Se ejecuta con el producto elegido al hacer clic en «Editar».
 */
function TablaProductos({ productos, alEditar }) {
  if (productos.length === 0) {
    return <p className="text-muted">Aún no hay productos registrados.</p>;
  }
  return (
    <div className="table-responsive">
      <table className="table table-striped table-hover align-middle">
        <thead className="table-success">
          <tr>
            <th>#</th>
            <th>Producto</th>
            <th>Categoría</th>
            <th>Unidad</th>
            <th>Perecedero</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {productos.map((producto) => (
            <tr key={producto.idProducto}>
              <td>{producto.idProducto}</td>
              <td>{producto.nombreProducto}</td>
              <td>{producto.categoria || '—'}</td>
              <td>{producto.unidadMedida}</td>
              <td>{producto.perecedero}</td>
              <td className="text-end">
                {/* Evento onClick: avisa al padre qué producto se quiere editar */}
                <button type="button" className="btn btn-sm btn-outline-success" onClick={() => alEditar(producto)}>
                  Editar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

TablaProductos.propTypes = {
  productos: PropTypes.arrayOf(PropTypes.object).isRequired,
  alEditar: PropTypes.func.isRequired,
};

export default TablaProductos;
