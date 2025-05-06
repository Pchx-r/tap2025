package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class ProductoDAO {
    private int id_producto;
    private String nombre_producto;
    private double precio;
    private double costo;
    private int id_categoria;
    private byte[] imagen;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public void INSERT() {
        String query = "INSERT INTO producto(nombre_producto, precio, costo, id_categoria, imagen) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, nombre_producto);
            stmt.setDouble(2, precio);
            stmt.setDouble(3, costo);
            stmt.setInt(4, id_categoria);
            stmt.setBytes(5, imagen);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE producto SET nombre_producto = ?, precio = ?, costo = ?, id_categoria = ?, imagen = ? WHERE id_producto = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, nombre_producto);
            stmt.setDouble(2, precio);
            stmt.setDouble(3, costo);
            stmt.setInt(4, id_categoria);
            stmt.setBytes(5, imagen);
            stmt.setInt(6, id_producto);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM producto WHERE id_producto = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1, id_producto);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ProductoDAO> SELECT() {
        String query = "SELECT * FROM producto";
        ObservableList<ProductoDAO> listaP = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                ProductoDAO objP = new ProductoDAO();
                objP.setId_producto(res.getInt("id_producto"));
                objP.setNombre_producto(res.getString("nombre_producto"));
                objP.setPrecio(res.getDouble("precio"));
                objP.setCosto(res.getDouble("costo"));
                objP.setId_categoria(res.getInt("id_categoria"));
                objP.setImagen(res.getBytes("imagen"));
                listaP.add(objP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaP;
    }

    public ObservableList<ProductoDAO> SELECT_BY_CATEGORIA(int categoriaId) {
        String query = "SELECT * FROM producto WHERE id_categoria = ?";
        ObservableList<ProductoDAO> listaP = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setInt(1, categoriaId);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                ProductoDAO objP = new ProductoDAO();
                objP.setId_producto(res.getInt("id_producto"));
                objP.setNombre_producto(res.getString("nombre_producto"));
                objP.setPrecio(res.getDouble("precio"));
                objP.setCosto(res.getDouble("costo"));
                objP.setId_categoria(res.getInt("id_categoria"));
                objP.setImagen(res.getBytes("imagen"));
                listaP.add(objP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaP;
    }
}
