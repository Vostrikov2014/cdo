import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    extensions: ['.js', '.jsx']  // Убедитесь, что Vite обрабатывает файлы .jsx
  },
  server: {
    port: 3000,
    build: {
      sourcemap: false
    }
  }
})
