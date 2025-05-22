package org.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import org.example.tap2025.modelos.ProductoDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Producto extends Stage {
    private GridPane gridPane;
    private Scene escena;
    private TextField txtNombreProducto, txtPrecio, txtCosto, txtIdCategoria;
    private Button btnGuardar, btnCancelar, btnSeleccionarImagen;
    private File imagenFile;
    private ProductoDAO objP;

    public Producto(TableView<ProductoDAO> tbvProductos, ProductoDAO producto) {
        objP = producto != null ? producto : new ProductoDAO();
        CrearUI(tbvProductos);
        this.setTitle("Gestión de Producto");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(TableView<ProductoDAO> tbvProductos) {
        gridPane = new GridPane();
        txtNombreProducto = new TextField();
        txtPrecio = new TextField();
        txtCosto = new TextField();
        txtIdCategoria = new TextField();
        btnGuardar = new Button("Guardar");
        btnCancelar = new Button("Cancelar");
        btnSeleccionarImagen = new Button("Seleccionar Imagen");

        btnSeleccionarImagen.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
            imagenFile = fileChooser.showOpenDialog(this);
        });

        btnGuardar.setOnAction(event -> {
            objP.setNombre_producto(txtNombreProducto.getText());
            objP.setPrecio(Double.parseDouble(txtPrecio.getText()));
            objP.setCosto(Double.parseDouble(txtCosto.getText()));
            objP.setId_categoria(Integer.parseInt(txtIdCategoria.getText()));
            if (imagenFile != null) {
                try (FileInputStream fis = new FileInputStream(imagenFile)) {
                    byte[] imagenBytes = new byte[(int) imagenFile.length()];
                    fis.read(imagenBytes);
                    objP.setImagen(imagenBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objP.getId_producto() > 0) {
                objP.UPDATE();
            } else {
                objP.INSERT();
            }
            tbvProductos.setItems(objP.SELECT());
            this.close();
        });

        btnCancelar.setOnAction(event -> this.close());

        gridPane.add(new Label("Nombre Producto:"), 0, 0);
        gridPane.add(txtNombreProducto, 1, 0);
        gridPane.add(new Label("Precio:"), 0, 1);
        gridPane.add(txtPrecio, 1, 1);
        gridPane.add(new Label("Costo:"), 0, 2);
        gridPane.add(txtCosto, 1, 2);
        gridPane.add(new Label("ID Categoría:"), 0, 3);
        gridPane.add(txtIdCategoria, 1, 3);
        gridPane.add(btnSeleccionarImagen, 0, 4);
        gridPane.add(btnGuardar, 0, 5);
        gridPane.add(btnCancelar, 1, 5);

        escena = new Scene(gridPane, 400, 300);
    }
}
