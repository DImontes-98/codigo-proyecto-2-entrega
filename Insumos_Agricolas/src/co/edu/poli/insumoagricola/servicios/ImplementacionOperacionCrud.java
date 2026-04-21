package co.edu.poli.insumoagricola.servicios;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import co.edu.poli.insumoagricola.model.InsumoAgricola;

/**
 * Implementación de las interfaces OperacionCRUD y OperacionArchivo
 * para la gestión de objetos InsumoAgricola.
 */
public class ImplementacionOperacionCrud implements OperacionCRUD, OperacionArchivo {

    private InsumoAgricola[] insumo;
    private ArrayList<InsumoAgricola> inventario;

    public ImplementacionOperacionCrud() {
        insumo = new InsumoAgricola[2];
        inventario = new ArrayList<>();
    }

    /**
     * Retorna el arreglo fijo interno para poder serializarlo desde Principal.
     */
    public InsumoAgricola[] getInsumo() {
        return insumo;
    }

    @Override
    public String crear(InsumoAgricola i) {
        if (i == null) {
            return "ERROR: No se puede insertar un insumo nulo.";
        }
        if (i.getCodigo() == null || i.getCodigo().trim().isEmpty()) {
            return "ERROR: El codigo del insumo no puede estar vacio.";
        }
        if (i.getNombre() == null || i.getNombre().trim().isEmpty()) {
            return "ERROR: El nombre del insumo no puede estar vacio.";
        }
        if (i.getPrecio() < 0) {
            return "ERROR: El precio no puede ser negativo.";
        }
        if (duplicado(i.getCodigo())) {
            return "ERROR: Ya existe un insumo con codigo " + i.getCodigo();
        }

        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] == null) {
                insumo[j] = i;
                return "OK: Insumo '" + i.getNombre() +
                       "' insertado en posicion " + j + " del arreglo.";
            }
        }

        expandirInventario(i);
        return "OK: Arreglo lleno. Insumo '" + i.getNombre() +
               "' agregado al inventario dinamico (ArrayList).";
    }

    @Override
    public InsumoAgricola consultar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.out.println("ERROR consultar: El codigo no puede estar vacio.");
            return null;
        }

        for (InsumoAgricola a : insumo) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return a;
        }

        for (InsumoAgricola a : inventario) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return a;
        }

        System.out.println("ERROR consultar: No existe insumo con codigo " + codigo);
        return null;
    }

    @Override
    public String modificar(String codigo, InsumoAgricola nuevoInsumo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "ERROR modificar: El codigo no puede estar vacio.";
        }
        if (nuevoInsumo == null) {
            return "ERROR modificar: El nuevo insumo no puede ser nulo.";
        }
        if (nuevoInsumo.getNombre() == null || nuevoInsumo.getNombre().trim().isEmpty()) {
            return "ERROR modificar: El nombre del nuevo insumo no puede estar vacio.";
        }
        if (nuevoInsumo.getPrecio() < 0) {
            return "ERROR modificar: El precio no puede ser negativo.";
        }

        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] != null && insumo[j].getCodigo().equalsIgnoreCase(codigo)) {
                insumo[j] = nuevoInsumo;
                return "OK: Insumo con codigo " + codigo +
                       " modificado en posicion " + j + " del arreglo.";
            }
        }

        for (int j = 0; j < inventario.size(); j++) {
            if (inventario.get(j) != null &&
                inventario.get(j).getCodigo().equalsIgnoreCase(codigo)) {
                inventario.set(j, nuevoInsumo);
                return "OK: Insumo con codigo " + codigo +
                       " modificado en posicion " + j + " del inventario dinamico.";
            }
        }

        return "ERROR modificar: No existe insumo con codigo " + codigo;
    }

    @Override
    public String eliminar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "ERROR eliminar: El codigo no puede estar vacio.";
        }

        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] != null && insumo[j].getCodigo().equalsIgnoreCase(codigo)) {
                String nombre = insumo[j].getNombre();
                insumo[j] = null;
                return "OK: Insumo '" + nombre + "' (codigo=" + codigo +
                       ") eliminado de posicion " + j + " del arreglo.";
            }
        }

        for (int j = 0; j < inventario.size(); j++) {
            if (inventario.get(j) != null &&
                inventario.get(j).getCodigo().equalsIgnoreCase(codigo)) {
                String nombre = inventario.get(j).getNombre();
                inventario.remove(j);
                return "OK: Insumo '" + nombre + "' (codigo=" + codigo +
                       ") eliminado del inventario dinamico.";
            }
        }

        return "ERROR eliminar: No existe insumo con codigo " + codigo;
    }

    @Override
    public String listar() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INVENTARIO DE INSUMOS AGRICOLAS - FINCA EL NARANJO ===\n");
        sb.append("-- Arreglo fijo (capacidad 2) --\n");

        boolean hayAlgo = false;
        for (int j = 0; j < insumo.length; j++) {
            if (insumo[j] != null) {
                sb.append("  [").append(j).append("] ")
                  .append(insumo[j].descripcion()).append("\n");
                hayAlgo = true;
            } else {
                sb.append("  [").append(j).append("] (vacio)\n");
            }
        }

        sb.append("-- Inventario dinamico (ArrayList: ")
          .append(inventario.size()).append(" elementos) --\n");

        if (inventario.isEmpty()) {
            sb.append("  (sin insumos adicionales)\n");
        } else {
            for (int j = 0; j < inventario.size(); j++) {
                sb.append("  [").append(j).append("] ")
                  .append(inventario.get(j).descripcion()).append("\n");
                hayAlgo = true;
            }
        }

        if (!hayAlgo) {
            sb.append("  (sin insumos registrados)\n");
        }

        sb.append("==========================================================");
        return sb.toString();
    }

    public void expandirInventario(InsumoAgricola i) {
        inventario.add(i);
    }

    @Override
    public String serializar(InsumoAgricola[] insumos, String path, String name) {
        try (FileOutputStream fos = new FileOutputStream(path + name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(insumos);
            return "OK: Archivo creado en " + path + name;
        } catch (IOException e) {
            return "ERROR al serializar: " + e.getMessage();
        }
    }

    @Override
    public InsumoAgricola[] deserializar(String path, String name) {
        try (FileInputStream fis = new FileInputStream(path + name);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            InsumoAgricola[] recuperado = (InsumoAgricola[]) ois.readObject();
            insumo = recuperado;
            return recuperado;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR al deserializar: " + e.getMessage());
            return null;
        }
    }

    private boolean duplicado(String codigo) {
        for (InsumoAgricola a : insumo) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        for (InsumoAgricola a : inventario) {
            if (a != null && a.getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        return false;
    }
}