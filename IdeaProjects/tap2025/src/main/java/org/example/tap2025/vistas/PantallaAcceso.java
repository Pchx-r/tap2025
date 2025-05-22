package org.example.tap2025.vistas;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2025.modelos.EmpleadoDAO;

import java.util.Optional;

public class PantallaAcceso extends Stage {
    private Label lblUsuario;
    private Label lblPswrd;
    private HBox hBoxButtons;
    private VBox vBoxDatos;
    private Scene escena;
    private Button btnAcceso;
    private Button btnVolver;
    private TextField txtUsuario;
    private TextField txtPassword;
    private EmpleadoDAO objE;

    public PantallaAcceso() {
        CrearUI();
        this.setTitle("Acceso");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        hBoxButtons = new HBox();
        vBoxDatos = new VBox();
        objE = new EmpleadoDAO();
        lblUsuario = new Label("Usuario:");
        lblPswrd = new Label("Contraseña");
        btnAcceso = new Button("Acceso");
        btnAcceso.setOnAction(event -> {
            ObservableList<EmpleadoDAO> listaE = objE.ACCESS(txtUsuario.getText(), txtPassword.getText());
            if(!listaE.isEmpty() && listaE != null) {
                objE = listaE.get(0);
                String puesto = objE.getPuesto();
                if(puesto != null && puesto.equals("j")) {
                    this.close();
                    new ListaEmpleado();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Usuario no valido");
                    alert.setContentText("El usuario no cuenta con los permisos para acceder a la lista de empleados");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Usuario no valido");
                alert.setContentText("El usuario no cuenta con los permisos para acceder a la lista de empleados");
                alert.showAndWait();
            }
        });
        btnVolver = new Button("Volver");
        btnVolver.setOnAction(event -> {this.close();});
        txtUsuario = new TextField();
        txtPassword = new TextField();
        hBoxButtons.getChildren().addAll(btnAcceso, btnVolver);
        vBoxDatos.getChildren().addAll(lblUsuario, txtUsuario, lblPswrd, txtPassword, hBoxButtons);
        escena = new Scene(vBoxDatos);
    }
}
