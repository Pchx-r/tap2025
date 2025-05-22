package org.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2025.componentes.ButtonCellCategory;
import org.example.tap2025.modelos.CategoriaDAO;

import java.io.ByteArrayInputStream;

public class ListaCategoria extends Stage {
    private ToolBar tlbMenu;
    private TableView<CategoriaDAO> tbvCategorias;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaCategoria() {
        CrearUI();
        this.setTitle("Listado de Categorías");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvCategorias = new TableView<>();
        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(event -> new Categoria(tbvCategorias, null));
        tlbMenu = new ToolBar(btnAgregar);
        CreateTable();
        vBox = new VBox(tlbMenu, tbvCategorias);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        CategoriaDAO objC = new CategoriaDAO();
        TableColumn<CategoriaDAO, Integer> tbcIdCategoria = new TableColumn<>("ID Categoría");
        tbcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        TableColumn<CategoriaDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<CategoriaDAO, String> tbcDescripcion = new TableColumn<>("Descripción");
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<CategoriaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<CategoriaDAO, String>, TableCell<CategoriaDAO, String>>() {
            @Override
            public TableCell<CategoriaDAO, String> call(TableColumn<CategoriaDAO, String> param) {
                return new ButtonCellCategory("Editar");
            }
        });
        TableColumn<CategoriaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<CategoriaDAO, String>, TableCell<CategoriaDAO, String>>() {
            @Override
            public TableCell<CategoriaDAO, String> call(TableColumn<CategoriaDAO, String> param) {
                return new ButtonCellCategory("Eliminar");
            }
        });
        tbvCategorias.getColumns().addAll(tbcIdCategoria, tbcNombre, tbcDescripcion,  tbcEditar, tbcEliminar);
        tbvCategorias.setItems(objC.SELECT());
    }
}
