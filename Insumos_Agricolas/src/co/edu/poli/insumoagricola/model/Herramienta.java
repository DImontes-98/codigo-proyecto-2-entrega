package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Herramientas que pueden servir en el ambito agricola
 */
public class Herramienta extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    private String material;
    private int vidaUtil;
    private String marca;

    public Herramienta(String codigo, String nombre, double precio, int stock,
                       String proveedor, String fechaVencimiento,
                       String material, int vidaUtil, String marca) {
        super(codigo, nombre, precio, stock, proveedor, fechaVencimiento);
        this.material = material;
        this.vidaUtil = vidaUtil;
        this.marca = marca;
    }

    public Herramienta() { super(); }

    public String getMaterial() { return material; }
    public int getVidaUtil() { return vidaUtil; }
    public String getMarca() { return marca; }

    public void setMaterial(String material) { this.material = material; }
    public void setVidaUtil(int vidaUtil) { this.vidaUtil = vidaUtil; }
    public void setMarca(String marca) { this.marca = marca; }

    @Override
    public String tipoInsumo() { return "Herramienta"; }

    @Override
    public double calcularCosto() { return getPrecio() * getStock(); }

    @Override
    public String descripcion() {
        return "[Herramienta] " + getNombre() +
               " | Material: " + material +
               " | Vida util: " + vidaUtil + " anios" +
               " | Marca: " + marca +
               " | Stock: " + getStock();
    }

    @Override
    public String toString() {
        return "Herramienta{material='" + material + "', vidaUtil=" + vidaUtil +
               ", marca='" + marca + "', " + super.toString() + "}";
    }
}