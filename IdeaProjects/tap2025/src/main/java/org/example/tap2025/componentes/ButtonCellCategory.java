package org.example.tap2025.componentes;

import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2025.modelos.CategoriaDAO;
import org.example.tap2025.vistas.Categoria;
import java.util.Optional;

public class ButtonCellCategory extends TableCell<CategoriaDAO, String> {
    private Button btnCelda;
    private String strLabelBtn;

    public ButtonCellCategory(String label) {
        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);
        btnCelda.setOnAction(event -> {
            CategoriaDAO objC = this.getTableView().getItems().get(this.getIndex());
            if (strLabelBtn.equals("Editar")) {
                new Categoria(this.getTableView(), objC);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del Sistema");
                alert.setContentText("¿Deseas eliminar el registro seleccionado?");
                Optional<ButtonType> opcion = alert.showAndWait();
                if (opcion.get() == ButtonType.OK) {
                    objC.DELETE();
                }
            }
            this.getTableView().setItems(objC.SELECT());
            this.getTableView().refresh();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            this.setGraphic(btnCelda);
        }
    }
}
