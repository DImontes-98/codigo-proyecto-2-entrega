package co.edu.poli.insumoagricola.servicios;

import co.edu.poli.insumoagricola.model.InsumoAgricola;

/**
 * Aca se define una de las operaciones para InsumoAgricola.
 */
public interface OperacionArchivo {

    String serializar(InsumoAgricola[] insumos, String path, String name);

    InsumoAgricola[] deserializar(String path, String name);
}
