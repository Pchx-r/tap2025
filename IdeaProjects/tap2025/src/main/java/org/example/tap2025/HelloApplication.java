package org.example.tap2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnBarPrincipal;
    private Menu mnCompetencia1, mnCompetencia2;
    private MenuItem mitCalculadora;
    private Scene escena;

    void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mnCompetencia1 = new Menu("competencia 1");
        mnCompetencia1.getItems().addAll(mitCalculadora);
        mnCompetencia2 = new Menu();
        mnBarPrincipal = new MenuBar();
        mnBarPrincipal.getMenus().addAll(mnCompetencia1, mnCompetencia2);
    }

    @Override
    public void start(Stage stage) throws IOException {
        vBox = new VBox();
        stage.setTitle("Hola mundo de eventos :D");
        stage.setScene(new Scene(vBox));
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    void clickOnSaludo() {

    }
}