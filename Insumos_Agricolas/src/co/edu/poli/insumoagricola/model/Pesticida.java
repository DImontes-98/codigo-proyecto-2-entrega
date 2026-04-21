package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Aca tenemos cualquier pesticida que queramos agregar
 */
public class Pesticida extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoPlagas;
    private String toxicidad;
    private String modoAplicacion;

    public Pesticida(String codigo, String nombre, double precio, int stock,
                     String proveedor, String fechaVencimiento,
                     String tipoPlagas, String toxicidad, String modoAplicacion) {
        super(codigo, nombre, precio, stock, proveedor, fechaVencimiento);
        this.tipoPlagas = tipoPlagas;
        this.toxicidad = toxicidad;
        this.modoAplicacion = modoAplicacion;
    }

    public Pesticida() { super(); }

    public String getTipoPlagas() { return tipoPlagas; }
    public String getToxicidad() { return toxicidad; }
    public String getModoAplicacion() { return modoAplicacion; }

    public void setTipoPlagas(String tipoPlagas) { this.tipoPlagas = tipoPlagas; }
    public void setToxicidad(String toxicidad) { this.toxicidad = toxicidad; }
    public void setModoAplicacion(String modoAplicacion) { this.modoAplicacion = modoAplicacion; }

    @Override
    public String tipoInsumo() { return "Pesticida"; }

    @Override
    public double calcularCosto() {
        if ("Alta".equalsIgnoreCase(toxicidad)) {
            return getPrecio() * getStock() * 1.10;
        }
        return getPrecio() * getStock();
    }

    @Override
    public String descripcion() {
        return "[Pesticida] " + getNombre() +
               " | Plagas: " + tipoPlagas +
               " | Toxicidad: " + toxicidad +
               " | Aplicacion: " + modoAplicacion +
               " | Stock: " + getStock() +
               " | Vence: " + getFechaVencimiento();
    }

    @Override
    public String toString() {
        return "Pesticida{tipoPlagas='" + tipoPlagas + "', toxicidad='" + toxicidad +
               "', modoAplicacion='" + modoAplicacion + "', " + super.toString() + "}";
    }
}