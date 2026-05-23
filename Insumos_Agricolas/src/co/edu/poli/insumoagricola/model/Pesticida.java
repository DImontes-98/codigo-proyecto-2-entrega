package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Representa un pesticida agrícola.
 * <p>
 * Acá tenemos cualquier pesticida que queramos agregar. Extiende {@link InsumoAgricola}
 * e implementa {@link Serializable} para permitir su persistencia.
 * Aplica un recargo del 10% en el costo total cuando la toxicidad es alta.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see InsumoAgricola
 */
public class Pesticida extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tipo de plagas que combate el pesticida. */
    private String tipoPlagas;

    /** Nivel de toxicidad del pesticida (por ejemplo: Alta, Media, Baja). */
    private String toxicidad;

    /** Modo de aplicación recomendado para el pesticida. */
    private String modoAplicacion;

    /**
     * Construye un pesticida con todos sus atributos.
     *
     * @param codigo           código único del pesticida
     * @param nombre           nombre del pesticida
     * @param precio           precio unitario
     * @param stock            cantidad disponible en inventario
     * @param proveedor        nombre del proveedor
     * @param fechaVencimiento fecha de vencimiento
     * @param tipoPlagas       tipo de plagas que combate
     * @param toxicidad        nivel de toxicidad del pesticida
     * @param modoAplicacion   modo de aplicación recomendado
     */
    public Pesticida(String codigo, String nombre, double precio, int stock,
                     String proveedor, String fechaVencimiento,
                     String tipoPlagas, String toxicidad, String modoAplicacion) {
        super(codigo, nombre, precio, stock, proveedor, fechaVencimiento);
        this.tipoPlagas = tipoPlagas;
        this.toxicidad = toxicidad;
        this.modoAplicacion = modoAplicacion;
    }

    /**
     * Constructor por defecto. Crea un pesticida sin inicializar atributos.
     */
    public Pesticida() { super(); }

    /**
     * Retorna el tipo de plagas que combate el pesticida.
     *
     * @return tipo de plagas objetivo
     */
    public String getTipoPlagas() { return tipoPlagas; }

    /**
     * Retorna el nivel de toxicidad del pesticida.
     *
     * @return nivel de toxicidad
     */
    public String getToxicidad() { return toxicidad; }

    /**
     * Retorna el modo de aplicación del pesticida.
     *
     * @return modo de aplicación recomendado
     */
    public String getModoAplicacion() { return modoAplicacion; }

    /**
     * Establece el tipo de plagas que combate el pesticida.
     *
     * @param tipoPlagas nuevo tipo de plagas objetivo
     */
    public void setTipoPlagas(String tipoPlagas) { this.tipoPlagas = tipoPlagas; }

    /**
     * Establece el nivel de toxicidad del pesticida.
     *
     * @param toxicidad nuevo nivel de toxicidad
     */
    public void setToxicidad(String toxicidad) { this.toxicidad = toxicidad; }

    /**
     * Establece el modo de aplicación del pesticida.
     *
     * @param modoAplicacion nuevo modo de aplicación
     */
    public void setModoAplicacion(String modoAplicacion) { this.modoAplicacion = modoAplicacion; }

    /**
     * Retorna el tipo de insumo.
     *
     * @return la cadena {@code "Pesticida"}
     */
    @Override
    public String tipoInsumo() { return "Pesticida"; }

    /**
     * Calcula el costo total del pesticida.
     * <p>
     * Si la toxicidad es {@code "Alta"} (sin distinción de mayúsculas/minúsculas),
     * aplica un recargo del 10% sobre el total; de lo contrario calcula
     * {@code precio * stock}.
     * </p>
     *
     * @return costo total con o sin recargo según el nivel de toxicidad
     */
    @Override
    public double calcularCosto() {
        if ("Alta".equalsIgnoreCase(toxicidad)) {
            return getPrecio() * getStock() * 1.10;
        }
        return getPrecio() * getStock();
    }

    /**
     * Retorna una descripción detallada del pesticida con sus atributos principales.
     *
     * @return cadena descriptiva con nombre, tipo de plagas, toxicidad,
     *         modo de aplicación, stock y fecha de vencimiento
     */
    @Override
    public String descripcion() {
        return "[Pesticida] " + getNombre() +
               " | Plagas: " + tipoPlagas +
               " | Toxicidad: " + toxicidad +
               " | Aplicacion: " + modoAplicacion +
               " | Stock: " + getStock() +
               " | Vence: " + getFechaVencimiento();
    }

    /**
     * Retorna una representación en cadena del pesticida con todos sus atributos.
     *
     * @return cadena con los datos del pesticida y los heredados de {@link InsumoAgricola}
     */
    @Override
    public String toString() {
        return "Pesticida{tipoPlagas='" + tipoPlagas + "', toxicidad='" + toxicidad +
               "', modoAplicacion='" + modoAplicacion + "', " + super.toString() + "}";
    }
}