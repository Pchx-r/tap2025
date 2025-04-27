package org.example.tap2025.vistas;

import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2025.componentes.ButtonCellOrden;
import org.example.tap2025.modelos.ClientesDAO;
import org.example.tap2025.modelos.MesaDAO;
import org.example.tap2025.modelos.OrdenDAO;

public class ListaOrdenes extends Stage {
    private TableView<OrdenDAO> tbvOrdenes;
    private VBox vboxOrdenes;
    private Scene escena;
    public ListaOrdenes() {
        CrearUI();
        this.setTitle("Ordenes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvOrdenes = new TableView<>();
        CreateTableOrden();
        vboxOrdenes = new VBox(tbvOrdenes);
        escena = new Scene(vboxOrdenes);
    }
    private void CreateTableOrden() {
        OrdenDAO objO = new OrdenDAO();
        TableColumn<OrdenDAO, String> tbcID = new TableColumn<>("ID Orden");
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        TableColumn<OrdenDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<OrdenDAO, String> tbcTotal = new TableColumn<>("Total");
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrdenDAO, String> tbcCliente = new TableColumn<>("Cliente");
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("nomCte"));
        TableColumn<OrdenDAO, String> tbcNomesa = new TableColumn<>("Número de Mesa");
        tbcNomesa.setCellValueFactory(new PropertyValueFactory<>("no_mesa"));
        TableColumn<OrdenDAO, String> tbcEmpleado = new TableColumn<>("Empleado");
        tbcEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));

        TableColumn<OrdenDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO,String> call(TableColumn<OrdenDAO, String> param) {
                return new ButtonCellOrden("Borrar");
            }
        });

        tbvOrdenes.getColumns().addAll(tbcID,tbcFecha,tbcTotal,tbcCliente,tbcNomesa,tbcEmpleado,tbcBorrar);
        tbvOrdenes.setItems(objO.SELECT());
    }
}
