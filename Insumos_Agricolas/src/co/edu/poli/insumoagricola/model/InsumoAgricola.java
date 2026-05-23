package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Clase abstracta base que representa un insumo agrícola genérico.
 * <p>
 * Esta clase la dejamos abstracta para que todas nuestras subclases tuvieran
 * siempre los métodos que consideramos necesarios. Implementa {@link Serializable}
 * para permitir la persistencia de sus instancias en archivos binarios.
 * </p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 * @see Fertilizante
 * @see Pesticida
 * @see Semilla
 * @see Herramienta
 */
public abstract class InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Código único que identifica el insumo. */
    private String codigo;

    /** Nombre descriptivo del insumo. */
    private String nombre;

    /** Precio unitario del insumo. */
    private double precio;

    /** Cantidad disponible en inventario. */
    private int stock;

    /** Nombre del proveedor que suministra el insumo. */
    private String proveedor;

    /** Fecha de vencimiento del insumo en formato de cadena. */
    private String fechaVencimiento;

    /**
     * Construye un nuevo insumo agrícola con todos sus atributos.
     *
     * @param codigo          código único del insumo
     * @param nombre          nombre del insumo
     * @param precio          precio unitario del insumo
     * @param stock           cantidad disponible en inventario
     * @param proveedor       nombre del proveedor
     * @param fechaVencimiento fecha de vencimiento del insumo
     */
    public InsumoAgricola(String codigo, String nombre, double precio,
                          int stock, String proveedor, String fechaVencimiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Constructor por defecto. Crea un insumo agrícola sin inicializar atributos.
     */
    public InsumoAgricola() {}

    /**
     * Retorna el tipo de insumo agrícola.
     * <p>
     * Cada subclase debe implementar este método para identificar
     * su categoría específica (Fertilizante, Semilla, Pesticida, etc.).
     * </p>
     *
     * @return cadena que representa el tipo de insumo
     */
    public abstract String tipoInsumo();

    /**
     * Retorna una descripción detallada del insumo agrícola.
     * <p>
     * Cada subclase debe implementar este método para generar
     * una descripción con sus atributos particulares.
     * </p>
     *
     * @return cadena con la descripción completa del insumo
     */
    public abstract String descripcion();

    /**
     * Retorna el código único del insumo.
     *
     * @return código del insumo
     */
    public String getCodigo() { return codigo; }

    /**
     * Retorna el nombre del insumo.
     *
     * @return nombre del insumo
     */
    public String getNombre() { return nombre; }

    /**
     * Retorna el precio unitario del insumo.
     *
     * @return precio del insumo
     */
    public double getPrecio() { return precio; }

    /**
     * Retorna la cantidad disponible en inventario.
     *
     * @return stock actual del insumo
     */
    public int getStock() { return stock; }

    /**
     * Retorna el nombre del proveedor del insumo.
     *
     * @return proveedor del insumo
     */
    public String getProveedor() { return proveedor; }

    /**
     * Retorna la fecha de vencimiento del insumo.
     *
     * @return fecha de vencimiento como cadena
     */
    public String getFechaVencimiento() { return fechaVencimiento; }

    /**
     * Establece el código del insumo.
     *
     * @param codigo nuevo código del insumo
     */
    public void setCodigo(String codigo) { this.codigo = codigo; }

    /**
     * Establece el nombre del insumo.
     *
     * @param nombre nuevo nombre del insumo
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Establece el precio unitario del insumo.
     *
     * @param precio nuevo precio del insumo
     */
    public void setPrecio(double precio) { this.precio = precio; }

    /**
     * Establece la cantidad disponible en inventario.
     *
     * @param stock nuevo stock del insumo
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * Establece el proveedor del insumo.
     *
     * @param proveedor nuevo proveedor del insumo
     */
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    /**
     * Establece la fecha de vencimiento del insumo.
     *
     * @param fechaVencimiento nueva fecha de vencimiento
     */
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    /**
     * Calcula el costo total del insumo multiplicando precio por stock.
     * <p>
     * Las subclases pueden sobreescribir este método para aplicar
     * descuentos o recargos según sus propias reglas de negocio.
     * </p>
     *
     * @return costo total calculado como {@code precio * stock}
     */
    public double calcularCosto() { return precio * stock; }

    /**
     * Valida que un código no sea nulo ni esté vacío.
     *
     * @param c código a validar
     * @return {@code true} si el código es válido; {@code false} en caso contrario
     */
    public boolean validarCodigo(String c) { return c != null && !c.trim().isEmpty(); }

    /**
     * Retorna una representación en cadena del insumo agrícola con todos sus atributos.
     *
     * @return cadena con los datos del insumo
     */
    @Override
    public String toString() {
        return "InsumoAgricola{codigo='" + codigo + "', nombre='" + nombre +
               "', precio=" + precio + ", stock=" + stock +
               ", proveedor='" + proveedor + "', fechaVencimiento='" + fechaVencimiento + "'}";
    }
}