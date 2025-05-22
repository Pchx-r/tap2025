package org.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import org.example.tap2025.modelos.CategoriaDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Categoria extends Stage {
    private GridPane gridPane;
    private Scene escena;
    private TextField txtNombre, txtDescripcion;
    private Button btnGuardar, btnCancelar, btnSeleccionarImagen;
    private File imagenFile;
    private CategoriaDAO objC;

    public Categoria(TableView<CategoriaDAO> tbvCategorias, CategoriaDAO categoria) {
        objC = categoria != null ? categoria : new CategoriaDAO();
        CrearUI(tbvCategorias);
        this.setTitle("Gestión de Categoría");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(TableView<CategoriaDAO> tbvCategorias) {
        gridPane = new GridPane();
        txtNombre = new TextField();
        txtDescripcion = new TextField();
        btnGuardar = new Button("Guardar");
        btnCancelar = new Button("Cancelar");
        btnSeleccionarImagen = new Button("Seleccionar Imagen");

        btnSeleccionarImagen.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
            imagenFile = fileChooser.showOpenDialog(this);
        });

        btnGuardar.setOnAction(event -> {
            objC.setNombre(txtNombre.getText());
            objC.setDescripcion(txtDescripcion.getText());
            if (imagenFile != null) {
                try (FileInputStream fis = new FileInputStream(imagenFile)) {
                    byte[] imagenBytes = new byte[(int) imagenFile.length()];
                    fis.read(imagenBytes);
                    objC.setImagen(imagenBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objC.getIdCategoria() > 0) {
                objC.UPDATE();
            } else {
                objC.INSERT();
            }
            tbvCategorias.setItems(objC.SELECT());
            this.close();
        });

        btnCancelar.setOnAction(event -> this.close());

        gridPane.add(new Label("Nombre:"), 0, 0);
        gridPane.add(txtNombre, 1, 0);
        gridPane.add(new Label("Descripción:"), 0, 1);
        gridPane.add(txtDescripcion, 1, 1);
        gridPane.add(btnSeleccionarImagen, 0, 2);
        gridPane.add(btnGuardar, 0, 3);
        gridPane.add(btnCancelar, 1, 3);

        escena = new Scene(gridPane, 400, 300);
    }
}
