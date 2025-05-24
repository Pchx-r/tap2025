package org.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2025.componentes.ButtonCellOrden;
import org.example.tap2025.modelos.OrdenProductoDAO;

public class ListaOrdenes extends Stage {
    private TableView<OrdenProductoDAO> tbvOrdenes;
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
        OrdenProductoDAO objO = new OrdenProductoDAO();
        TableColumn<OrdenProductoDAO, String> tbcID = new TableColumn<>("ID Orden");
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        TableColumn<OrdenProductoDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<OrdenProductoDAO, String> tbcTotal = new TableColumn<>("Total");
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrdenProductoDAO, String> tbcCliente = new TableColumn<>("Cliente");
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("nomCte"));
        TableColumn<OrdenProductoDAO, String> tbcNomesa = new TableColumn<>("Número de Mesa");
        tbcNomesa.setCellValueFactory(new PropertyValueFactory<>("no_mesa"));
        TableColumn<OrdenProductoDAO, String> tbcEmpleado = new TableColumn<>("Empleado");
        tbcEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));

        TableColumn<OrdenProductoDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<OrdenProductoDAO, String>, TableCell<OrdenProductoDAO, String>>() {
            @Override
            public TableCell<OrdenProductoDAO,String> call(TableColumn<OrdenProductoDAO, String> param) {
                return new ButtonCellOrden("Borrar");
            }
        });

        tbvOrdenes.getColumns().addAll(tbcID,tbcFecha,tbcTotal,tbcCliente,tbcNomesa,tbcEmpleado,tbcBorrar);
        tbvOrdenes.setItems(objO.SELECT());
    }
}
