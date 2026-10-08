import { useEffect, useState } from 'react';
import FormularioEntrada from '../components/FormularioEntrada';
import FormularioDetalleEntrada from '../components/FormularioDetalleEntrada';
import Mensaje from '../components/Mensaje';
import {
  crearDetalleEntrada, crearEntrada, listarBodegas, listarEmpleados, listarLotes, listarProveedores,
} from '../services/api';
import { obtenerMensajeError } from '../utils/mensajes';
import { etiquetaLote, formatearCantidad, formatearMoneda } from '../utils/formato';

/**
 * PaginaEntradas (ruta /entradas) - HU-03 registrar entrada de productos.
 * Coordina el registro en dos pasos, igual que la API: primero la entrada (cabecera)
 * y después sus detalles. Al agregar cada detalle, la API suma el stock a la bodega.
 */
function PaginaEntradas() {
  // Estado: catálogos para las listas, entrada creada y detalles agregados.
  // 'entrada' es null hasta que se crea la cabecera (paso 1); entonces aparece el paso 2.
  const [catalogos, setCatalogos] = useState({ bodegas: [], proveedores: [], empleados: [], lotes: [] });
  const [entrada, setEntrada] = useState(null);
  const [detalles, setDetalles] = useState([]);
  const [mensaje, setMensaje] = useState({ tipo: 'exito', texto: '' });

  // useEffect: al abrir la página se piden a la API los datos de las listas desplegables.
  useEffect(() => {
    const cargarCatalogos = async () => {
      try {
        const [bodegas, proveedores, empleados, lotes] = await Promise.all([
          listarBodegas(), listarProveedores(), listarEmpleados(), listarLotes(),
        ]);
        setCatalogos({ bodegas, proveedores, empleados, lotes });
      } catch (error) {
        setMensaje({ tipo: 'error', texto: obtenerMensajeError(error) });
      }
    };
    cargarCatalogos();
  }, []);

  // Paso 1: crea la entrada (POST /api/entradas).
  const crearNuevaEntrada = async (datos) => {
    try {
      const creada = await crearEntrada({
        fechaEntrada: datos.fechaEntrada,
        numeroFactura: datos.numeroFactura.trim() || null,
        bodega: { idBodega: Number(datos.idBodega) },
        proveedor: { idProveedor: Number(datos.idProveedor) },
        empleado: { idEmpleado: Number(datos.idEmpleado) },
      });
      const bodega = catalogos.bodegas.find((b) => b.idBodega === Number(datos.idBodega));
      setEntrada({ idEntrada: creada.idEntrada, fecha: datos.fechaEntrada, bodega: bodega ? bodega.nombreBodega : '' });
      setDetalles([]);
      setMensaje({ tipo: 'exito', texto: `Entrada #${creada.idEntrada} creada. Ahora agrega los productos recibidos.` });
    } catch (error) {
      setMensaje({ tipo: 'error', texto: obtenerMensajeError(error) });
    }
  };

  // Paso 2: agrega un detalle (POST /api/detalles-entrada). Si la API rechaza, el error sube al formulario.
  const agregarDetalle = async (datos) => {
    const guardado = await crearDetalleEntrada({
      entrada: { idEntrada: entrada.idEntrada },
      lote: { idLote: datos.idLote },
      cantidad: datos.cantidad,
      valorUnitario: datos.valorUnitario,
    });
    const lote = catalogos.lotes.find((l) => l.idLote === datos.idLote);
    setDetalles([...detalles, { id: guardado.idDetalleEntrada, lote: lote ? etiquetaLote(lote) : `Lote #${datos.idLote}`, ...datos }]);
    setMensaje({ tipo: 'exito', texto: 'Producto agregado. El stock de la bodega se actualizó.' });
  };

  // Termina la entrada actual para poder registrar otra.
  const nuevaEntrada = () => {
    setEntrada(null);
    setDetalles([]);
    setMensaje({ tipo: 'exito', texto: '' });
  };

  const opcionesLote = catalogos.lotes.map((l) => ({ valor: l.idLote, texto: etiquetaLote(l) }));

  return (
    <section>
      <h2 className="h3 mb-3">Registrar entrada de productos</h2>
      <Mensaje tipo={mensaje.tipo} texto={mensaje.texto} />

      {!entrada && <FormularioEntrada {...catalogos} alCrear={crearNuevaEntrada} />}

      {entrada && (
        <>
          <p className="text-muted">
            Entrada <strong>#{entrada.idEntrada}</strong> · {entrada.fecha} · Bodega: {entrada.bodega}
          </p>
          <FormularioDetalleEntrada entradaId={entrada.idEntrada} lotes={opcionesLote} alAgregar={agregarDetalle} />

          {detalles.length > 0 && (
            <div className="table-responsive mb-3">
              <table className="table table-sm table-striped">
                <thead className="table-success">
                  <tr><th>Lote</th><th className="text-end">Cantidad</th><th className="text-end">Valor unitario</th><th className="text-end">Subtotal</th></tr>
                </thead>
                <tbody>
                  {detalles.map((d) => (
                    <tr key={d.id}>
                      <td>{d.lote}</td>
                      <td className="text-end">{formatearCantidad(d.cantidad)}</td>
                      <td className="text-end">{formatearMoneda(d.valorUnitario)}</td>
                      <td className="text-end">{formatearMoneda(d.cantidad * d.valorUnitario)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
          <button type="button" className="btn btn-outline-success" onClick={nuevaEntrada}>
            Terminar y registrar otra entrada
          </button>
        </>
      )}
    </section>
  );
}

export default PaginaEntradas;
