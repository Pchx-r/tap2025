package org.example.tap2025.modelos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrdenDAO {
    int idOrden;
    String fecha;
    double total;
    OrdenProductoDAO ordenProductoDAO;

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
}
