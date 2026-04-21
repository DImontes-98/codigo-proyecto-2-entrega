package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Esta clase la dejamos abstracta para que todas nuestras subclases tuvieran siempre los metodos que consideramos necesarios.
 */
public abstract class InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private double precio;
    private int stock;
    private String proveedor;
    private String fechaVencimiento;

    public InsumoAgricola(String codigo, String nombre, double precio,
                          int stock, String proveedor, String fechaVencimiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
        this.fechaVencimiento = fechaVencimiento;
    }

    public InsumoAgricola() {}

    public abstract String tipoInsumo();

    public abstract String descripcion();

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public String getProveedor() { return proveedor; }
    public String getFechaVencimiento() { return fechaVencimiento; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public double calcularCosto() { return precio * stock; }

    public boolean validarCodigo(String c) { return c != null && !c.trim().isEmpty(); }

    @Override
    public String toString() {
        return "InsumoAgricola{codigo='" + codigo + "', nombre='" + nombre +
               "', precio=" + precio + ", stock=" + stock +
               ", proveedor='" + proveedor + "', fechaVencimiento='" + fechaVencimiento + "'}";
    }
}