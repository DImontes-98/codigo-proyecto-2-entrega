package co.edu.poli.insumoagricolamvc.controlador;

import co.edu.poli.insumoagricola.model.*;
import co.edu.poli.insumoagricola.servicios.ImplementacionOperacionCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;

/**
 * Controlador JavaFX para la pantalla de registro de insumos agrícolas.
 * Conecta la vista FXML con la lógica del CRUD.
 */
public class InsumoControlador {

    // Campos formulario
    @FXML private DatePicker dpFechaIngreso;
    @FXML private TextField  tfId;
    @FXML private TextField  tfNombre;
    @FXML private TextField  tfPrecio;
    @FXML private TextField  tfCantidad;
    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField  tfProveedor;
    @FXML private DatePicker dpFechaVencimiento;

    // Campos extra dinámicos
    @FXML private Label     lblExtra1;
    @FXML private Label     lblExtra2;
    @FXML private Label     lblExtra3;
    @FXML private TextField tfExtra1;
    @FXML private TextField tfExtra2;
    @FXML private TextField tfExtra3;

    // Búsqueda
    @FXML private TextField        tfBuscar;
    @FXML private ComboBox<String> cbFiltro;

    // Tabla
    @FXML private TableView<InsumoAgricola>         tablaInsumos;
    @FXML private TableColumn<InsumoAgricola, String>  colId;
    @FXML private TableColumn<InsumoAgricola, String>  colNombre;
    @FXML private TableColumn<InsumoAgricola, String>  colTipo;
    @FXML private TableColumn<InsumoAgricola, Integer> colCantidad;
    @FXML private TableColumn<InsumoAgricola, String>  colFechaV;
    @FXML private TableColumn<InsumoAgricola, String>  colProveedor;

    // Lógica
    private final ImplementacionOperacionCrud crud = new ImplementacionOperacionCrud();
    private final ObservableList<InsumoAgricola> listaObservable = FXCollections.observableArrayList();

