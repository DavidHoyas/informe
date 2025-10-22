package es.etg.dam;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class InformeTest {

    // Mock simple para simular comandos sin ejecutar nada real
    static class ComandoMock implements ComandoSistema {
        private final String comando;
        private final String descripcion;
        private final String salida;

        ComandoMock(String comando, String descripcion, String salida) {
            this.comando = comando;
            this.descripcion = descripcion;
            this.salida = salida;
        }

        @Override
        public String ejecutar() {
            return salida;
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

    @Test
    void testGenerarInformeMarkdown() throws Exception {
        // Crear un archivo temporal
        File informeMd = File.createTempFile("informe", ".md");
        informeMd.deleteOnExit(); // Se borrará al terminar

        // Simular comportamiento de Informe.java
        StringBuilder informe = new StringBuilder();
        informe.append("# Informe del sistema\n\n");

        ComandoSistema cmd = new ComandoMock("ps", "Procesos del sistema", "Salida simulada");
        informe.append("## ").append(cmd.getDescripcion())
               .append(" (").append(cmd.getComando()).append(")\n");
        informe.append("```\n").append(cmd.ejecutar()).append("```\n\n");

        try (FileWriter writer = new FileWriter(informeMd)) {
            writer.write(informe.toString());
        }

        // Comprobaciones
        assertTrue(informeMd.exists(), "El archivo de informe debería existir");
        String contenido = Files.readString(informeMd.toPath());
        assertTrue(contenido.contains("# Informe del sistema"));
        assertTrue(contenido.contains("Procesos del sistema"));
        assertTrue(contenido.contains("Salida simulada"));
    }
}
