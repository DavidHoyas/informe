package es.etg.dam;

// Interfaz que define el comportamiento de un comando del sistema
public interface ComandoSistema {
    String ejecutar() throws Exception;
    String getComando();
    String getDescripcion();

}
