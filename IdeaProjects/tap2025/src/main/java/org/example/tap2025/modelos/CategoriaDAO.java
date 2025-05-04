package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriaDAO {
    private int idCategoria;
    private String nombre;
    private String descripcion;

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
        String query = "INSERT INTO categoria (nom_categoria, descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
        try {
            Statement stmt= Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE categoria SET nom_categoria = '"+nombre+"',"+
                "descripcion = '"+descripcion+"' WHERE id_categoria = "+idCategoria;
        try {
            Statement stmt= Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
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
                listaC.add(objC);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaC;
    }

}
