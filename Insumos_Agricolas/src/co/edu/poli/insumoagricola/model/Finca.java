package co.edu.poli.insumoagricola.model;

/**
 * Representa una finca agrícola.
 * <p>
 * La finca es opcional, más sin embargo la agregamos igualmente. Permite
 * calcular el valor total del inventario de insumos y contar insumos
 * por tipo dentro de un arreglo dado.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see InsumoAgricola
 */
public class Finca {

    /** Nombre de la finca. */
    private String nombre;

    /** Ubicación geográfica de la finca. */
    private String ubicacion;

    /**
     * Construye una finca con su nombre y ubicación.
     *
     * @param nombre    nombre de la finca
     * @param ubicacion ubicación geográfica de la finca
     */
    public Finca(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    /**
     * Retorna el nombre de la finca.
     *
     * @return nombre de la finca
     */
    public String getNombre() { return nombre; }

    /**
     * Retorna la ubicación de la finca.
     *
     * @return ubicación geográfica de la finca
     */
    public String getUbicacion() { return ubicacion; }

    /**
     * Establece el nombre de la finca.
     *
     * @param nombre nuevo nombre de la finca
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Establece la ubicación de la finca.
     *
     * @param ubicacion nueva ubicación geográfica de la finca
     */
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    /**
     * Calcula el valor total del inventario sumando el costo de cada insumo.
     * <p>
     * Recorre el arreglo de insumos e ignora las posiciones nulas.
     * </p>
     *
     * @param insumos arreglo de {@link InsumoAgricola} que conforma el inventario
     * @return valor total del inventario como la suma de {@link InsumoAgricola#calcularCosto()}
     *         de cada insumo no nulo
     */
    public double calcularTotalInventario(InsumoAgricola[] insumos) {
        double total = 0;
        for (InsumoAgricola i : insumos) {
            if (i != null) total += i.calcularCosto();
        }
        return total;
    }

    /**
     * Cuenta cuántos insumos del arreglo pertenecen a un tipo específico.
     * <p>
     * La comparación del tipo es insensible a mayúsculas y minúsculas.
     * Las posiciones nulas del arreglo se ignoran.
     * </p>
     *
     * @param insumos arreglo de {@link InsumoAgricola} donde se realizará el conteo
     * @param tipo    tipo de insumo a buscar (por ejemplo: {@code "Fertilizante"}, {@code "Semilla"})
     * @return cantidad de insumos no nulos cuyo tipo coincide con el parámetro dado
     */
    public int contarInsumosPorTipo(InsumoAgricola[] insumos, String tipo) {
        int contador = 0;
        for (InsumoAgricola i : insumos) {
            if (i != null && i.tipoInsumo().equalsIgnoreCase(tipo)) contador++;
        }
        return contador;
    }

    /**
     * Retorna una representación en cadena de la finca con su nombre y ubicación.
     *
     * @return cadena con los datos de la finca
     */
    @Override
    public String toString() {
        return "Finca{nombre='" + nombre + "', ubicacion='" + ubicacion + "'}";
    }
}