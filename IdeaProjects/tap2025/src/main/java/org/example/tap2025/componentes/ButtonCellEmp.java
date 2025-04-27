package org.example.tap2025.componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2025.modelos.EmpleadoDAO;
import org.example.tap2025.vistas.Empleado;

import java.util.Optional;

public class ButtonCellEmp extends TableCell<EmpleadoDAO, String> {
    private Button btnCelda;
    private String strLabelBtn;

    public ButtonCellEmp(String label) {
        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);
        btnCelda.setOnAction(e -> {
            EmpleadoDAO objE = this.getTableView().getItems().get(this.getIndex());
            if (strLabelBtn.equals("Editar")) {
                new Empleado(this.getTableView(), objE);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del Sistema :D");
                alert.setContentText("Deseas eliminar al empleado seleccionado?");
                Optional<ButtonType> opcion = alert.showAndWait();
                if (opcion.get() == ButtonType.OK) {
                    objE.DELETE();
                }
            }
            this.getTableView().setItems(objE.SELECT());
            this.getTableView().refresh();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            this.setGraphic(btnCelda);
        }
    }
}