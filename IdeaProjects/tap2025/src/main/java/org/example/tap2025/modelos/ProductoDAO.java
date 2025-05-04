package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductoDAO {
    private int id_producto;
    private String nombre_producto;
    private double precio;
    private double costo;
    private int id_categoria;

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

    public ObservableList<ProductoDAO> SELECT_BY_CATEGORIA(int categoriaId) {
        String query = "SELECT * FROM producto WHERE id_categoria = " + categoriaId;
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
                listaP.add(objP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaP;
    }
}