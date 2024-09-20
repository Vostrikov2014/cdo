import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    build: {
      sourcemap: true
    },
    proxy: {
      '/': {
        target: `http://localhost:8090`,
        changeOrigin: true,
        rewrite: (path) => path
      }
    }
  }
})
