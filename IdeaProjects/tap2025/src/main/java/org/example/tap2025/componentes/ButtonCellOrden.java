package org.example.tap2025.componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2025.modelos.OrdenDAO;
import org.example.tap2025.vistas.Orden;

import java.util.Optional;

public class ButtonCellOrden extends TableCell<OrdenDAO,String> {
    private Button btnCelda;
    private String strLableBtn;
    public ButtonCellOrden(String label) {
        strLableBtn = label;
        btnCelda = new Button(strLableBtn);
        btnCelda.setOnAction(event -> {
           OrdenDAO objO = this.getTableView().getItems().get(this.getIndex());
           if(strLableBtn.equals("Finalizar")){
               new Orden(this.getTableView(), objO);
           }else{
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Mensaje del sistema");
               alert.setContentText("¿Deseas eliminar el registro seleccionado?");
               Optional<ButtonType> opcion = alert.showAndWait();
               if(opcion.get() == ButtonType.OK){
                   objO.DELETE();
               }
           }
           this.getTableView().setItems(objO.SELECT());
           this.getTableView().refresh();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if(!empty){
            this.setGraphic(btnCelda);
        }
    }
}
