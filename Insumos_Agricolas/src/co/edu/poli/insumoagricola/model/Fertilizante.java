package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Representa un fertilizante agrícola, especialización de InsumoAgricola.
 */
public class Fertilizante extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    private String composicion;
    private String npk;
    private String modoAplicacion;

    public Fertilizante(String codigo, String nombre, double precio, int stock,
                        String proveedor, String fechaVencimiento,
                        String composicion, String npk, String modoAplicacion) {
        super(codigo, nombre, precio, stock, proveedor, fechaVencimiento);
        this.composicion = composicion;
        this.npk = npk;
        this.modoAplicacion = modoAplicacion;
    }

    public Fertilizante() { super(); }

    public String getComposicion() { return composicion; }
    public String getNpk() { return npk; }
    public String getModoAplicacion() { return modoAplicacion; }

    public void setComposicion(String composicion) { this.composicion = composicion; }
    public void setNpk(String npk) { this.npk = npk; }
    public void setModoAplicacion(String modoAplicacion) { this.modoAplicacion = modoAplicacion; }

    @Override
    public String tipoInsumo() { return "Fertilizante"; }

    @Override
    public double calcularCosto() {
        if (getStock() > 100) {
            return getPrecio() * getStock() * 0.95;
        }
        return getPrecio() * getStock();
    }

    @Override
    public String descripcion() {
        return "[Fertilizante] " + getNombre() +
               " | NPK: " + npk +
               " | Aplicacion: " + modoAplicacion +
               " | Stock: " + getStock() +
               " | Vence: " + getFechaVencimiento();
    }

    @Override
    public String toString() {
        return "Fertilizante{composicion='" + composicion + "', npk='" + npk +
               "', modoAplicacion='" + modoAplicacion + "', " + super.toString() + "}";
    }
}