package co.edu.poli.insumoagricola.servicios;

import co.edu.poli.insumoagricola.model.InsumoAgricola;

/**
 * Interfaz que define las operaciones CRUD sobre objetos de tipo InsumoAgricola.
 */
public interface OperacionCRUD {

    String crear(InsumoAgricola i);

    InsumoAgricola consultar(String codigo);

    String modificar(String codigo, InsumoAgricola i);

    String eliminar(String codigo);

    String listar();
}