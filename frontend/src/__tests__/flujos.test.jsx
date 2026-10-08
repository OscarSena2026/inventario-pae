/**
 * Pruebas de los flujos principales del front-end con la API simulada (mock).
 * Se ejecutan con:  npm test
 */
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MemoryRouter } from 'react-router-dom';
import App from '../App';
import * as api from '../services/api';

vi.mock('../services/api');

// Datos de ejemplo con la misma forma que devuelve la API.
const bodega = { idBodega: 1, nombreBodega: 'Bodega Central Montería' };
const proveedor = { idProveedor: 2, nombreProveedor: 'Distribuidora La Sabana' };
const empleado = { idEmpleado: 1, nombres: 'Laura Martínez', cargo: 'Bodeguera' };
const sede = { idSede: 1, nombreSede: 'IE San José' };
const producto = { idProducto: 1, nombreProducto: 'Arroz', categoria: 'Granos', unidadMedida: 'kg', perecedero: 'NO' };
const lote = { idLote: 1, fechaVencimiento: '2027-01-01', producto };
const existencia = { idExistencia: 1, cantidadDisponible: 50, bodega, lote };

// Dibuja la aplicación completa en la ruta indicada (sin navegador real).
const ir = (ruta) => render(<MemoryRouter initialEntries={[ruta]} future={{ v7_startTransition: true, v7_relativeSplatPath: true }}><App /></MemoryRouter>);

// Antes de cada prueba se reinician los mocks y se simulan las respuestas de la API (sin servidor real).
beforeEach(() => {
  vi.resetAllMocks();
  api.listarBodegas.mockResolvedValue([bodega]);
  api.listarProveedores.mockResolvedValue([proveedor]);
  api.listarEmpleados.mockResolvedValue([empleado]);
  api.listarSedes.mockResolvedValue([sede]);
  api.listarLotes.mockResolvedValue([lote]);
  api.listarProductos.mockResolvedValue([producto]);
  api.listarExistencias.mockResolvedValue([existencia]);
  api.listarExistenciasPorBodega.mockResolvedValue([existencia]);
});

