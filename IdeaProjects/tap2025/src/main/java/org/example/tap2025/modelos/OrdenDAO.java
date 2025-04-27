package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDAO {
    private int id_orden;
    private String fecha;
    private double total;
    private int idCte;
    private int no_mesa;
    private int id_empleado;
    private String nomCte;
    private String empleado;

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getNomCte() {
        return nomCte;
    }

    public void setNomCte(String nomCte) {
        this.nomCte = nomCte;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdCte() {
        return idCte;
    }

    public void setIdCte(int idCte) {
        this.idCte = idCte;
    }

    public int getNo_mesa() {
        return no_mesa;
    }

    public void setNo_mesa(int no_mesa) {
        this.no_mesa = no_mesa;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void INSERT(){
        String query = "INSERT INTO orden (fecha, total, idCte, no_mesa, id_empleado, estado)"+
                "VALUES('"+fecha+"','"+total+"','"+idCte+"','"+no_mesa+"','"+id_empleado+"', 'EN PROCESO')";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void UPDATE(){
        String query = "UPDATE orden SET fecha = '"+fecha+"',"+
                "total = '"+total+"', idCte = '"+idCte+"', "+
                "no_mesa = '"+no_mesa+"', id_empleado = '"+id_empleado+"' WHERE id_orden = "+id_orden;
    }

    public void DELETE(){
        String query = "DELETE FROM orden WHERE id_orden = "+id_orden;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<OrdenDAO> FIND(int no_mesa){
        String query = "select o.id_orden,o.fecha,o.total,c.nomCte,o.no_mesa, concat(e.nombre,' ',e.apellido1,' ',e.apellido2) as empleado \n" +
                "FROM orden o join empleado e on o.id_empleado = e.id_empleado\n" +
                "             join clientes c on o.idCte = c.idCte\n" +
                "WHERE no_mesa = "+no_mesa;
        ObservableList<OrdenDAO> listaO = FXCollections.observableArrayList();
        OrdenDAO objO;
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objO = new OrdenDAO();
                objO.setId_orden(res.getInt("id_orden"));
                objO.setFecha(res.getString("fecha"));
                objO.setTotal(res.getDouble("total"));
                objO.setNomCte(res.getString("nomCte"));
                objO.setNo_mesa(res.getInt("no_mesa"));
                objO.setEmpleado(res.getString("empleado"));
                listaO.add(objO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaO;
    }
    public ObservableList<OrdenDAO> SELECT(){
        String query = "select o.id_orden,o.fecha,o.total,c.nomCte,o.no_mesa, concat(e.nombre,' ',e.apellido1,' ',e.apellido2) as empleado\n" +
                "FROM orden o join empleado e on o.id_empleado = e.id_empleado\n" +
                "             join clientes c on o.idCte = c.idCte";
        ObservableList<OrdenDAO> listaO = FXCollections.observableArrayList();
        OrdenDAO objO;
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objO = new OrdenDAO();
                objO.setId_orden(res.getInt("id_orden"));
                objO.setFecha(res.getString("fecha"));
                objO.setTotal(res.getDouble("total"));
                objO.setNomCte(res.getString("nomCte"));
                objO.setNo_mesa(res.getInt("no_mesa"));
                objO.setEmpleado(res.getString("empleado"));
                listaO.add(objO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaO;
    }
}
