// Configuración de Vite para el front-end del Sistema de Inventario PAE.
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  // Puerto de desarrollo. La API de Spring Boot debe permitirlo en su configuración CORS.
  server: { port: 5173 },
});
