package co.edu.poli.insumoagricola.model;

/**
 * Representa la finca agrícola que gestiona el inventario de insumos.
 */
public class Finca {

    private String nombre;
    private String ubicacion;

    public Finca(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public double calcularTotalInventario(InsumoAgricola[] insumos) {
        double total = 0;
        for (InsumoAgricola i : insumos) {
            if (i != null) total += i.calcularCosto();
        }
        return total;
    }

    public int contarInsumosPorTipo(InsumoAgricola[] insumos, String tipo) {
        int contador = 0;
        for (InsumoAgricola i : insumos) {
            if (i != null && i.tipoInsumo().equalsIgnoreCase(tipo)) contador++;
        }
        return contador;
    }

    @Override
    public String toString() {
        return "Finca{nombre='" + nombre + "', ubicacion='" + ubicacion + "'}";
    }
}
