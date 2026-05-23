package co.edu.poli.insumoagricola.servicios;

import co.edu.poli.insumoagricola.model.InsumoAgricola;

/**
 * Interfaz que define las operaciones de persistencia en archivo
 * para objetos de tipo {@link InsumoAgricola}.
 * <p>
 * Acá se define una de las operaciones para {@link InsumoAgricola}.
 * Cualquier clase que implemente esta interfaz deberá proveer la lógica
 * para serializar y deserializar arreglos de insumos agrícolas.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see InsumoAgricola
 * @see ImplementacionOperacionCrud
 */
public interface OperacionArchivo {

    /**
     * Serializa un arreglo de insumos agrícolas en un archivo binario.
     *
     * @param insumos arreglo de {@link InsumoAgricola} a persistir
     * @param path    ruta del directorio donde se guardará el archivo
     * @param name    nombre del archivo de salida
     * @return mensaje indicando el resultado de la operación
     */
    String serializar(InsumoAgricola[] insumos, String path, String name);

    /**
     * Deserializa un arreglo de insumos agrícolas desde un archivo binario.
     *
     * @param path ruta del directorio donde se encuentra el archivo
     * @param name nombre del archivo a leer
     * @return arreglo de {@link InsumoAgricola} recuperado del archivo,
     *         o {@code null} si ocurre un error
     */
    InsumoAgricola[] deserializar(String path, String name);
}