package org.example.tap2025.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {
    private Scene escena;
    private Label lblDisplay;
    private VBox vBox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    String strKeys[] = {"7","8","9","*","4","5","6","/","1","2","3","+","=","0",".","-"};
    public void CrearUI(){
        CreateKeyboard();
        lblDisplay = new Label("0");
        vBox = new VBox(lblDisplay,gdpTeclado);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10,10,10,10));
        escena = new Scene(vBox, 200, 200);
        escena.getStylesheets().add(getClass().getResource("/styles/calcu.css").toString());

    }
    public Calculadora(){
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
    public void CreateKeyboard(){
        arBtnTeclado = new Button[4][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[j][i] = new Button(strKeys[pos]);
                if(strKeys[pos].equals("*")){
                    arBtnTeclado[j][i].setId("fontButton");
                }
                int finalPos = pos;
                arBtnTeclado[j][i].setOnAction(event -> EventoTeclado(strKeys[finalPos]));
                arBtnTeclado[j][i].setPrefSize(50,50);
                gdpTeclado.add(arBtnTeclado[j][i], j, i);
                pos++;
            }
        }
    }
    public void EventoTeclado(String strTecla){
        if(lblDisplay.getText().equals("0")){
            lblDisplay.setText(strTecla);
        }else {
            lblDisplay.setText(lblDisplay.getText()+strTecla);
        }

    }
}
