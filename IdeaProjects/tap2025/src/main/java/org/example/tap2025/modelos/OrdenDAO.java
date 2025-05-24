package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrdenDAO {
    int idOrden;
    String fecha;
    double total;
    int nro_mesa;
    String nom_cte;
    OrdenProductoDAO ordenProductoDAO;

    public int getNro_mesa() {
        return nro_mesa;
    }

    public void setNro_mesa(int nro_mesa) {
        this.nro_mesa = nro_mesa;
    }

    public String getNom_cte() {
        return nom_cte;
    }

    public void setNom_cte(String nom_cte) {
        this.nom_cte = nom_cte;
    }

    public OrdenDAO() {
         ordenProductoDAO = new OrdenProductoDAO();
     }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
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


    public int COMPROBANTE(int no_mesa) throws SQLException {
        String query = "SELECT id_orden FROM orden_producto WHERE no_mesa = ? AND estado = 'EN PROCESO'";
        PreparedStatement stmt = Conexion.connection.prepareStatement(query);
        stmt.setInt(1, no_mesa);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id_orden");
        } else {
            String insertQuery = "INSERT INTO orden (fecha) VALUES (?)";
            PreparedStatement insertStmt = Conexion.connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, fecha);
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }
        return -1; // Error
    }


    public void INSERT() throws SQLException {
        double totalObtenido = ordenProductoDAO.getTotalOrden();
        String query = "INSERT INTO orden (fecha, total) VALUES (?, ?)";
        PreparedStatement stmt = Conexion.connection.prepareStatement(query);
        stmt.setString(1, fecha);
        stmt.setDouble(2, totalObtenido);
        stmt.executeUpdate();
    }
    public void UPDATE( ){
        String query = "update orden set total =(select sum(precio) from producto p join orden_producto op on p.id_producto = op.id_producto where id_orden = ?), fecha = now() where id_orden = ? ";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1,idOrden);
            stmt.setInt(2,idOrden);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void RESERVE(String fechaReservacion) {
        String query = "INSERT INTO orden (fecha, total) VALUES (?, 0)";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, fechaReservacion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDAO> SELECT(){
        String query = "select o.id_orden, o.fecha, o.total, m.no_mesa, c.nomCte\n" +
                "from orden o join orden_producto op on o.id_orden = op.id_orden\n" +
                "             join mesa m on op.no_mesa = m.no_mesa\n" +
                "             join clientes c on op.idCte = c.idCte\n" +
                "group by id_orden, m.no_mesa, c.nomCte\n" +
                "order by id_orden";
        ObservableList<OrdenDAO> listaO = FXCollections.observableArrayList();
        OrdenDAO objO;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objO = new OrdenDAO();
                objO.setIdOrden(res.getInt("id_orden"));
                objO.setFecha(res.getString("fecha"));
                objO.setTotal(res.getDouble("total"));
                objO.setNom_cte(res.getString("nomCte"));
                objO.setNro_mesa(res.getInt("no_mesa"));
                listaO.add(objO);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listaO;
    }
}
