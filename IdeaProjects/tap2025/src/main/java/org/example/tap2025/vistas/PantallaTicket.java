package org.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2025.componentes.ButtonCellTicket;
import org.example.tap2025.modelos.OrdenDAO;

public class PantallaTicket extends Stage {
    private TableView<OrdenDAO> tbvTickets;
    private Scene escena;
    private VBox vBox;
    private Button btnGenerar;

    public PantallaTicket() {
        CrearUI();
        this.setTitle("Tickets");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvTickets = new TableView<>();
        CreateTable();
        vBox = new VBox(tbvTickets);
        escena = new Scene(vBox);
    }
    private void CreateTable() {
        OrdenDAO objO = new OrdenDAO();
        TableColumn<OrdenDAO, String> tbcNroOrden = new TableColumn<>("Nro Orden");
        tbcNroOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));
        TableColumn<OrdenDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<OrdenDAO, String> tbcTotal = new TableColumn<>("Total");
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrdenDAO, String> tbcNomCte = new TableColumn<>("Nombre Cliente");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nom_cte"));
        TableColumn<OrdenDAO, String> no_mesa = new TableColumn<>("Mesa");
        no_mesa.setCellValueFactory(new PropertyValueFactory<>("nro_mesa"));

        TableColumn<OrdenDAO, String> tbcPrint = new TableColumn<>("Imprimir");
        tbcPrint.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> param) {
                return new ButtonCellTicket("Imprimir");
            }
        });

        tbvTickets.getColumns().addAll(tbcNroOrden,tbcFecha,tbcTotal,tbcNomCte,no_mesa,tbcPrint);
        tbvTickets.setItems(objO.SELECT());
    }
}
