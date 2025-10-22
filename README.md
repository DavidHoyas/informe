# Informe

## Introducción

Este proyecto ejecuta comandos del sistema y captura su salida para generar un informe en formato Markdown. Este informe incluye:

- Nombre del comando.

- Descripción del comando ejecutado.

- Salida del comando en un bloque de código.

---

## Estructura del proyecto

```
- informe
  - src
    - main
      - java
        - es/etg/dam
          - Comandos.java
          - ComandoSistema.java
          - Informe.java
    - test
      - java
        - es/etg/dam
          - InformeTest.java
  - .gitignore
  - informe.md
  - pom.xml
  - README.md
```

---

## Comandos.java

``` java
// Sobrescribe el método de la interfaz para ejecutar el comando del sistema
@Override
public String ejecutar() throws Exception {
    Process proceso = Runtime.getRuntime().exec(comando);
    StringBuilder salida = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            salida.append(linea).append("\n");
        }
    }

    proceso.waitFor();
    return salida.toString(); 
}
```

---

## ComandoSistema.java

``` java
// Interfaz que define el comportamiento de un comando del sistema
public interface ComandoSistema {
    String ejecutar() throws Exception;
    String getComando();
    String getDescripcion();

}
```

---

## Informe.java

``` java
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
```

---

## Test

### InformeTest.java

``` java
// Simulación de comandos sin ejecutar nada real
@Data
@AllArgsConstructor
static class ComandoTest implements ComandoSistema {
    private final String comando;
    private final String descripcion;
    private final String salida;

    @Override
    public String ejecutar() {
        return salida;
    }
}
```

---

``` java
@Test
void testGenerarInformeMarkdown() throws Exception {
    // Crear un archivo temporal
    File informeMd = File.createTempFile("informe", ".md");
    informeMd.deleteOnExit(); // Se borrará al terminar

    // Simular comportamiento de Informe.java
    StringBuilder informe = new StringBuilder();
    informe.append("# Informe del sistema\n\n");

    ComandoSistema cmd = new ComandoTest("ps", "Procesos del sistema", "Salida simulada");
    informe.append("## ").append(cmd.getDescripcion())
        .append(" (").append(cmd.getComando()).append(")\n");
    informe.append("```\n").append(cmd.ejecutar()).append("```\n\n");

    try (FileWriter writer = new FileWriter(informeMd)) {
        writer.write(informe.toString());
    }

    [...]

}
```

---

``` java
// Comprobaciones
assertTrue(informeMd.exists(), "El archivo de informe debería existir");
String contenido = Files.readString(informeMd.toPath());
assertTrue(contenido.contains("# Informe del sistema"));
assertTrue(contenido.contains("Procesos del sistema"));
assertTrue(contenido.contains("Salida simulada"));
```

---

## Tecnologías utilizadas

- Java 17
- JUnit 5
- Lombok
- Comandos del sistema `ps`, `df` y `free`