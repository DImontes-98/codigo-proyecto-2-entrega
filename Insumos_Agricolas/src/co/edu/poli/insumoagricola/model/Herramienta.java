package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Representa una herramienta de uso agrícola.
 * <p>
 * Herramientas que pueden servir en el ámbito agrícola. Extiende {@link InsumoAgricola}
 * e implementa {@link Serializable} para permitir su persistencia.
 * El costo total se calcula directamente como {@code precio * stock} sin descuentos
 * ni recargos adicionales.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see InsumoAgricola
 */
public class Herramienta extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Material del que está fabricada la herramienta. */
    private String material;

    /** Vida útil estimada de la herramienta en años. */
    private int vidaUtil;

    /** Marca o fabricante de la herramienta. */
    private String marca;

    /**
     * Construye una herramienta con todos sus atributos.
     *
     * @param codigo           código único de la herramienta
     * @param nombre           nombre de la herramienta
     * @param precio           precio unitario
     * @param stock            cantidad disponible en inventario
     * @param proveedor        nombre del proveedor
     * @param fechaVencimiento fecha de vencimiento
     * @param material         material de fabricación de la herramienta
     * @param vidaUtil         vida útil estimada en años
     * @param marca            marca o fabricante de la herramienta
     */
    public Herramienta(String codigo, String nombre, double precio, int stock,
                       String proveedor, String fechaVencimiento,
                       String material, int vidaUtil, String marca) {
        super(codigo, nombre, precio, stock, proveedor, fechaVencimiento);
        this.material = material;
        this.vidaUtil = vidaUtil;
        this.marca = marca;
    }

    /**
     * Constructor por defecto. Crea una herramienta sin inicializar atributos.
     */
    public Herramienta() { super(); }

    /**
     * Retorna el material de fabricación de la herramienta.
     *
     * @return material de la herramienta
     */
    public String getMaterial() { return material; }

    /**
     * Retorna la vida útil estimada de la herramienta en años.
     *
     * @return vida útil en años
     */
    public int getVidaUtil() { return vidaUtil; }

    /**
     * Retorna la marca o fabricante de la herramienta.
     *
     * @return marca de la herramienta
     */
    public String getMarca() { return marca; }

    /**
     * Establece el material de fabricación de la herramienta.
     *
     * @param material nuevo material de la herramienta
     */
    public void setMaterial(String material) { this.material = material; }

    /**
     * Establece la vida útil estimada de la herramienta.
     *
     * @param vidaUtil nueva vida útil en años
     */
    public void setVidaUtil(int vidaUtil) { this.vidaUtil = vidaUtil; }

    /**
     * Establece la marca o fabricante de la herramienta.
     *
     * @param marca nueva marca de la herramienta
     */
    public void setMarca(String marca) { this.marca = marca; }

    /**
     * Retorna el tipo de insumo.
     *
     * @return la cadena {@code "Herramienta"}
     */
    @Override
    public String tipoInsumo() { return "Herramienta"; }

    /**
     * Calcula el costo total de la herramienta.
     * <p>
     * El costo se calcula como {@code precio * stock}, sin descuentos ni recargos.
     * </p>
     *
     * @return costo total calculado como {@code precio * stock}
     */
    @Override
    public double calcularCosto() { return getPrecio() * getStock(); }

    /**
     * Retorna una descripción detallada de la herramienta con sus atributos principales.
     *
     * @return cadena descriptiva con nombre, material, vida útil, marca y stock
     */
    @Override
    public String descripcion() {
        return "[Herramienta] " + getNombre() +
               " | Material: " + material +
               " | Vida util: " + vidaUtil + " anios" +
               " | Marca: " + marca +
               " | Stock: " + getStock();
    }

    /**
     * Retorna una representación en cadena de la herramienta con todos sus atributos.
     *
     * @return cadena con los datos de la herramienta y los heredados de {@link InsumoAgricola}
     */
    @Override
    public String toString() {
        return "Herramienta{material='" + material + "', vidaUtil=" + vidaUtil +
               ", marca='" + marca + "', " + super.toString() + "}";
    }
}