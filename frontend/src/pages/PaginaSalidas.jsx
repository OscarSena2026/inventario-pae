import { useEffect, useState } from 'react';
import FormularioSalida from '../components/FormularioSalida';
import FormularioDetalleSalida from '../components/FormularioDetalleSalida';
import Mensaje from '../components/Mensaje';
import {
  crearDetalleSalida, crearSalida, listarBodegas, listarEmpleados, listarExistenciasPorBodega, listarSedes,
} from '../services/api';
import { obtenerMensajeError } from '../utils/mensajes';
import { etiquetaLote, formatearCantidad } from '../utils/formato';

/**
 * PaginaSalidas (ruta /salidas) - HU-04 registrar salida de productos.
 * Coordina el registro en dos pasos: primero la salida (cabecera) y luego sus detalles.
 * Al agregar cada detalle, la API verifica el stock y descuenta la cantidad.
 */
function PaginaSalidas() {
  const [catalogos, setCatalogos] = useState({ bodegas: [], sedes: [], empleados: [] });
  const [salida, setSalida] = useState(null);
  const [disponibles, setDisponibles] = useState([]); // existencias de la bodega de la salida
  const [detalles, setDetalles] = useState([]);
  const [mensaje, setMensaje] = useState({ tipo: 'exito', texto: '' });

  // useEffect: al abrir la página se piden a la API los datos de las listas desplegables.
  useEffect(() => {
    const cargarCatalogos = async () => {
      try {
        const [bodegas, sedes, empleados] = await Promise.all([listarBodegas(), listarSedes(), listarEmpleados()]);
        setCatalogos({ bodegas, sedes, empleados });
      } catch (error) {
        setMensaje({ tipo: 'error', texto: obtenerMensajeError(error) });
      }
    };
    cargarCatalogos();
  }, []);

  // Vuelve a pedir las existencias de la bodega para mostrar solo lotes con stock.
  const cargarDisponibles = async (idBodega) => {
    setDisponibles(await listarExistenciasPorBodega(idBodega));
  };

  // Paso 1: crea la salida (POST /api/salidas) y carga el stock de su bodega.
  const crearNuevaSalida = async (datos) => {
    try {
      const creada = await crearSalida({
        fechaSalida: datos.fechaSalida,
        motivo: datos.motivo.trim() || null,
        bodega: { idBodega: Number(datos.idBodega) },
        sede: { idSede: Number(datos.idSede) },
        empleado: { idEmpleado: Number(datos.idEmpleado) },
      });
      await cargarDisponibles(Number(datos.idBodega));
      const bodega = catalogos.bodegas.find((b) => b.idBodega === Number(datos.idBodega));
      const sede = catalogos.sedes.find((s) => s.idSede === Number(datos.idSede));
      setSalida({
        idSalida: creada.idSalida,
        idBodega: Number(datos.idBodega),
        fecha: datos.fechaSalida,
        bodega: bodega ? bodega.nombreBodega : '',
        sede: sede ? sede.nombreSede : '',
      });
      setDetalles([]);
      setMensaje({ tipo: 'exito', texto: `Salida #${creada.idSalida} creada. Ahora agrega los productos a despachar.` });
    } catch (error) {
      setMensaje({ tipo: 'error', texto: obtenerMensajeError(error) });
    }
  };

  // Paso 2: agrega un detalle (POST /api/detalles-salida).
  // Si no hay stock suficiente la API responde 400 y el error sube al formulario, que lo muestra.
  const agregarDetalle = async (datos) => {
    const guardado = await crearDetalleSalida({
      salida: { idSalida: salida.idSalida },
      lote: { idLote: datos.idLote },
      cantidad: datos.cantidad,
    });
    const existencia = disponibles.find((e) => e.lote.idLote === datos.idLote);
    setDetalles([...detalles, {
      id: guardado.idDetalleSalida,
      lote: existencia ? etiquetaLote(existencia.lote) : `Lote #${datos.idLote}`,
      cantidad: datos.cantidad,
    }]);
    await cargarDisponibles(salida.idBodega); // refresca el stock disponible
    setMensaje({ tipo: 'exito', texto: 'Producto agregado a la salida. El stock se descontó.' });
  };

  const nuevaSalida = () => {
    setSalida(null);
    setDetalles([]);
    setDisponibles([]);
    setMensaje({ tipo: 'exito', texto: '' });
  };

  // Lotes con existencia mayor que 0 en la bodega, listos para la lista desplegable.
  const opcionesLote = disponibles
    .filter((e) => Number(e.cantidadDisponible) > 0)
    .map((e) => ({ valor: e.lote.idLote, texto: etiquetaLote(e.lote, `disponible ${formatearCantidad(e.cantidadDisponible)}`) }));

  return (
    <section>
      <h2 className="h3 mb-3">Registrar salida de productos</h2>
      <Mensaje tipo={mensaje.tipo} texto={mensaje.texto} />

      {!salida && <FormularioSalida {...catalogos} alCrear={crearNuevaSalida} />}

      {salida && (
        <>
          <p className="text-muted">
            Salida <strong>#{salida.idSalida}</strong> · {salida.fecha} · {salida.bodega} → {salida.sede}
          </p>
          <FormularioDetalleSalida salidaId={salida.idSalida} lotes={opcionesLote} alAgregar={agregarDetalle} />

          {detalles.length > 0 && (
            <div className="table-responsive mb-3">
              <table className="table table-sm table-striped">
                <thead className="table-success">
                  <tr><th>Lote</th><th className="text-end">Cantidad despachada</th></tr>
                </thead>
                <tbody>
                  {detalles.map((d) => (
                    <tr key={d.id}><td>{d.lote}</td><td className="text-end">{formatearCantidad(d.cantidad)}</td></tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
          <button type="button" className="btn btn-outline-success" onClick={nuevaSalida}>
            Terminar y registrar otra salida
          </button>
        </>
      )}
    </section>
  );
}

export default PaginaSalidas;
