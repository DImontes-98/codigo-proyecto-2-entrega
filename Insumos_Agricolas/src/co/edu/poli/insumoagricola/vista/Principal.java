package co.edu.poli.insumoagricola.vista;

import java.util.Scanner;

import co.edu.poli.insumoagricola.model.Fertilizante;
import co.edu.poli.insumoagricola.model.Herramienta;
import co.edu.poli.insumoagricola.model.InsumoAgricola;
import co.edu.poli.insumoagricola.model.Pesticida;
import co.edu.poli.insumoagricola.model.Semilla;
import co.edu.poli.insumoagricola.servicios.ImplementacionOperacionCrud;

/**
 *Aca esta representada nuestra clase principal de la finca el Naranjo
 */
public class Principal {

    public static void main(String[] args) {

        ImplementacionOperacionCrud crud = new ImplementacionOperacionCrud();
        int opcion;

        try (Scanner sc = new Scanner(System.in)) {
            do {
                System.out.println("\n========================================");
                System.out.println("  FINCA EL NARANJO - INVENTARIO INSUMOS");
                System.out.println("========================================");
                System.out.println("1. Crear insumo");
                System.out.println("2. Consultar insumo");
                System.out.println("3. Modificar insumo");
                System.out.println("4. Eliminar insumo");
                System.out.println("5. Listar insumos");
                System.out.println("6. Serializar inventario");
                System.out.println("7. Deserializar inventario");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1: crearInsumo(crud, sc); break;
                    case 2: consultarInsumo(crud, sc); break;
                    case 3: modificarInsumo(crud, sc); break;
                    case 4: eliminarInsumo(crud, sc); break;
                    case 5: System.out.println(crud.listar()); break;
                    case 6: serializarInventario(crud, sc); break;
                    case 7: deserializarInventario(crud, sc); break;
                    case 0: System.out.println("Saliendo del sistema. Hasta luego."); break;
                    default: System.out.println("Opcion no valida. Intente de nuevo.");
                }

            } while (opcion != 0);
        }
    }

    private static void crearInsumo(ImplementacionOperacionCrud crud, Scanner sc) {
        System.out.println("\n-- CREAR INSUMO --");
        System.out.println("Tipo de insumo:");
        System.out.println("1. Fertilizante");
        System.out.println("2. Pesticida");
        System.out.println("3. Herramienta");
        System.out.println("4. Semilla");
        System.out.print("Seleccione: ");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Precio: ");
        double precio = sc.nextDouble();
        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();
        System.out.print("Proveedor: ");
        String proveedor = sc.nextLine();
        System.out.print("Fecha de vencimiento (YYYY-MM-DD): ");
        String fecha = sc.nextLine();

        InsumoAgricola nuevo = null;

        switch (tipo) {
            case 1:
                System.out.print("Composicion: ");
                String composicion = sc.nextLine();
                System.out.print("NPK (ej: 10-20-10): ");
                String npk = sc.nextLine();
                System.out.print("Modo de aplicacion: ");
                String modoFert = sc.nextLine();
                nuevo = (InsumoAgricola) new Fertilizante(codigo, nombre, precio, stock,
                        proveedor, fecha, composicion, npk, modoFert);
                break;
            case 2:
                System.out.print("Tipo de plagas: ");
                String plagas = sc.nextLine();
                System.out.print("Toxicidad (Alta/Media/Baja): ");
                String toxicidad = sc.nextLine();
                System.out.print("Modo de aplicacion: ");
                String modoPest = sc.nextLine();
                nuevo = new Pesticida(codigo, nombre, precio, stock,
                        proveedor, fecha, plagas, toxicidad, modoPest);
                break;
            case 3:
                System.out.print("Material: ");
                String material = sc.nextLine();
                System.out.print("Vida util (anios): ");
                int vidaUtil = sc.nextInt();
                sc.nextLine();
                System.out.print("Marca: ");
                String marca = sc.nextLine();
                nuevo = new Herramienta(codigo, nombre, precio, stock,
                        proveedor, fecha, material, vidaUtil, marca);
                break;
            case 4:
                System.out.print("Composicion: ");
                String compSem = sc.nextLine();
                System.out.print("NPK requerido: ");
                String npkSem = sc.nextLine();
                System.out.print("Modo de siembra: ");
                String modoSem = sc.nextLine();
                System.out.print("Variedad: ");
                String variedad = sc.nextLine();
                System.out.print("Ciclo de vida (dias): ");
                int ciclo = sc.nextInt();
                sc.nextLine();
                nuevo = new Semilla(codigo, nombre, precio, stock,
                        proveedor, fecha, compSem, npkSem, modoSem, variedad, ciclo);
                break;
            default:
                System.out.println("Tipo no valido.");
                return;
        }

        System.out.println(crud.crear(nuevo));
    }

    private static void consultarInsumo(ImplementacionOperacionCrud crud, Scanner sc) {
        System.out.println("\n-- CONSULTAR INSUMO --");
        System.out.print("Ingrese el codigo del insumo: ");
        String codigo = sc.nextLine();
        InsumoAgricola encontrado = crud.consultar(codigo);
        if (encontrado != null) {
            System.out.println("Insumo encontrado:");
            System.out.println(encontrado.descripcion());
            System.out.println("Costo total: $" + encontrado.calcularCosto());
        }
    }

    private static void modificarInsumo(ImplementacionOperacionCrud crud, Scanner sc) {
        System.out.println("\n-- MODIFICAR INSUMO --");
        System.out.print("Ingrese el codigo del insumo a modificar: ");
        String codigo = sc.nextLine();

        InsumoAgricola existente = crud.consultar(codigo);
        if (existente == null) {
            System.out.println("No se encontro el insumo con codigo: " + codigo);
            return;
        }

        System.out.println("Insumo actual: " + existente.descripcion());
        System.out.println("Ingrese los nuevos datos (tipo: " + existente.tipoInsumo() + "):");

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo precio: ");
        double precio = sc.nextDouble();
        System.out.print("Nuevo stock: ");
        int stock = sc.nextInt();
        sc.nextLine();
        System.out.print("Nuevo proveedor: ");
        String proveedor = sc.nextLine();
        System.out.print("Nueva fecha de vencimiento (YYYY-MM-DD): ");
        String fecha = sc.nextLine();

        InsumoAgricola modificado = null;

        // Semilla se evalua primero porque ya no extiende Fertilizante
        if (existente instanceof Semilla) {
            Semilla s = (Semilla) existente;
            System.out.print("Nueva variedad: ");
            String variedad = sc.nextLine();
            System.out.print("Nuevo ciclo de vida (dias): ");
            int ciclo = sc.nextInt();
            sc.nextLine();
            modificado = new Semilla(codigo, nombre, precio, stock, proveedor, fecha,
                    s.getComposicion(), s.getNpk(), s.getModoAplicacion(), variedad, ciclo);

        } else if (existente instanceof Fertilizante) {
            Fertilizante f = (Fertilizante) existente;
            modificado = new Fertilizante(codigo, nombre, precio, stock, proveedor, fecha,
                    f.getComposicion(), f.getNpk(), f.getModoAplicacion());

        } else if (existente instanceof Pesticida) {
            Pesticida p = (Pesticida) existente;
            modificado = new Pesticida(codigo, nombre, precio, stock, proveedor, fecha,
                    p.getTipoPlagas(), p.getToxicidad(), p.getModoAplicacion());

        } else if (existente instanceof Herramienta) {
            Herramienta h = (Herramienta) existente;
            modificado = new Herramienta(codigo, nombre, precio, stock, proveedor, fecha,
                    h.getMaterial(), h.getVidaUtil(), h.getMarca());
        }

        if (modificado != null) {
            System.out.println(crud.modificar(codigo, modificado));
        }
    }

    private static void eliminarInsumo(ImplementacionOperacionCrud crud, Scanner sc) {
        System.out.println("\n-- ELIMINAR INSUMO --");
        System.out.print("Ingrese el codigo del insumo a eliminar: ");
        String codigo = sc.nextLine();
        System.out.println(crud.eliminar(codigo));
    }

    private static void serializarInventario(ImplementacionOperacionCrud crud, Scanner sc) {
        System.out.println("\n-- SERIALIZAR INVENTARIO --");
        System.out.print("Ruta del archivo (ej: C:/datos/): ");
        String path = sc.nextLine();
        System.out.print("Nombre del archivo (ej: inventario.bin): ");
        String name = sc.nextLine();
        System.out.println(crud.serializar(crud.getInsumo(), path, name));
    }

    private static void deserializarInventario(ImplementacionOperacionCrud crud, Scanner sc) {
        System.out.println("\n-- DESERIALIZAR INVENTARIO --");
        System.out.print("Ruta del archivo (ej: C:/datos/): ");
        String path = sc.nextLine();
        System.out.print("Nombre del archivo (ej: inventario.bin): ");
        String name = sc.nextLine();

        InsumoAgricola[] recuperado = crud.deserializar(path, name);
        if (recuperado != null) {
            System.out.println("OK: Inventario recuperado con " +
                               recuperado.length + " posiciones.");
            System.out.println(crud.listar());
        }
    }
}