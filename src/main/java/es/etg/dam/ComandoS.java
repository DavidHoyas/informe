package es.etg.dam;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// Implementación de un comando del sistema
class ComandoS implements ComandoSistema {
    private final String comando;
    private final String descripcion;

    public ComandoS(String comando, String descripcion) {
        this.comando = comando;
        this.descripcion = descripcion;
    }

    // Sobrescribe el método de la interfaz para ejecutar el comando del sistema
    @Override
    public String ejecutar() throws Exception {
        // Ejecuta el comando del sistema
        Process proceso = Runtime.getRuntime().exec(comando);
        StringBuilder salida = new StringBuilder();

        // Lee la salida del comando
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                salida.append(linea).append("\n");
            }
        }

        proceso.waitFor();
        // Devuelve la salida como texto
        return salida.toString(); 
    }

    @Override
    public String getComando() {
        return comando;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
