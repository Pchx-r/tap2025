package org.example.tap2025.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.tap2025.modelos.Component;

import java.util.*;

public class RompecabezasVentana extends Stage {
    private Scene escena;
    private int raiz;
    private GridPane gdpRompecabezas;
    private Button[] btnImg;
    private Component[] components;
    private Component aux;
    private Button auxBtn;
    private int auxCount;
    private Label lblTimer;
    private int secondsElapsed;
    private Timeline timeline;

    public RompecabezasVentana(int numero) {
        lblTimer = new Label("Tiempo: 0s");
        secondsElapsed = 0;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        CreateUI(numero);
        String title = "Rompecabezas de " + numero;
        this.setTitle(title);
        this.setScene(escena);
        this.show();
    }

    private void updateTimer() {
        secondsElapsed++;
        lblTimer.setText("Tiempo: " + secondsElapsed + "s");
    }

    public void CreateUI(int numero) {
        raiz = (int) Math.sqrt(numero);
        gdpRompecabezas = new GridPane();
        btnImg = new Button[numero];
        components = new Component[numero];

        List<int[]> posiciones = new ArrayList<>();
        for (int i = 0; i < raiz; i++) {
            for (int j = 0; j < raiz; j++) {
                posiciones.add(new int[]{i, j});
            }
        }

        Collections.shuffle(posiciones);

        int count = 0;
        for (int i = 1; i <= raiz; i++) {
            for (int j = 1; j <= raiz; j++) {
                int[] pos = posiciones.get(count);
                int x = pos[0];
                int y = pos[1];
                String source = "/images/" + numero + "_row-" + i + "-column-" + j + ".jpg";
                ImageView imv = new ImageView(getClass().getResource(source).toString());
                components[count] = new Component(source, x, y, j - 1, i - 1);
                btnImg[count] = new Button();
                btnImg[count].setGraphic(imv);
                int finalCount = count;
                btnImg[count].setOnMouseClicked(event -> handlePieceClick(btnImg[finalCount], components[finalCount], components, finalCount));
                gdpRompecabezas.add(btnImg[count], x, y);
                count++;
            }
        }

        VBox root = new VBox(lblTimer, gdpRompecabezas);
        escena = new Scene(root, 750, 550);
        setResizable(false);
    }

    private void handlePieceClick(Button btnImg, Component clickedPiece, Component[] components, int finalCount) {
        if (aux == null) {
            aux = new Component(clickedPiece.getSource(), clickedPiece.getActualPositionX(), clickedPiece.getActualPositionY(), clickedPiece.getGoalPositionX(), clickedPiece.getGoalPositionY());
            auxBtn = btnImg;
            auxCount = finalCount;
        } else {
            swapPieces(aux, clickedPiece);
            Button auxBtn2 = new Button();
            auxBtn2.setGraphic(btnImg.getGraphic());
            btnImg.setGraphic(auxBtn.getGraphic());
            components[auxCount] = clickedPiece;
            components[finalCount] = aux;
            auxBtn.setGraphic(auxBtn2.getGraphic());
            aux = null;
            auxBtn = null;

            if (Check()) {
                timeline.stop();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("FELICIDADES");
                a.setContentText("Felicidades, has ganado en " + secondsElapsed + " segundos");
                a.show();
                try(PDDocument document = new PDDocument()){
                    PDPage page = new PDPage();
                    document.addPage(page);
                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                        contentStream.newLineAtOffset(100,700);
                        contentStream.showText("Tiempo en realizar rompecabezas " + secondsElapsed+ " segundos");
                        contentStream.endText();
                    }
                    document.save("Rompecabezas registro.pdf");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void swapPieces(Component piece1, Component piece2) {
        int actualX = piece1.getActualPositionX();
        int actualY = piece1.getActualPositionY();
        String auxSource = piece1.getSource();
        piece1.setActualPositionX(piece2.getActualPositionX());
        piece1.setActualPositionY(piece2.getActualPositionY());
        piece2.setActualPositionX(actualX);
        piece2.setActualPositionY(actualY);
        piece1.setSource(piece2.getSource());
        piece2.setSource(auxSource);
    }

    public boolean Check() {
        int numero = raiz * raiz;
        for (int i = 0; i < numero; i++) {
            if (components[i].getGoalPositionY() != components[i].getActualPositionY() || components[i].getGoalPositionX() != components[i].getActualPositionX()) {
                return false;
            }
        }
        return true;
    }
}
