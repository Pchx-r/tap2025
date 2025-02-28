package org.example.tap2025.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ClientesDAO {
    private int idCte;
    private String nomCte;
    private String direccion;
    private String telefon;
    private String emailCte;

    public int getIdCte() {
        return idCte;
    }

    public void setIdCte(int idCte) {
        this.idCte = idCte;
    }

    public String getNomCte() {
        return nomCte;
    }

    public void setNomCte(String nomCte) {
        this.nomCte = nomCte;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmailCte() {
        return emailCte;
    }

    public void setEmailCte(String emailCte) {
        this.emailCte = emailCte;
    }

    public void INSERT(){
        String query = "INSERT INTO clientes(nomCte,telCte,direccion,emailCte) " +
                "values('"+nomCte+"','"+telefon+"','"+direccion+"','"+emailCte+"')";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void UPDATE(){
        String query = "UPDATE clientes SET nomCte = '"+nomCte+"'," +
                "telCte = '"+telefon+"',direccion = '"+direccion+"'," +
                "emailCte = '"+emailCte+"' WHERE idCte = "+idCte;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void DELETE(){
        String query = "delete from clientes where idCte = "+idCte;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<ClientesDAO> SELECT(){
        String query = "select * from clientes order by idCte";
        ObservableList<ClientesDAO> listaC = FXCollections.observableArrayList();
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            ClientesDAO objetoC;
            while(res.next()){
                objetoC = new ClientesDAO();
                objetoC.setIdCte(res.getInt("idCte"));
                objetoC.setNomCte(res.getString("nomCte"));
                objetoC.setDireccion(res.getString("direccion"));
                objetoC.setTelefon(res.getString("telCte"));
                objetoC.setEmailCte(res.getString("emailCte"));
                listaC.add(objetoC);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaC;
    }

}
