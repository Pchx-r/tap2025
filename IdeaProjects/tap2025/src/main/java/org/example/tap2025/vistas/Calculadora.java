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
    private TextField txt = new TextField();

    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    String strKeys[] = {"7","8","9","*","4","5","6","/","1","2","3","+","=","0",".","-"};
    String num1, num2;
    char caso = '0';
    boolean flag = false, operacion= false;
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

        if(lblDisplay.getText().equals("*")||lblDisplay.getText().equals("/")||lblDisplay.getText().equals("-")||lblDisplay.getText().equals("+")){
            lblDisplay.setText("");
        }

        if(strTecla.equals("=") && caso != '0') {
            num2 = lblDisplay.getText();
            try{
                lblDisplay.setText(resultado());
            }catch (NumberFormatException e){
                lblDisplay.setText(e.getMessage());
            }catch (NullPointerException e){
                lblDisplay.setText(e.getMessage());
            }catch (ArithmeticException e){
                lblDisplay.setText(e.getMessage());
            }
            num1 = null;
            num2 = null;
            caso = '0';
            flag = true;


        }else if (strTecla.equals("*") && num1 == null ||strTecla.equals("/")&& num1 == null||strTecla.equals("+")&& num1 == null||strTecla.equals("-")&& num1 == null){
            num1 = lblDisplay.getText();
            caso=strTecla.charAt(0);
            lblDisplay.setText(strTecla);
            operacion = true;
        }else if(strTecla.equals("*") && lblDisplay.getText().equals("0") ||strTecla.equals("/") && lblDisplay.getText().equals("0") ||strTecla.equals("+") && lblDisplay.getText().equals("0") ||strTecla.equals("-") && lblDisplay.getText().equals("0") ){
            lblDisplay.setText("0");
        }else if(lblDisplay.getText().equals("0")||lblDisplay.getText().equals("0")||lblDisplay.getText().equals(num1) && !operacion){
            lblDisplay.setText(strTecla);
        }else if(strTecla.equals("*") ||strTecla.equals("/")||strTecla.equals("+")||strTecla.equals("-")){

            try{
                if(lblDisplay.getText().equals("*")||strTecla.equals("/")||strTecla.equals("+")||strTecla.equals("-")){}
                num2 = lblDisplay.getText();
                num1 = resultado();
                lblDisplay.setText(num1);
                num2 = null;
                caso = strTecla.charAt(0);
                operacion= false;
            }catch (NumberFormatException e){
                caso=strTecla.charAt(0);
                lblDisplay.setText(strTecla);
            }

        }else {
            if(flag){
                lblDisplay.setText("");
                flag = false;
                num1 = null;
            }
            lblDisplay.setText(lblDisplay.getText()+strTecla);
        }

    }
    public String resultado(){
        double temp = 0.0;
                switch (caso) {
            case '+' -> temp = Double.parseDouble(num1) + Double.parseDouble(num2);
            case '-' -> temp = Double.parseDouble(num1) - Double.parseDouble(num2);
            case '*' -> temp = Double.parseDouble(num1) * Double.parseDouble(num2);
            case '/' -> temp = Double.parseDouble(num1) / Double.parseDouble(num2);
            case '0' -> temp = Double.parseDouble(num1);
        };
        return ""+temp;
    }
}
