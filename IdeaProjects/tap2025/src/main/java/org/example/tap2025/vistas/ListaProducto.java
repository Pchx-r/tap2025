package org.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2025.componentes.ButtonCellProducto;
import org.example.tap2025.modelos.ProductoDAO;

import java.io.ByteArrayInputStream;

public class ListaProducto extends Stage {
    private ToolBar tlbMenu;
    private TableView<ProductoDAO> tbvProductos;
    private HBox hBox;
    private BorderPane bdpProductos;
    private Scene escena;
    private Button btnAgregar, btnGraficas;

    public ListaProducto() {
        CrearUI();
        this.setTitle("Listado de Productos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvProductos = new TableView<>();
        btnAgregar = new Button("Agregar");
        btnGraficas = new Button("Graficas");
        btnGraficas.setOnAction(event -> new Graficas());
        btnAgregar.setOnAction(event -> new Producto(tbvProductos, null));
        tlbMenu = new ToolBar(btnAgregar, btnGraficas);
        CreateTable();
        bdpProductos = new BorderPane();
        hBox = new HBox(tlbMenu);
        bdpProductos.setTop(hBox);
        bdpProductos.setCenter(tbvProductos);
        escena = new Scene(bdpProductos, 800, 600);
    }

    private void CreateTable() {
        ProductoDAO objP = new ProductoDAO();
        TableColumn<ProductoDAO, Integer> tbcIdProducto = new TableColumn<>("ID Producto");
        tbcIdProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
        TableColumn<ProductoDAO, String> tbcNombreProducto = new TableColumn<>("Nombre Producto");
        tbcNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre_producto"));
        TableColumn<ProductoDAO, Double> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        TableColumn<ProductoDAO, Double> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        TableColumn<ProductoDAO, Integer> tbcIdCategoria = new TableColumn<>("ID Categoría");
        tbcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        TableColumn<ProductoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCellProducto("Editar");
            }
        });
        TableColumn<ProductoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCellProducto("Eliminar");
            }
        });

        tbvProductos.getColumns().addAll(tbcIdProducto, tbcNombreProducto, tbcPrecio, tbcCosto, tbcIdCategoria, tbcEditar, tbcEliminar);
        tbvProductos.setItems(objP.SELECT());
    }
}
