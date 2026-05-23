package co.edu.poli.insumoagricola.servicios;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import co.edu.poli.insumoagricola.model.InsumoAgricola;

/**
 * Implementación de las interfaces {@link OperacionCRUD} y {@link OperacionArchivo}
 * para la gestión de objetos {@link InsumoAgricola}.
 * <p>
 * Administra dos estructuras de datos internas: un arreglo fijo de capacidad 2
 * y un {@link ArrayList} dinámico que actúa como desbordamiento cuando el arreglo
 * está lleno. Las operaciones de serialización y deserialización permiten persistir
 * el estado del arreglo fijo en archivos binarios.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see OperacionCRUD
 * @see OperacionArchivo
 * @see InsumoAgricola
 */
public class ImplementacionOperacionCrud implements OperacionCRUD, OperacionArchivo {

    /** Arreglo fijo con capacidad para 2 insumos agrícolas. */
    private InsumoAgricola[] insumo;

    /** Lista dinámica que almacena insumos cuando el arreglo fijo está lleno. */
    private ArrayList<InsumoAgricola> inventario;

    /**
     * Construye una nueva instancia con el arreglo fijo inicializado en tamaño 2
     * y el inventario dinámico vacío.
     */
    public ImplementacionOperacionCrud() {
        insumo = new InsumoAgricola[2];
        inventario = new ArrayList<>();
    }

