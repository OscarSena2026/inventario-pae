// Configuración de Vitest (pruebas): usa el plugin de React y simula el navegador con jsdom.
import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';
// globals: true permite usar describe/it/expect sin importarlos en cada prueba.
export default defineConfig({ plugins: [react()], test: { environment: 'jsdom', globals: true } });
