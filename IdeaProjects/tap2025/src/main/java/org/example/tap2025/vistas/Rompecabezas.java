package org.example.tap2025.vistas;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class Rompecabezas extends Stage {
    private Scene escena;
    private String tipo;
    private GridPane gdpContenedor;
    private ComboBox<String> cBox;
    private Button btnConfirmar;
    public Rompecabezas() {
        CreateUI();
        this.setTitle("Rompe Cabezas");
        this.setScene(escena);
        this.show();
    }

    public void CreateUI(){
        gdpContenedor = new GridPane();
        gdpContenedor.setHgap(2);
        gdpContenedor.setVgap(10);
        gdpContenedor.setPadding(new Insets(10,10,10,15));
        btnConfirmar = new Button("Confirmar");
        cBox = new ComboBox<>();
        cBox.setItems(FXCollections.observableArrayList("9","16","25"));
        cBox.setMaxSize(150,50);
        gdpContenedor.add(cBox, 0, 0);
        gdpContenedor.add(btnConfirmar, 0, 1);
        escena = new Scene(gdpContenedor, 300, 200);
        Alert a = new Alert(Alert.AlertType.NONE);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        btnConfirmar.setOnAction(event ->{
            tipo = cBox.getSelectionModel().getSelectedItem();
            if (tipo != null) {
                EventoComboBox(tipo);
            } else {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setContentText("Por favor, selecciona un valor antes de confirmar.");
                a.show();
            }
        });
    }

    public void EventoComboBox(String strCombo){
        new RompecabezasVentana(Integer.parseInt(strCombo));
    }
}
