package org.example.tap2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2025.vistas.Calculadora;

import java.io.IOException;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnBarPrincipal;
    private Menu mnCompetencia1, mnCompetencia2;
    private MenuItem mitCalculadora, mitSalida;
    private Scene escena;

    void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitSalida = new MenuItem("Salida");
        mitCalculadora.setOnAction(event -> new Calculadora());
        mitSalida.setOnAction(event -> System.exit(0));
        mnCompetencia1 = new Menu("Competencia 1");
        mnCompetencia1.getItems().addAll(mitCalculadora, mitSalida);
        mnCompetencia2 = new Menu("Competencia 2");
        mnBarPrincipal = new MenuBar();
        mnBarPrincipal.getMenus().addAll(mnCompetencia1, mnCompetencia2);
        vBox = new VBox(mnBarPrincipal);
        escena = new Scene(vBox);
        escena.getStylesheets().add(getClass().getResource("/styles/main.css").toString());
    }

    @Override
    public void start(Stage stage) throws IOException {
        CrearUI();
        stage.setTitle("Hola mundo de eventos :D");
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    void clickOnSaludo() {

    }
}