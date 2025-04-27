package org.example.tap2025.vistas;

import javafx.beans.value.ObservableValue;
import org.example.tap2025.componentes.ButtonCell;
import org.example.tap2025.componentes.ButtonCellEmp;
import org.example.tap2025.modelos.ClientesDAO;
import org.example.tap2025.modelos.EmpleadoDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaEmpleado extends Stage{

    private ToolBar tblMenu;
    private TableView<EmpleadoDAO> tbvEmpleados;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;
    public ListaEmpleado(){
        CrearUI();
        this.setTitle("Lista de Empleados ");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        tbvEmpleados = new TableView<>();
        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Empleado(tbvEmpleados,null));
        btnAgregar.setText("Agregar");
        tblMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tblMenu, tbvEmpleados);
        escena = new Scene(vBox,1000,800);
    }

    private void CreateTable(){
        EmpleadoDAO objE = new EmpleadoDAO();
        TableColumn<EmpleadoDAO, String> tbcNomEmp = new TableColumn<>("Nombre");
        tbcNomEmp.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<EmpleadoDAO, String> tbcCurp = new TableColumn<>("Curp");
        tbcCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        TableColumn<EmpleadoDAO, String> tbcRfc = new TableColumn<>("RFC");
        tbcRfc.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        TableColumn<EmpleadoDAO, String> tbcSueldo = new TableColumn<>("Sueldo");
        tbcSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        TableColumn<EmpleadoDAO, String> tbcPuesto = new TableColumn<>("Puesto");
        tbcPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        TableColumn<EmpleadoDAO, String> tbcCelEmp = new TableColumn<>("Celular");
        tbcCelEmp.setCellValueFactory(new PropertyValueFactory<>("celEmp"));
        TableColumn<EmpleadoDAO, String> tbcHorario = new TableColumn<>("Horario");
        tbcHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        TableColumn<EmpleadoDAO, String> tbcFechaIngreso = new TableColumn<>("Fecha Ingreso");
        tbcFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        TableColumn<EmpleadoDAO, String> tbcNssEmp = new TableColumn<>("Nss");
        tbcNssEmp.setCellValueFactory(new PropertyValueFactory<>("nssEmp"));

        TableColumn<EmpleadoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCellEmp("Editar");
            }
        });
        TableColumn<EmpleadoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCellEmp("Eliminar");
            }
        });

        tbvEmpleados.getColumns().addAll(tbcNomEmp,tbcCurp, tbcRfc, tbcSueldo, tbcPuesto, tbcCelEmp, tbcHorario, tbcFechaIngreso, tbcNssEmp, tbcEditar, tbcEliminar);
        tbvEmpleados.setItems(objE.SELECT());
    }
}
