package co.edu.poli.insumoagricola.servicios;

import co.edu.poli.insumoagricola.model.InsumoAgricola;

/**
 * Interfaz que define las operaciones de persistencia de objetos InsumoAgricola.
 */
public interface OperacionArchivo {

    String serializar(InsumoAgricola[] insumos, String path, String name);

    InsumoAgricola[] deserializar(String path, String name);
}
