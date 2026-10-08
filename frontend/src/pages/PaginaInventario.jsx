import { useEffect, useState } from 'react';
import FiltroInventario from '../components/FiltroInventario';
import TablaExistencias from '../components/TablaExistencias';
import Mensaje from '../components/Mensaje';
import { listarBodegas, listarExistencias } from '../services/api';
import { obtenerMensajeError } from '../utils/mensajes';

/**
 * PaginaInventario (ruta /inventario) - HU-05 consultar inventario (existencias).
 * Pide las existencias a la API cada vez que se abre, para ver el stock actualizado
 * después de las entradas y salidas.
 */
function PaginaInventario() {
  // Estado: existencias y bodegas de la API, filtros elegidos, aviso para el usuario y bandera de carga.
  const [existencias, setExistencias] = useState([]);
  const [bodegas, setBodegas] = useState([]);
  const [filtro, setFiltro] = useState({ idBodega: '', texto: '' });
  const [mensaje, setMensaje] = useState({ tipo: 'exito', texto: '' });
  const [cargando, setCargando] = useState(true);

  // Pide las existencias y las bodegas a la API (GET /api/existencias y GET /api/bodegas).
  const cargarDatos = async () => {
    setCargando(true);
    try {
      const [listaExistencias, listaBodegas] = await Promise.all([listarExistencias(), listarBodegas()]);
      setExistencias(listaExistencias);
      setBodegas(listaBodegas);
      setMensaje({ tipo: 'exito', texto: '' });
    } catch (error) {
      setMensaje({ tipo: 'error', texto: obtenerMensajeError(error) });
    } finally {
      setCargando(false);
    }
  };

  // useEffect: carga los datos al abrir la página.
  useEffect(() => {
    cargarDatos();
  }, []);

  // Aplica los filtros elegidos (bodega y texto del producto) sobre la lista.
  const existenciasFiltradas = existencias.filter((e) => {
    const coincideBodega = !filtro.idBodega || String(e.bodega.idBodega) === String(filtro.idBodega);
    const coincideTexto = e.lote.producto.nombreProducto.toLowerCase().includes(filtro.texto.trim().toLowerCase());
    return coincideBodega && coincideTexto;
  });

  // La página solo coordina: el filtro avisa qué se eligió y la tabla dibuja la lista ya filtrada.
  return (
    <section>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2 className="h3 mb-0">Inventario (existencias)</h2>
        <button type="button" className="btn btn-sm btn-outline-success" onClick={cargarDatos}>
          Actualizar
        </button>
      </div>
      <Mensaje tipo={mensaje.tipo} texto={mensaje.texto} />
      <FiltroInventario bodegas={bodegas} alFiltrar={setFiltro} />
      {cargando ? <p>Cargando existencias...</p> : <TablaExistencias existencias={existenciasFiltradas} />}
    </section>
  );
}

export default PaginaInventario;
