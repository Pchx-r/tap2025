package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenProductoDAO {
    private int id_orden;
    private String fecha;
    private double total;
    private int idCte;
    private int no_mesa;
    private int id_empleado;
    private int nro_orden;
    private String nomCte;
    private String empleado;
    private int id_producto;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

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

    public int getNro_orden() {
        return nro_orden;
    }

    public void setNro_orden(int nro_orden) {
        this.nro_orden = nro_orden;
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

    public void INSERT(int id_emp, int id_prod) {
        System.out.println(id_orden);
        this.id_producto += id_prod;
        this.id_empleado = id_emp;
        String query = "INSERT INTO orden_producto (id_orden, total, idCte, no_mesa, id_empleado, estado, id_producto ) VALUES (?, ?, ?, ?, ?, 'EN PROCESO', ?)";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1, id_orden);
            stmt.setDouble(2, total);
            stmt.setInt(3, idCte);
            stmt.setInt(4, no_mesa);
            stmt.setInt(5, id_empleado);
            stmt.setInt(6, id_producto);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE orden_producto SET estado = 'FINALIZADO' WHERE nro_orden = "+nro_orden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM orden_producto WHERE nro_orden = "+nro_orden;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenProductoDAO> FIND(int no_mesa) {
        String query = "SELECT op.id_orden, op.nro_orden, op.total, c.nomCte, op.no_mesa, CONCAT(e.nombre, ' ', e.apellido1, ' ', e.apellido2) AS empleado " +
                "FROM orden_producto op JOIN empleado e ON op.id_empleado = e.id_empleado " +
                "JOIN clientes c ON op.idCte = c.idCte " +
                "WHERE no_mesa = ? AND estado = 'EN PROCESO'";
        ObservableList<OrdenProductoDAO> listaO = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1, no_mesa);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                OrdenProductoDAO objO = new OrdenProductoDAO();
                objO.setId_orden(res.getInt("id_orden"));
                objO.setNro_orden(res.getInt("nro_orden"));
                objO.setTotal(res.getDouble("total"));
                objO.setNomCte(res.getString("nomCte"));
                objO.setNo_mesa(res.getInt("no_mesa"));
                objO.setEmpleado(res.getString("empleado"));
                listaO.add(objO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaO;
    }

    public ObservableList<OrdenProductoDAO> SELECT(){
        String query = "select op.id_orden,o.fecha, op.id_producto ,op.total,c.nomCte,op.no_mesa, concat(e.nombre,' ',e.apellido1,' ',e.apellido2) as empleado\n" +
                "FROM orden_producto op join empleado e on op.id_empleado = e.id_empleado join orden o on op.id_orden = o.id_orden\n" +
                "             join clientes c on op.idCte = c.idCte ";
        ObservableList<OrdenProductoDAO> listaO = FXCollections.observableArrayList();
        OrdenProductoDAO objO;
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objO = new OrdenProductoDAO();
                objO.setId_orden(res.getInt("id_orden"));
                objO.setFecha(res.getString("fecha"));
                objO.setTotal(res.getDouble("total"));
                objO.setNomCte(res.getString("nomCte"));
                objO.setNo_mesa(res.getInt("no_mesa"));
                objO.setEmpleado(res.getString("empleado"));
                objO.setId_producto(res.getInt("id_producto"));
                listaO.add(objO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaO;
    }

    public int COUNT(){
        String query = "select count(*) as nro_ordenes_activas from orden_producto where estado = 'EN PROCESO' and no_mesa = "+no_mesa;
        int total=0;
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                total = res.getInt("nro_ordenes_activas");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }

    public int getCantidadVendidaPorProducto(int idProducto) {
        String query = "SELECT COUNT(*) AS cantidad  FROM orden_producto where id_producto = ?";
        int cantidad = 0;
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1,idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidad;
    }

    public double getTotalVentasPorDia(String fecha) {
        String query = "SELECT count(nro_orden) AS totalVentas FROM orden_producto join orden on orden_producto.id_orden = orden.id_orden WHERE fecha = ?";
        double totalVentas = 0;
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, fecha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalVentas = rs.getDouble("totalVentas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalVentas;
    }

    public double getTotalVentasPorEmpleado(int id_empleado) {
        String query = "SELECT SUM(total) AS totalVentas FROM orden_producto WHERE id_empleado = ?";
        double totalVentas = 0;
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1, id_empleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalVentas = rs.getDouble("totalVentas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalVentas;
    }

    public double getTotalOrden(){
        String query = "select sum(total) as totalVentas from orden_producto where id_orden = ? order by 1";
        double totalOrden = 0;
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1,no_mesa);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalOrden = rs.getDouble("totalVentas");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalOrden;
    }

}
