package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Representa un fertilizante agrícola.
 * <p>
 * Cualquier fertilizante se puede agregar. Extiende {@link InsumoAgricola}
 * e implementa {@link Serializable} para permitir su persistencia.
 * Aplica un descuento del 5% en el costo total cuando el stock supera las
 * 100 unidades.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see InsumoAgricola
 */
public class Fertilizante extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Composición química del fertilizante. */
    private String composicion;

    /** Relación de nitrógeno, fósforo y potasio (NPK) del fertilizante. */
    private String npk;

    /** Modo de aplicación recomendado para el fertilizante. */
    private String modoAplicacion;

    /**
     * Construye un fertilizante con todos sus atributos.
     *
     * @param codigo           código único del fertilizante
     * @param nombre           nombre del fertilizante
     * @param precio           precio unitario
     * @param stock            cantidad disponible en inventario
     * @param proveedor        nombre del proveedor
     * @param fechaVencimiento fecha de vencimiento
     * @param composicion      composición química del fertilizante
     * @param npk              relación NPK del fertilizante
     * @param modoAplicacion   modo de aplicación recomendado
     */
    public Fertilizante(String codigo, String nombre, double precio, int stock,
                        String proveedor, String fechaVencimiento,
                        String composicion, String npk, String modoAplicacion) {
        super(codigo, nombre, precio, stock, proveedor, fechaVencimiento);
        this.composicion = composicion;
        this.npk = npk;
        this.modoAplicacion = modoAplicacion;
    }

    /**
     * Constructor por defecto. Crea un fertilizante sin inicializar atributos.
     */
    public Fertilizante() { super(); }

    /**
     * Retorna la composición química del fertilizante.
     *
     * @return composición del fertilizante
     */
    public String getComposicion() { return composicion; }

    /**
     * Retorna la relación NPK del fertilizante.
     *
     * @return valor NPK del fertilizante
     */
    public String getNpk() { return npk; }

    /**
     * Retorna el modo de aplicación del fertilizante.
     *
     * @return modo de aplicación recomendado
     */
    public String getModoAplicacion() { return modoAplicacion; }

    /**
     * Establece la composición química del fertilizante.
     *
     * @param composicion nueva composición del fertilizante
     */
    public void setComposicion(String composicion) { this.composicion = composicion; }

    /**
     * Establece la relación NPK del fertilizante.
     *
     * @param npk nuevo valor NPK
     */
    public void setNpk(String npk) { this.npk = npk; }

    /**
     * Establece el modo de aplicación del fertilizante.
     *
     * @param modoAplicacion nuevo modo de aplicación
     */
    public void setModoAplicacion(String modoAplicacion) { this.modoAplicacion = modoAplicacion; }

    /**
     * Retorna el tipo de insumo.
     *
     * @return la cadena {@code "Fertilizante"}
     */
    @Override
    public String tipoInsumo() { return "Fertilizante"; }

    /**
     * Calcula el costo total del fertilizante.
     * <p>
     * Si el stock es mayor a 100 unidades, aplica un descuento del 5%
     * sobre el total; de lo contrario calcula {@code precio * stock}.
     * </p>
     *
     * @return costo total con o sin descuento según el stock
     */
    @Override
    public double calcularCosto() {
        if (getStock() > 100) {
            return getPrecio() * getStock() * 0.95;
        }
        return getPrecio() * getStock();
    }

    /**
     * Retorna una descripción detallada del fertilizante con sus atributos principales.
     *
     * @return cadena descriptiva con nombre, NPK, modo de aplicación, stock y fecha de vencimiento
     */
    @Override
    public String descripcion() {
        return "[Fertilizante] " + getNombre() +
               " | NPK: " + npk +
               " | Aplicacion: " + modoAplicacion +
               " | Stock: " + getStock() +
               " | Vence: " + getFechaVencimiento();
    }

    /**
     * Retorna una representación en cadena del fertilizante con todos sus atributos.
     *
     * @return cadena con los datos del fertilizante y los heredados de {@link InsumoAgricola}
     */
    @Override
    public String toString() {
        return "Fertilizante{composicion='" + composicion + "', npk='" + npk +
               "', modoAplicacion='" + modoAplicacion + "', " + super.toString() + "}";
    }
}