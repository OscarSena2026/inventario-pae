import { Navigate, Route, Routes } from 'react-router-dom';
import Encabezado from './components/Encabezado';
import PiePagina from './components/PiePagina';
import PaginaProductos from './pages/PaginaProductos';
import PaginaEntradas from './pages/PaginaEntradas';
import PaginaSalidas from './pages/PaginaSalidas';
import PaginaInventario from './pages/PaginaInventario';

// Opciones del menú: una por cada pantalla del módulo.
const ENLACES = [
  { ruta: '/inventario', texto: 'Inventario' },
  { ruta: '/productos', texto: 'Productos' },
  { ruta: '/entradas', texto: 'Entradas' },
  { ruta: '/salidas', texto: 'Salidas' },
];

/**
 * App (componente raíz)
 * Define las rutas con React Router y ubica el encabezado, la página activa y el pie de página.
 */
function App() {
  return (
    <>
      <Encabezado enlaces={ENLACES} />
      <main className="container">
        <Routes>
          {/* La página de inicio es la consulta del inventario (HU-05) */}
          <Route path="/" element={<Navigate to="/inventario" replace />} />
          <Route path="/inventario" element={<PaginaInventario />} />
          <Route path="/productos" element={<PaginaProductos />} />
          <Route path="/entradas" element={<PaginaEntradas />} />
          <Route path="/salidas" element={<PaginaSalidas />} />
          <Route path="*" element={<p className="text-muted">La página que buscas no existe.</p>} />
        </Routes>
      </main>
      <PiePagina version="1.0.0" />
    </>
  );
}

export default App;
