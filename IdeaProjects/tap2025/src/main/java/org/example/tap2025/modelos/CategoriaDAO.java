package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriaDAO {
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private byte[] imagen;

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void INSERT(){
        String query = "INSERT INTO categoria (nom_categoria, descripcion, imagen) VALUES ('"+nombre+"','"+descripcion+"','"+imagen+"')";
        try {
            Statement stmt= Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE categoria SET nom_categoria = ?, descripcion = ?, imagen = ? WHERE id_categoria = ?";
        try {
            PreparedStatement stmt = Conexion.connection.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setBytes(3, imagen);
            stmt.setInt(4, idCategoria);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DELETE(){
        String query = "DELETE FROM categoria WHERE id_categoria = "+idCategoria;
        try {
            Statement stmt= Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<CategoriaDAO> SELECT(){
        String query = "SELECT * FROM categoria";
        ObservableList<CategoriaDAO> listaC = FXCollections.observableArrayList();
        CategoriaDAO objC;
        try {
            Statement stmt= Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                objC = new CategoriaDAO();
                objC.setIdCategoria(res.getInt("id_categoria"));
                objC.setNombre(res.getString("nom_categoria"));
                objC.setDescripcion(res.getString("descripcion"));
                objC.setImagen(res.getBytes("imagen"));
                listaC.add(objC);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaC;
    }

}
