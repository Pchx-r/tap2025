package org.example.tap2025.modelos;

import java.sql.ResultSet;
import java.sql.Statement;

public class MesaDAO {
    private int no_mesa;
    private String capacidad;

    public int getNo_mesa() {
        return no_mesa;
    }

    public void setNo_mesa(int no_mesa) {
        this.no_mesa = no_mesa;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }


    public void INSERT(){
        String query = "INSERT INTO mesa (capacidad) VALUES ('"+capacidad+"')";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        String query = "UPDATE mesa SET capacidad = '"+capacidad+"' WHERE no_mesa = "+no_mesa;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int Count(){
        String query = "SELECT COUNT(*) as cantidad FROM mesa";
        int nro_mesas = 0;
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()){
                nro_mesas = res.getInt("cantidad");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return nro_mesas;
    }


}
