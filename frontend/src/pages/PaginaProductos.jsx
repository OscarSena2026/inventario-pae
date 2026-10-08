import { useEffect, useState } from 'react';
import FormularioProducto from '../components/FormularioProducto';
import TablaProductos from '../components/TablaProductos';
import Mensaje from '../components/Mensaje';
import { actualizarProducto, crearProducto, listarProductos } from '../services/api';
import { obtenerMensajeError } from '../utils/mensajes';

/**
 * PaginaProductos (ruta /productos) - HU-02 registrar producto y HU-08 modificar producto.
 * Centraliza el estado de la lista y la comunicación con la API; los hijos solo reciben datos por props.
 */
function PaginaProductos() {
  // Estado de la página.
  // Estado: lista de productos, producto que se está editando (null = registro nuevo), aviso y bandera de carga.
  const [productos, setProductos] = useState([]);
  const [productoEnEdicion, setProductoEnEdicion] = useState(null);
  const [mensaje, setMensaje] = useState({ tipo: 'exito', texto: '' });
  const [cargando, setCargando] = useState(true);

  // Pide la lista de productos a la API (GET /api/productos).
  const cargarProductos = async () => {
    try {
      setProductos(await listarProductos());
    } catch (error) {
      setMensaje({ tipo: 'error', texto: obtenerMensajeError(error) });
    } finally {
      setCargando(false);
    }
  };

  // useEffect: carga los productos una sola vez, cuando se abre la página.
  useEffect(() => {
    cargarProductos();
  }, []);

  // Registra (POST) o modifica (PUT) según haya un producto en edición. Devuelve true si salió bien.
  const guardarProducto = async (datos) => {
    try {
      if (productoEnEdicion) {
        await actualizarProducto(productoEnEdicion.idProducto, datos);
        setMensaje({ tipo: 'exito', texto: 'Producto modificado correctamente.' });
      } else {
        await crearProducto(datos);
        setMensaje({ tipo: 'exito', texto: 'Producto registrado correctamente.' });
      }
      setProductoEnEdicion(null);
      await cargarProductos();
      return true;
    } catch (error) {
      // Aquí llegan, por ejemplo, el error de nombre duplicado que devuelve la API.
      setMensaje({ tipo: 'error', texto: obtenerMensajeError(error) });
      return false;
    }
  };

  // Al elegir «Editar» en la tabla, el formulario pasa a modo modificación.
  const editarProducto = (producto) => {
    setMensaje({ tipo: 'exito', texto: '' });
    setProductoEnEdicion(producto);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  // Un solo formulario sirve para registrar y modificar; debajo se muestra la tabla con la lista.
  return (
    <section>
      <h2 className="h3 mb-3">Productos</h2>
      <Mensaje tipo={mensaje.tipo} texto={mensaje.texto} />
      {/* La «key» reinicia el formulario cuando se cambia entre registrar y modificar */}
      <FormularioProducto
        key={productoEnEdicion ? productoEnEdicion.idProducto : 'nuevo'}
        productoInicial={productoEnEdicion}
        alGuardar={guardarProducto}
        alCancelar={() => setProductoEnEdicion(null)}
      />
      {cargando ? <p>Cargando productos...</p> : <TablaProductos productos={productos} alEditar={editarProducto} />}
    </section>
  );
}

export default PaginaProductos;