    /**
     * Inicializa la pantalla configurando tabla, combos y listeners.
     */
    @FXML
    public void initialize() {
        // ComboBox tipos
        cbTipo.setItems(FXCollections.observableArrayList(
                "Fertilizante", "Pesticida", "Herramienta", "Semilla"));
        cbTipo.setOnAction(e -> actualizarCamposExtra());

        // ComboBox filtro
        cbFiltro.setItems(FXCollections.observableArrayList(
                "Todos", "Fertilizante", "Pesticida", "Herramienta", "Semilla"));
        cbFiltro.setValue("Todos");

        // Columnas con PropertyValueFactory para getters normales
        colId.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colFechaV.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        // Columna Tipo: usa setCellFactory manual porque el método es tipoInsumo() sin "get"
        colTipo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().tipoInsumo()));

        tablaInsumos.setItems(listaObservable);

        // Clic en fila → cargar en formulario
        tablaInsumos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> { if (newVal != null) cargarEnFormulario(newVal); });

        ocultarCamposExtra();
    }

    // ── CRUD ──────────────────────────────────────────────────────────────

    /**
     * Registra un nuevo insumo y lo agrega a la tabla.
     */
    @FXML
    private void registrar() {
        InsumoAgricola nuevo = construirDesdeFormulario();
        if (nuevo == null) return;
        String resultado = crud.crear(nuevo);
        mostrarAlerta(resultado);
        if (resultado.startsWith("OK")) {
            listaObservable.add(nuevo);
            limpiar();
        }
    }

    /**
     * Actualiza el insumo seleccionado con los nuevos datos del formulario.
     */
    @FXML
    private void actualizar() {
        String codigo = tfId.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAlerta("ERROR: Seleccione un insumo de la tabla para actualizar.");
            return;
        }
        InsumoAgricola modificado = construirDesdeFormulario();
        if (modificado == null) return;
        String resultado = crud.modificar(codigo, modificado);
        mostrarAlerta(resultado);
        if (resultado.startsWith("OK")) {
            for (int i = 0; i < listaObservable.size(); i++) {
                if (listaObservable.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                    listaObservable.set(i, modificado);
                    break;
                }
            }
            limpiar();
        }
    }

    /**
     * Elimina el insumo seleccionado de la tabla y del CRUD.
     */
    @FXML
    private void eliminar() {
        InsumoAgricola seleccionado = tablaInsumos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("ERROR: Seleccione un insumo de la tabla para eliminar.");
            return;
        }
        String codigo = seleccionado.getCodigo();
        String resultado = crud.eliminar(codigo);
        mostrarAlerta(resultado);
        if (resultado.startsWith("OK")) {
            listaObservable.remove(seleccionado);
            limpiar();
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    @FXML
    private void limpiar() {
        tfId.clear(); tfNombre.clear(); tfCantidad.clear();
        tfPrecio.clear(); tfProveedor.clear();
        tfExtra1.clear(); tfExtra2.clear(); tfExtra3.clear();
        dpFechaIngreso.setValue(null);
        dpFechaVencimiento.setValue(null);
        cbTipo.setValue(null);
        ocultarCamposExtra();
        tablaInsumos.getSelectionModel().clearSelection();
    }

    /**
     * Filtra la tabla por texto y tipo de insumo.
     */
    @FXML
    private void buscar() {
        String texto  = tfBuscar.getText().trim().toLowerCase();
        String filtro = cbFiltro.getValue();
        ObservableList<InsumoAgricola> filtrados = FXCollections.observableArrayList();
        for (InsumoAgricola i : listaObservable) {
            boolean coincideTexto = texto.isEmpty()
                    || i.getNombre().toLowerCase().contains(texto)
                    || i.getCodigo().toLowerCase().contains(texto);
            boolean coincideTipo = "Todos".equals(filtro)
                    || i.tipoInsumo().equalsIgnoreCase(filtro);
            if (coincideTexto && coincideTipo) filtrados.add(i);
        }
        tablaInsumos.setItems(filtrados);
    }

    /**
     * Guarda el inventario en un archivo .bin usando FileChooser.
     */
    @FXML
    private void guardarArchivo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar inventario");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivo binario", "*.bin"));
        fc.setInitialFileName("inventario.bin");
        File archivo = fc.showSaveDialog(tablaInsumos.getScene().getWindow());
        if (archivo != null) {
            InsumoAgricola[] arr = listaObservable.toArray(new InsumoAgricola[0]);
            String resultado = crud.serializar(arr,
                    archivo.getParent() + File.separator, archivo.getName());
            mostrarAlerta(resultado);
        }
    }

    /**
     * Carga el inventario desde un archivo .bin y refresca la tabla.
     */
    @FXML
    private void cargarArchivo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Cargar inventario");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivo binario", "*.bin"));
        File archivo = fc.showOpenDialog(tablaInsumos.getScene().getWindow());
        if (archivo != null) {
            InsumoAgricola[] recuperado = crud.deserializar(
                    archivo.getParent() + File.separator, archivo.getName());
            if (recuperado != null) {
                listaObservable.clear();
                for (InsumoAgricola i : recuperado) {
                    if (i != null) listaObservable.add(i);
                }
                tablaInsumos.setItems(listaObservable);
                mostrarAlerta("OK: Inventario cargado con " +
                        listaObservable.size() + " insumos.");
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    /**
     * Construye un InsumoAgricola desde los campos del formulario.
     *
     * @return insumo construido o null si hay error de validación
     */
    private InsumoAgricola construirDesdeFormulario() {
        String codigo    = tfId.getText().trim();
        String nombre    = tfNombre.getText().trim();
        String tipo      = cbTipo.getValue();
        String proveedor = tfProveedor.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || tipo == null || proveedor.isEmpty()) {
            mostrarAlerta("ERROR: ID, Nombre, Tipo y Proveedor son obligatorios.");
            return null;
        }
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(tfPrecio.getText().trim());
            stock  = Integer.parseInt(tfCantidad.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta("ERROR: Precio y Cantidad deben ser números válidos.");
            return null;
        }
        String fechaV = dpFechaVencimiento.getValue() != null
                ? dpFechaVencimiento.getValue().toString() : "N/A";

        switch (tipo) {
            case "Fertilizante":
                return new Fertilizante(codigo, nombre, precio, stock, proveedor, fechaV,
                        tfExtra1.getText().trim(), tfExtra2.getText().trim(),
                        tfExtra3.getText().trim());
            case "Pesticida":
                return new Pesticida(codigo, nombre, precio, stock, proveedor, fechaV,
                        tfExtra1.getText().trim(), tfExtra2.getText().trim(),
                        tfExtra3.getText().trim());
            case "Herramienta":
                int vida = 0;
                try { vida = Integer.parseInt(tfExtra2.getText().trim()); }
                catch (NumberFormatException ignored) {}
                return new Herramienta(codigo, nombre, precio, stock, proveedor, fechaV,
                        tfExtra1.getText().trim(), vida, tfExtra3.getText().trim());
            case "Semilla":
                int ciclo = 0;
                try { ciclo = Integer.parseInt(tfExtra2.getText().trim()); }
                catch (NumberFormatException ignored) {}
                return new Semilla(codigo, nombre, precio, stock, proveedor, fechaV,
                        "", "", tfExtra3.getText().trim(),
                        tfExtra1.getText().trim(), ciclo);
            default:
                return null;
        }
    }

    /**
     * Carga los datos de un insumo seleccionado en el formulario.
     *
     * @param i insumo seleccionado en la tabla
     */
    private void cargarEnFormulario(InsumoAgricola i) {
        tfId.setText(i.getCodigo());
        tfNombre.setText(i.getNombre());
        tfPrecio.setText(String.valueOf(i.getPrecio()));
        tfCantidad.setText(String.valueOf(i.getStock()));
        tfProveedor.setText(i.getProveedor());
        cbTipo.setValue(i.tipoInsumo());
        if (i.getFechaVencimiento() != null && !i.getFechaVencimiento().equals("N/A")) {
            try { dpFechaVencimiento.setValue(LocalDate.parse(i.getFechaVencimiento())); }
            catch (Exception ignored) {}
        }
        actualizarCamposExtra();

        if (i instanceof Fertilizante f) {
            tfExtra1.setText(f.getComposicion());
            tfExtra2.setText(f.getNpk());
            tfExtra3.setText(f.getModoAplicacion());
        } else if (i instanceof Pesticida p) {
            tfExtra1.setText(p.getTipoPlagas());
            tfExtra2.setText(p.getToxicidad());
            tfExtra3.setText(p.getModoAplicacion());
        } else if (i instanceof Herramienta h) {
            tfExtra1.setText(h.getMaterial());
            tfExtra2.setText(String.valueOf(h.getVidaUtil()));
            tfExtra3.setText(h.getMarca());
        } else if (i instanceof Semilla s) {
            tfExtra1.setText(s.getVariedad());
            tfExtra2.setText(String.valueOf(s.getCicloVida()));
            tfExtra3.setText(s.getModoAplicacion());
        }
    }

    /**
     * Actualiza etiquetas y visibilidad de campos extra según el tipo seleccionado.
     */
    private void actualizarCamposExtra() {
        String tipo = cbTipo.getValue();
        if (tipo == null) { ocultarCamposExtra(); return; }
        lblExtra1.setVisible(true); tfExtra1.setVisible(true);
        lblExtra2.setVisible(true); tfExtra2.setVisible(true);
        lblExtra3.setVisible(true); tfExtra3.setVisible(true);
        switch (tipo) {
            case "Fertilizante":
                lblExtra1.setText("Composicion:");
                lblExtra2.setText("NPK:");
                lblExtra3.setText("Modo aplicacion:");
                break;
            case "Pesticida":
                lblExtra1.setText("Tipo plagas:");
                lblExtra2.setText("Toxicidad:");
                lblExtra3.setText("Modo aplicacion:");
                break;
            case "Herramienta":
                lblExtra1.setText("Material:");
                lblExtra2.setText("Vida util (anios):");
                lblExtra3.setText("Marca:");
                break;
            case "Semilla":
                lblExtra1.setText("Variedad:");
                lblExtra2.setText("Ciclo vida (dias):");
                lblExtra3.setText("Modo siembra:");
                break;
        }
    }

    /**
     * Oculta los campos extra del formulario.
     */
    private void ocultarCamposExtra() {
        lblExtra1.setVisible(false); tfExtra1.setVisible(false);
        lblExtra2.setVisible(false); tfExtra2.setVisible(false);
        lblExtra3.setVisible(false); tfExtra3.setVisible(false);
    }

    /**
     * Muestra un cuadro de diálogo con el mensaje dado.
     *
     * @param mensaje texto a mostrar
     */
    private void mostrarAlerta(String mensaje) {
        Alert.AlertType tipo = mensaje.startsWith("OK")
                ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR;
        Alert alert = new Alert(tipo);
        alert.setTitle("Sistema de Insumos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}