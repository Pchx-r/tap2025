package org.example.tap2025.vistas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2025.modelos.EmpleadoDAO;

public class Empleado extends Stage {
    private Button btnGuardar;
    private TextField txtNombre, txtApellido1, txtApellido2, txtCurp, txtRfc, txtSueldo, txtPuesto, txtCelEmp, txtHorario, txtFechaIngreso, txtNssEmp, txtUser, txtPasswrd;
    private VBox vBox;
    private Scene escena;
    private EmpleadoDAO objE;
    private TableView<EmpleadoDAO> tbvEmpleados;

    public Empleado(TableView<EmpleadoDAO> tbvEmp, EmpleadoDAO obj) {
        this.tbvEmpleados = tbvEmp;
        CrearUI();
        if( obj == null){
            objE = new EmpleadoDAO();
        }else {
            objE = obj;
            txtNombre.setText(objE.getNombre());
            txtApellido1.setText(objE.getApellido1());
            txtApellido2.setText(objE.getApellido2());
            txtCurp.setText(objE.getCurp());
            txtRfc.setText(objE.getRfc());
            txtSueldo.setText(objE.getSueldo());
            txtPuesto.setText(objE.getPuesto());
            txtCelEmp.setText(objE.getCelEmp());
            txtHorario.setText(objE.getHorario());
            txtFechaIngreso.setText(objE.getFechaIngreso());
            txtNssEmp.setText(objE.getNssEmp());
            txtUser.setText(objE.getUser());
        }
        this.setTitle("Registrar Cliente");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        Label lblNombre = new Label("Nombre: ");
        txtNombre = new TextField();
        Label lblApellido1 = new Label("Apellido1: ");
        txtApellido1 = new TextField();
        Label lblApellido2 = new Label("Apellido2: ");
        txtApellido2 = new TextField();
        Label lblCurp = new Label("Curp: ");
        txtCurp = new TextField();
        Label lblRfc = new Label("Rfc: ");
        txtRfc = new TextField();
        Label lblSueldo = new Label("Sueldo: ");
        txtSueldo = new TextField();
        Label lblPuesto = new Label("Puesto: ");
        txtPuesto = new TextField();
        Label lblCelEmp = new Label("Nro Celular: ");
        txtCelEmp = new TextField();
        Label lblHorario = new Label("Hora: ");
        txtHorario = new TextField();
        Label lblFechaIngreso = new Label("Fecha de ingreso: ");
        txtFechaIngreso = new TextField();
        Label lblNssEmp = new Label("Nss: ");
        txtNssEmp = new TextField();
        Label lblUser = new Label("User: ");
        txtUser = new TextField();
        Label lblPasswrd = new Label("Password: ");
        txtPasswrd = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objE.setNombre(txtNombre.getText());
            objE.setApellido1(txtApellido1.getText());
            objE.setApellido2(txtApellido2.getText());
            objE.setCurp(txtCurp.getText());
            objE.setRfc(txtRfc.getText());
            objE.setSueldo(txtSueldo.getText());
            objE.setPuesto(txtPuesto.getText());
            objE.setCelEmp(txtCelEmp.getText());
            objE.setHorario(txtHorario.getText());
            objE.setFechaIngreso(txtFechaIngreso.getText());
            objE.setNssEmp(txtNssEmp.getText());
            objE.setUser(txtUser.getText());
            if (objE.getIdEmpleado()>0){
                objE.UPDATE();
            }else {
                objE.INSERT();
                tbvEmpleados.setItems(objE.SELECT());
            }
            objE.INSERT();
            tbvEmpleados.setItems(objE.SELECT());
        });
        vBox = new VBox(lblNombre,txtNombre,lblApellido1,txtApellido1,lblApellido2,txtApellido2,lblCurp,txtCurp,lblRfc,txtRfc,lblSueldo,txtSueldo,lblPuesto,txtPuesto,lblCelEmp,txtCelEmp,lblHorario,txtHorario,lblFechaIngreso,txtFechaIngreso,lblNssEmp,txtNssEmp,lblUser,txtUser,lblPasswrd,txtPasswrd,btnGuardar);
        escena = new Scene(vBox);
    }
}