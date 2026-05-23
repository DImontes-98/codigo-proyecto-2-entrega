package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Representa una semilla agrícola.
 * <p>
 * Mostramos a la semilla como cualquier semilla que se quiera colocar. Extiende
 * {@link InsumoAgricola} e implementa {@link Serializable} para permitir su
 * persistencia. Aplica un descuento del 8% en el costo total cuando el stock
 * supera las 500 unidades.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see InsumoAgricola
 */
public class Semilla extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Composición de la semilla. */
    private String composicion;

    /** Relación de nitrógeno, fósforo y potasio (NPK) asociada a la semilla. */
    private String npk;

    /** Modo de siembra o aplicación recomendado. */
    private String modoAplicacion;

    /** Variedad o cultivar de la semilla. */
    private String variedad;

    /** Duración del ciclo de vida de la semilla en días. */
    private int cicloVida;

    /**
     * Construye una semilla con todos sus atributos.
     *
     * @param codigo           código único de la semilla
     * @param nombre           nombre de la semilla
     * @param precio           precio unitario
     * @param stock            cantidad disponible en inventario
     * @param proveedor        nombre del proveedor
     * @param fechaVencimiento fecha de vencimiento
     * @param composicion      composición de la semilla
     * @param npk              relación NPK asociada
     * @param modoAplicacion   modo de siembra o aplicación recomendado
     * @param variedad         variedad o cultivar de la semilla
     * @param cicloVida        duración del ciclo de vida en días
     */
    public Semilla(String codigo, String nombre, double precio, int stock,
                   String proveedor, String fechaVencimiento,
                   String composicion, String npk, String modoAplicacion,
                   String variedad, int cicloVida) {
        super(codigo, nombre, precio, stock, proveedor, fechaVencimiento);
        this.composicion = composicion;
        this.npk = npk;
        this.modoAplicacion = modoAplicacion;
        this.variedad = variedad;
        this.cicloVida = cicloVida;
    }

    /**
     * Constructor por defecto. Crea una semilla sin inicializar atributos.
     */
    public Semilla() { super(); }

    /**
     * Retorna la composición de la semilla.
     *
     * @return composición de la semilla
     */
    public String getComposicion() { return composicion; }

    /**
     * Retorna la relación NPK de la semilla.
     *
     * @return valor NPK de la semilla
     */
    public String getNpk() { return npk; }

    /**
     * Retorna el modo de siembra o aplicación de la semilla.
     *
     * @return modo de aplicación recomendado
     */
    public String getModoAplicacion() { return modoAplicacion; }

    /**
     * Retorna la variedad o cultivar de la semilla.
     *
     * @return variedad de la semilla
     */
    public String getVariedad() { return variedad; }

    /**
     * Retorna la duración del ciclo de vida de la semilla en días.
     *
     * @return ciclo de vida en días
     */
    public int getCicloVida() { return cicloVida; }

    /**
     * Establece la composición de la semilla.
     *
     * @param composicion nueva composición de la semilla
     */
    public void setComposicion(String composicion) { this.composicion = composicion; }

    /**
     * Establece la relación NPK de la semilla.
     *
     * @param npk nuevo valor NPK
     */
    public void setNpk(String npk) { this.npk = npk; }

    /**
     * Establece el modo de siembra o aplicación de la semilla.
     *
     * @param modoAplicacion nuevo modo de aplicación
     */
    public void setModoAplicacion(String modoAplicacion) { this.modoAplicacion = modoAplicacion; }

    /**
     * Establece la variedad o cultivar de la semilla.
     *
     * @param variedad nueva variedad de la semilla
     */
    public void setVariedad(String variedad) { this.variedad = variedad; }

    /**
     * Establece la duración del ciclo de vida de la semilla.
     *
     * @param cicloVida nuevo ciclo de vida en días
     */
    public void setCicloVida(int cicloVida) { this.cicloVida = cicloVida; }

    /**
     * Retorna el tipo de insumo.
     *
     * @return la cadena {@code "Semilla"}
     */
    @Override
    public String tipoInsumo() { return "Semilla"; }

    /**
     * Calcula el costo total de la semilla.
     * <p>
     * Si el stock es mayor a 500 unidades, aplica un descuento del 8%
     * sobre el total; de lo contrario calcula {@code precio * stock}.
     * </p>
     *
     * @return costo total con o sin descuento según el stock
     */
    @Override
    public double calcularCosto() {
        if (getStock() > 500) {
            return getPrecio() * getStock() * 0.92;
        }
        return getPrecio() * getStock();
    }

    /**
     * Retorna una descripción detallada de la semilla con sus atributos principales.
     *
     * @return cadena descriptiva con nombre, variedad, ciclo de vida,
     *         modo de siembra, stock y fecha de vencimiento
     */
    @Override
    public String descripcion() {
        return "[Semilla] " + getNombre() +
               " | Variedad: " + variedad +
               " | Ciclo: " + cicloVida + " dias" +
               " | Siembra: " + modoAplicacion +
               " | Stock: " + getStock() +
               " | Vence: " + getFechaVencimiento();
    }

    /**
     * Retorna una representación en cadena de la semilla con todos sus atributos.
     *
     * @return cadena con los datos de la semilla y los heredados de {@link InsumoAgricola}
     */
    @Override
    public String toString() {
        return "Semilla{composicion='" + composicion + "', npk='" + npk +
               "', modoAplicacion='" + modoAplicacion + "', variedad='" + variedad +
               "', cicloVida=" + cicloVida + ", " + super.toString() + "}";
    }
}