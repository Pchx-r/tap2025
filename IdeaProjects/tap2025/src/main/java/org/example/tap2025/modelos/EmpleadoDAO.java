package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadoDAO {
    private int idEmpleado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String curp;
    private String rfc;
    private String sueldo;
    private String puesto;
    private String celEmp;
    private String horario;
    private String fechaIngreso;
    private String nssEmp;
    private String user;
    private String passwrd;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = sueldo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getCelEmp() {
        return celEmp;
    }

    public void setCelEmp(String celEmp) {
        this.celEmp = celEmp;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getNssEmp() {
        return nssEmp;
    }

    public void setNssEmp(String nssEmp) {
        this.nssEmp = nssEmp;
    }

    public void INSERT(){
        String query = "INSERT INTO empleado(nombre,apellido1,apellido2,curp,rfc,sueldo,puesto,celEmp,horario,fechaIngreso,nssEmp,user,passwrd) " +
                "values('"+nombre+"','"+apellido1+"','"+apellido2+"','"+curp+"','"+rfc+"','"+sueldo+"','"+puesto+"','"+celEmp+"','"+horario+"','"+fechaIngreso+"','"+nssEmp+"','"+ user+"',aes_encrypt('"+passwrd+"','clave'))";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE empleado SET nombre = '"+nombre+"',"+
                "apellido1 = '"+apellido1+"', apellido2 = '"+apellido2+"',"+
                "curp = '"+curp+"',rfc = '"+rfc+"',"+
                "sueldo = '"+sueldo+"', puesto = '"+puesto+"',"+
                "celEmp = '"+celEmp+"', horario ='"+horario+"',"+
                "fechaIngreso = '"+fechaIngreso+"', nssEmp = '"+nssEmp+"', user = '"+user+"' WHERE idEmpleado = "+idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DELETE() {
    String query = "DELETE FROM empleado WHERE id_empleado = "+idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<EmpleadoDAO> SELECT(){
        String query = "select id_empleado,concat(nombre, ' ', apellido1, ' ', empleado.apellido2 ) as nombre,curp,rfc,sueldo,puesto,celEmp,horario,fechaIngreso,nssEmp,user\n" +
                "from empleado";
        ObservableList<EmpleadoDAO> listaE = FXCollections.observableArrayList();
        EmpleadoDAO objE;
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                objE = new EmpleadoDAO();
                objE.setIdEmpleado(res.getInt("id_empleado"));
                objE.setNombre(res.getString("nombre"));
                objE.setCurp(res.getString("curp"));
                objE.setRfc(res.getString("rfc"));
                objE.setSueldo(res.getString("sueldo"));
                objE.setPuesto(res.getString("puesto"));
                objE.setCelEmp(res.getString("celEmp"));
                objE.setHorario(res.getString("horario"));
                objE.setFechaIngreso(res.getString("fechaIngreso"));
                objE.setNssEmp(res.getString("nssEmp"));
                objE.setUser(res.getString("user"));
                listaE.add(objE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaE;
    }
}
