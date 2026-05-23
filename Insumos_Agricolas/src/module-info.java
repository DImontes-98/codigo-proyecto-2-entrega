module Insumos_Agricolas {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.poli.insumoagricola.model to javafx.base, javafx.fxml;
    opens co.edu.poli.insumoagricola.vista to javafx.fxml;
    opens co.edu.poli.insumoagricolamvc.controlador to javafx.fxml;

    exports co.edu.poli.insumoagricola.vista;
    exports co.edu.poli.insumoagricola.model;
    exports co.edu.poli.insumoagricola.servicios;
}