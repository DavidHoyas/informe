package es.etg.dam;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Informe {

    public static void main(String[] args) throws Exception {

        File md = new File("informe.md");

        // Lista de comandos a ejecutar
        List<ComandoSistema> comandos = new ArrayList<>();
        comandos.add(new Comandos("ps", "Procesos del sistema"));
        comandos.add(new Comandos("df", "Espacio en disco"));
        comandos.add(new Comandos("free", "Memoria disponible"));

        // Construye el contenido del informe
        StringBuilder informe = new StringBuilder();
        informe.append("# Informe del sistema\n\n");

        // Ejecuta cada comando y agrega su salida al informe
        for (ComandoSistema comando : comandos) {
            informe.append("## ").append(comando.getDescripcion())
                   .append(" (").append(comando.getComando()).append(")\n");
            informe.append("```\n").append(comando.ejecutar()).append("```\n\n");
        }

        // Escribe el informe en el archivo Markdown
        try (FileWriter writer = new FileWriter(md)) {
            writer.write(informe.toString());
            System.out.println("Informe generado en: " + md.getPath());
        }
    }
}