describe('Sistema de Inventario PAE (front-end)', () => {
  it('HU-05: muestra el inventario y filtra por producto', async () => {
    const u = userEvent.setup();
    ir('/');
    expect(await screen.findByText('Bodega Central Montería', { selector: 'td' })).toBeTruthy();
    expect(screen.getByText(/50 kg/)).toBeTruthy();
    await u.type(screen.getByPlaceholderText('Buscar por nombre...'), 'zzz');
    expect(screen.getByText('No hay existencias para mostrar.')).toBeTruthy();
  });

  it('HU-02: valida campos obligatorios y registra un producto', async () => {
    const u = userEvent.setup();
    ir('/productos');
    await screen.findByText('Arroz');
    await u.click(screen.getByRole('button', { name: 'Registrar producto' }));
    expect(screen.getByText('El nombre del producto es obligatorio')).toBeTruthy();
    expect(api.crearProducto).not.toHaveBeenCalled();
    api.crearProducto.mockResolvedValue({ idProducto: 2 });
    await u.type(screen.getByLabelText('Nombre del producto'), 'Lentejas');
    await u.type(screen.getByLabelText('Unidad de medida'), 'kg');
    await u.click(screen.getByRole('button', { name: 'Registrar producto' }));
    await waitFor(() => expect(api.crearProducto).toHaveBeenCalledWith(
      { nombreProducto: 'Lentejas', categoria: '', unidadMedida: 'kg', perecedero: 'NO' }));
    expect(await screen.findByText('Producto registrado correctamente.')).toBeTruthy();
  });

  it('HU-02: muestra el error de producto duplicado que devuelve la API', async () => {
    const u = userEvent.setup();
    ir('/productos');
    await screen.findByText('Arroz');
    api.crearProducto.mockRejectedValue({ response: { status: 400, data: { detalles: "Ya existe un producto registrado con el nombre 'Arroz'" } } });
    await u.type(screen.getByLabelText('Nombre del producto'), 'Arroz');
    await u.type(screen.getByLabelText('Unidad de medida'), 'kg');
    await u.click(screen.getByRole('button', { name: 'Registrar producto' }));
    expect(await screen.findByText(/Ya existe un producto registrado/)).toBeTruthy();
  });

  it('HU-08: modifica un producto', async () => {
    const u = userEvent.setup();
    window.scrollTo = vi.fn();
    ir('/productos');
    await screen.findByText('Arroz');
    api.actualizarProducto.mockResolvedValue({});
    await u.click(screen.getByRole('button', { name: 'Editar' }));
    expect(screen.getByText('Modificar producto')).toBeTruthy();
    const campo = screen.getByLabelText('Unidad de medida');
    await u.clear(campo); await u.type(campo, 'libra');
    await u.click(screen.getByRole('button', { name: 'Guardar cambios' }));
    await waitFor(() => expect(api.actualizarProducto).toHaveBeenCalledWith(1,
      { nombreProducto: 'Arroz', categoria: 'Granos', unidadMedida: 'libra', perecedero: 'NO' }));
  });

  it('HU-03: registra una entrada y le agrega un detalle', async () => {
    const u = userEvent.setup();
    ir('/entradas');
    await screen.findByRole('option', { name: 'Bodega Central Montería' });
    api.crearEntrada.mockResolvedValue({ idEntrada: 7 });
    api.crearDetalleEntrada.mockResolvedValue({ idDetalleEntrada: 3 });
    await u.selectOptions(screen.getByLabelText('Bodega'), '1');
    await u.selectOptions(screen.getByLabelText('Proveedor'), '2');
    await u.selectOptions(screen.getByLabelText('Empleado responsable'), '1');
    await u.click(screen.getByRole('button', { name: 'Crear entrada' }));
    await waitFor(() => expect(api.crearEntrada).toHaveBeenCalled());
    const payload = api.crearEntrada.mock.calls[0][0];
    expect(payload.bodega).toEqual({ idBodega: 1 });
    expect(payload.proveedor).toEqual({ idProveedor: 2 });
    expect(payload.numeroFactura).toBeNull();
    expect(await screen.findByText(/Productos de la entrada #7/)).toBeTruthy();
    await u.selectOptions(screen.getByLabelText('Lote (producto)'), '1');
    await u.type(screen.getByLabelText('Cantidad'), '10');
    await u.type(screen.getByLabelText('Valor unitario ($)'), '2500');
    await u.click(screen.getByRole('button', { name: 'Agregar producto' }));
    await waitFor(() => expect(api.crearDetalleEntrada).toHaveBeenCalledWith(
      { entrada: { idEntrada: 7 }, lote: { idLote: 1 }, cantidad: 10, valorUnitario: 2500 }));
    expect(await screen.findByText(/25\.000/)).toBeTruthy();
  });

  it('HU-04: muestra «Stock insuficiente» y conserva los datos', async () => {
    const u = userEvent.setup();
    ir('/salidas');
    await screen.findByRole('option', { name: 'IE San José' });
    api.crearSalida.mockResolvedValue({ idSalida: 9 });
    await u.selectOptions(screen.getByLabelText('Bodega de origen'), '1');
    await u.selectOptions(screen.getByLabelText('Sede de destino'), '1');
    await u.selectOptions(screen.getByLabelText('Empleado responsable'), '1');
    await u.click(screen.getByRole('button', { name: 'Crear salida' }));
    expect(await screen.findByText(/Productos de la salida #9/)).toBeTruthy();
    api.crearDetalleSalida.mockRejectedValue({ response: { status: 400, data: { error: 'Regla de negocio violada', detalles: 'Stock insuficiente: no hay suficiente cantidad disponible de este lote en la bodega para despachar 999' } } });
    await u.selectOptions(screen.getByLabelText('Lote disponible'), '1');
    await u.type(screen.getByLabelText('Cantidad a despachar'), '999');
    await u.click(screen.getByRole('button', { name: 'Agregar producto' }));
    expect(await screen.findByText(/Stock insuficiente/)).toBeTruthy();
    expect(screen.getByLabelText('Cantidad a despachar').value).toBe('999');
  });

  it('muestra un aviso claro cuando la API no responde', async () => {
    api.listarProductos.mockRejectedValue(new Error('Network Error'));
    ir('/productos');
    expect(await screen.findByText(/No se pudo conectar con la API/)).toBeTruthy();
  });
});
