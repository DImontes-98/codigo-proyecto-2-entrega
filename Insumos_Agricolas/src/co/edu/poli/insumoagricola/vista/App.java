package co.edu.poli.insumoagricola.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación JavaFX para el sistema de inventario
 * de insumos agrícolas de la Finca El Naranjo.
 * <p>
 * Carga la vista FXML y lanza la ventana principal de la aplicación.
 * </p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>{@code
 * App.main(new String[]{});
 * }</pre>
 */
public class App extends Application {

    /**
     * Método de inicio de JavaFX. Carga el archivo FXML y configura
     * el escenario principal de la aplicación.
     *
     * @param stage escenario principal proporcionado por JavaFX
     * @throws Exception si ocurre un error al cargar el FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/co/edu/poli/insumoagricola/vista/insumos.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Finca El Naranjo - Inventario de Insumos Agrícolas");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada principal del programa.
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        launch(args);
    }
}