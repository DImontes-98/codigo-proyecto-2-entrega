package co.edu.poli.insumoagricola.servicios;

import co.edu.poli.insumoagricola.model.InsumoAgricola;

/**
 * Interfaz que define las operaciones CRUD sobre objetos de tipo {@link InsumoAgricola}.
 * <p>
 * Cualquier clase que implemente esta interfaz deberá proveer la lógica
 * para crear, consultar, modificar, eliminar y listar insumos agrícolas.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see InsumoAgricola
 * @see ImplementacionOperacionCrud
 */
public interface OperacionCRUD {

    /**
     * Crea y almacena un nuevo insumo agrícola.
     *
     * @param i insumo agrícola a registrar
     * @return mensaje indicando el resultado de la operación
     */
    String crear(InsumoAgricola i);

    /**
     * Busca y retorna un insumo agrícola según su código.
     *
     * @param codigo código del insumo a consultar
     * @return el {@link InsumoAgricola} encontrado, o {@code null} si no existe
     */
    InsumoAgricola consultar(String codigo);

    /**
     * Reemplaza un insumo existente con la nueva información proporcionada.
     *
     * @param codigo código del insumo a modificar
     * @param i      nuevo objeto {@link InsumoAgricola} con la información actualizada
     * @return mensaje indicando el resultado de la operación
     */
    String modificar(String codigo, InsumoAgricola i);

    /**
     * Elimina el insumo agrícola asociado al código dado.
     *
     * @param codigo código del insumo a eliminar
     * @return mensaje indicando el resultado de la operación
     */
    String eliminar(String codigo);

    /**
     * Genera y retorna una cadena con el listado completo de los insumos registrados.
     *
     * @return cadena con el inventario actual de insumos agrícolas
     */
    String listar();
}