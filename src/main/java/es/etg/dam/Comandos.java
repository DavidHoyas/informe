package es.etg.dam;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor

// Implementación de un comando del sistema
class Comandos implements ComandoSistema {
    private final String comando;
    private final String descripcion;

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

}