    /**
     * Retorna el arreglo fijo interno para poder serializarlo desde Principal.
     *
     * @return arreglo de {@link InsumoAgricola} de capacidad fija
     */
    public InsumoAgricola[] getInsumo() {
        return insumo;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Valida que el insumo no sea nulo, que su código y nombre no estén vacíos,
     * que el precio no sea negativo y que no exista un insumo con el mismo código.
     * Si el arreglo fijo está lleno, el insumo se agrega al inventario dinámico.
     * </p>
     *
     * @param i insumo agrícola a registrar
     * @return mensaje con prefijo {@code "OK:"} si la operación fue exitosa,
     *         o con prefijo {@code "ERROR:"} si falló alguna validación
     */
    @Override
    public String crear(InsumoAgricola i) {
        if (i == null) {
            return "ERROR: No se puede insertar un insumo nulo.";
        }
        if (i.getCodigo() == null || i.getCodigo().trim().isEmpty()) {
            return "ERROR: El codigo del insumo no puede estar vacio.";
        }
        if (i.getNombre() == null || i.getNombre().trim().isEmpty()) {
            return "ERROR: El nombre del insumo no puede estar vacio.";
        }
        if (i.getPrecio() < 0) {
            return "ERROR: El precio no puede ser negativo.";
        }
        if (duplicado(i.getCodigo())) {
            return "ERROR: Ya existe un insumo con codigo " + i.getCodigo();
        }

        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] == null) {
                insumo[j] = i;
                return "OK: Insumo '" + i.getNombre() +
                       "' insertado en posicion " + j + " del arreglo.";
            }
        }

        expandirInventario(i);
        return "OK: Arreglo lleno. Insumo '" + i.getNombre() +
               "' agregado al inventario dinamico (ArrayList).";
    }

    /**
     * {@inheritDoc}
     * <p>
     * Busca primero en el arreglo fijo y luego en el inventario dinámico.
     * Imprime un mensaje de error en consola si el código es inválido o no se encuentra.
     * </p>
     *
     * @param codigo código del insumo a consultar
     * @return el {@link InsumoAgricola} encontrado, o {@code null} si no existe
     */
    @Override
    public InsumoAgricola consultar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.out.println("ERROR consultar: El codigo no puede estar vacio.");
            return null;
        }

        for (InsumoAgricola a : insumo) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return a;
        }

        for (InsumoAgricola a : inventario) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return a;
        }

        System.out.println("ERROR consultar: No existe insumo con codigo " + codigo);
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Valida que el código y el nuevo insumo no sean nulos ni inválidos.
     * Busca el insumo primero en el arreglo fijo y luego en el inventario dinámico.
     * </p>
     *
     * @param codigo      código del insumo a modificar
     * @param nuevoInsumo nuevo objeto {@link InsumoAgricola} con la información actualizada
     * @return mensaje con prefijo {@code "OK:"} si la operación fue exitosa,
     *         o con prefijo {@code "ERROR"} si falló alguna validación o no se encontró el insumo
     */
    @Override
    public String modificar(String codigo, InsumoAgricola nuevoInsumo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "ERROR modificar: El codigo no puede estar vacio.";
        }
        if (nuevoInsumo == null) {
            return "ERROR modificar: El nuevo insumo no puede ser nulo.";
        }
        if (nuevoInsumo.getNombre() == null || nuevoInsumo.getNombre().trim().isEmpty()) {
            return "ERROR modificar: El nombre del nuevo insumo no puede estar vacio.";
        }
        if (nuevoInsumo.getPrecio() < 0) {
            return "ERROR modificar: El precio no puede ser negativo.";
        }

        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] != null && insumo[j].getCodigo().equalsIgnoreCase(codigo)) {
                insumo[j] = nuevoInsumo;
                return "OK: Insumo con codigo " + codigo +
                       " modificado en posicion " + j + " del arreglo.";
            }
        }

        for (int j = 0; j < inventario.size(); j++) {
            if (inventario.get(j) != null &&
                inventario.get(j).getCodigo().equalsIgnoreCase(codigo)) {
                inventario.set(j, nuevoInsumo);
                return "OK: Insumo con codigo " + codigo +
                       " modificado en posicion " + j + " del inventario dinamico.";
            }
        }

        return "ERROR modificar: No existe insumo con codigo " + codigo;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Establece la posición del arreglo fijo en {@code null} al eliminar,
     * o remueve el elemento del inventario dinámico según corresponda.
     * </p>
     *
     * @param codigo código del insumo a eliminar
     * @return mensaje con prefijo {@code "OK:"} si la operación fue exitosa,
     *         o con prefijo {@code "ERROR"} si el código es inválido o no se encontró el insumo
     */
    @Override
    public String eliminar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "ERROR eliminar: El codigo no puede estar vacio.";
        }

        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] != null && insumo[j].getCodigo().equalsIgnoreCase(codigo)) {
                String nombre = insumo[j].getNombre();
                insumo[j] = null;
                return "OK: Insumo '" + nombre + "' (codigo=" + codigo +
                       ") eliminado de posicion " + j + " del arreglo.";
            }
        }

        for (int j = 0; j < inventario.size(); j++) {
            if (inventario.get(j) != null &&
                inventario.get(j).getCodigo().equalsIgnoreCase(codigo)) {
                String nombre = inventario.get(j).getNombre();
                inventario.remove(j);
                return "OK: Insumo '" + nombre + "' (codigo=" + codigo +
                       ") eliminado del inventario dinamico.";
            }
        }

        return "ERROR eliminar: No existe insumo con codigo " + codigo;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Genera una cadena con el contenido del arreglo fijo y del inventario dinámico,
     * indicando las posiciones vacías del arreglo y la cantidad de elementos en el
     * {@link ArrayList}.
     * </p>
     *
     * @return cadena con el inventario completo de insumos agrícolas
     */
    @Override
    public String listar() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INVENTARIO DE INSUMOS AGRICOLAS - FINCA EL NARANJO ===\n");
        sb.append("-- Arreglo fijo (capacidad 2) --\n");

        boolean hayAlgo = false;
        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] != null) {
                sb.append("  [").append(j).append("] ")
                  .append(insumo[j].descripcion()).append("\n");
                hayAlgo = true;
            } else {
                sb.append("  [").append(j).append("] (vacio)\n");
            }
        }

        sb.append("-- Inventario dinamico (ArrayList: ")
          .append(inventario.size()).append(" elementos) --\n");

        if (inventario.isEmpty()) {
            sb.append("  (sin insumos adicionales)\n");
        } else {
            for (int j = 0; j < inventario.size(); j++) {
                sb.append("  [").append(j).append("] ")
                  .append(inventario.get(j).descripcion()).append("\n");
                hayAlgo = true;
            }
        }

        if (!hayAlgo) {
            sb.append("  (sin insumos registrados)\n");
        }

        sb.append("==========================================================");
        return sb.toString();
    }

    /**
     * Agrega un insumo agrícola al inventario dinámico ({@link ArrayList}).
     * <p>
     * Se invoca automáticamente desde {@link #crear(InsumoAgricola)} cuando
     * el arreglo fijo ya está lleno.
     * </p>
     *
     * @param i insumo agrícola a agregar al inventario dinámico
     */
    public void expandirInventario(InsumoAgricola i) {
        inventario.add(i);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Utiliza serialización de objetos Java ({@link ObjectOutputStream}) para
     * escribir el arreglo en un archivo binario en la ruta y nombre especificados.
     * </p>
     *
     * @param insumos arreglo de {@link InsumoAgricola} a persistir
     * @param path    ruta del directorio donde se guardará el archivo
     * @param name    nombre del archivo de salida
     * @return mensaje con prefijo {@code "OK:"} indicando la ruta del archivo creado,
     *         o con prefijo {@code "ERROR al serializar:"} si ocurre una excepción
     */
    @Override
    public String serializar(InsumoAgricola[] insumos, String path, String name) {
        try (FileOutputStream fos = new FileOutputStream(path + name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(insumos);
            return "OK: Archivo creado en " + path + name;
        } catch (IOException e) {
            return "ERROR al serializar: " + e.getMessage();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Utiliza deserialización de objetos Java ({@link ObjectInputStream}) para
     * recuperar el arreglo desde el archivo binario. El arreglo interno
     * {@code insumo} se actualiza con los datos recuperados.
     * </p>
     *
     * @param path ruta del directorio donde se encuentra el archivo
     * @param name nombre del archivo a leer
     * @return arreglo de {@link InsumoAgricola} recuperado del archivo,
     *         o {@code null} si ocurre un {@link IOException} o {@link ClassNotFoundException}
     */
    @Override
    public InsumoAgricola[] deserializar(String path, String name) {
        try (FileInputStream fis = new FileInputStream(path + name);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            InsumoAgricola[] recuperado = (InsumoAgricola[]) ois.readObject();
            insumo = recuperado;
            return recuperado;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR al deserializar: " + e.getMessage());
            return null;
        }
    }

    /**
     * Verifica si ya existe un insumo con el código indicado en alguna de las
     * dos estructuras de datos internas.
     * <p>
     * La comparación es insensible a mayúsculas y minúsculas.
     * </p>
     *
     * @param codigo código a verificar
     * @return {@code true} si ya existe un insumo con ese código; {@code false} en caso contrario
     */
    private boolean duplicado(String codigo) {
        for (InsumoAgricola a : insumo) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        for (InsumoAgricola a : inventario) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        return false;
    }
}