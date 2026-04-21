package co.edu.poli.insumoagricola.model;

import java.io.Serializable;

/**
 * Mostramos a la semilla como cualquier semilla que se quiera colocar
 */
public class Semilla extends InsumoAgricola implements Serializable {

    private static final long serialVersionUID = 1L;

    private String composicion;
    private String npk;
    private String modoAplicacion;
    private String variedad;
    private int cicloVida;

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

    public Semilla() { super(); }

    public String getComposicion() { return composicion; }
    public String getNpk() { return npk; }
    public String getModoAplicacion() { return modoAplicacion; }
    public String getVariedad() { return variedad; }
    public int getCicloVida() { return cicloVida; }

    public void setComposicion(String composicion) { this.composicion = composicion; }
    public void setNpk(String npk) { this.npk = npk; }
    public void setModoAplicacion(String modoAplicacion) { this.modoAplicacion = modoAplicacion; }
    public void setVariedad(String variedad) { this.variedad = variedad; }
    public void setCicloVida(int cicloVida) { this.cicloVida = cicloVida; }

    @Override
    public String tipoInsumo() { return "Semilla"; }

    @Override
    public double calcularCosto() {
        if (getStock() > 500) {
            return getPrecio() * getStock() * 0.92;
        }
        return getPrecio() * getStock();
    }

    @Override
    public String descripcion() {
        return "[Semilla] " + getNombre() +
               " | Variedad: " + variedad +
               " | Ciclo: " + cicloVida + " dias" +
               " | Siembra: " + modoAplicacion +
               " | Stock: " + getStock() +
               " | Vence: " + getFechaVencimiento();
    }

    @Override
    public String toString() {
        return "Semilla{composicion='" + composicion + "', npk='" + npk +
               "', modoAplicacion='" + modoAplicacion + "', variedad='" + variedad +
               "', cicloVida=" + cicloVida + ", " + super.toString() + "}";
    }
}