package org.example.tap2025.vistas;

import com.mysql.cj.xdevapi.Table;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2025.componentes.ButtonCellOrden;
import org.example.tap2025.modelos.CategoriaDAO;
import org.example.tap2025.modelos.MesaDAO;
import org.example.tap2025.modelos.OrdenDAO;
import org.example.tap2025.modelos.ProductoDAO;

public class OrdenesRestaurantes extends Stage {
    private HBox hBoxOptions;
    private Scene escena;
    private Button btnOrdenes, btnEmpleados;
    private Button[] btnMesa;
    private Button[] btnCategoria;
    private GridPane gdpMesas;
    private BorderPane borderPane;
    private MesaDAO mesaDAO;
    private TableView tbvOrden;
    private GridPane gdpCategorias;
    private CategoriaDAO categoriaDAO;
    private TableView<ProductoDAO> tbvProductos;
    private ProductoDAO productoDAO;

    public OrdenesRestaurantes() {
        CrearUI();
        this.setTitle("Ordenes Restaurante");
        this.setScene(escena);
        this.setMinHeight(800);
        this.setMinWidth(800);
        this.show();
    }

    private void CrearUI() {
        btnOrdenes = new Button("Ordenes");
        btnEmpleados = new Button("Empleados");
        mesaDAO = new MesaDAO();
        categoriaDAO = new CategoriaDAO();
        gdpMesas = new GridPane();
        gdpCategorias = new GridPane();
        tbvOrden = new TableView();
        productoDAO = new ProductoDAO();
        CreateProductosTable();
        CreateTableOrden();
        CreateGridMesas();
        CreateGridCategorias();
        ImageView imv = new ImageView(getClass().getResource("/images/orden_alimento.png").toString());
        imv.setFitWidth(60);
        imv.setFitHeight(60);
        btnOrdenes.setGraphic(imv);
        btnOrdenes.setOnAction(event -> new ListaOrdenes());
        imv = new ImageView(getClass().getResource("/images/personal.png").toString());
        imv.setFitHeight(60);
        imv.setFitWidth(60);
        btnEmpleados.setGraphic(imv);
        btnEmpleados.setOnAction(event -> new ListaEmpleado());
        hBoxOptions = new HBox();
        hBoxOptions.getChildren().addAll(btnOrdenes,btnEmpleados);
        borderPane = new BorderPane();
        borderPane.setTop(hBoxOptions);
        borderPane.setLeft(gdpMesas);
        borderPane.setCenter(tbvOrden);
        borderPane.setRight(gdpCategorias);
        escena = new Scene(borderPane);
    }
    private void CreateTableOrden() {
        OrdenDAO objO = new OrdenDAO();
        TableColumn<OrdenDAO, String> tbcID = new TableColumn<>("ID Orden");
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        TableColumn<OrdenDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<OrdenDAO, String> tbcTotal = new TableColumn<>("Total");
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrdenDAO, String> tbcCliente = new TableColumn<>("Cliente");
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("nomCte"));
        TableColumn<OrdenDAO, String> tbcNomesa = new TableColumn<>("Número de Mesa");
        tbcNomesa.setCellValueFactory(new PropertyValueFactory<>("no_mesa"));

        TableColumn<OrdenDAO, String> tbcFinalizar = new TableColumn<>("Finalizar");
        tbcFinalizar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> ordenDAOStringTableColumn) {
                return new ButtonCellOrden("Finalizar");
            }
        });

        tbvOrden.getColumns().addAll(tbcID,tbcFecha,tbcTotal,tbcCliente,tbcNomesa,tbcFinalizar);
        tbvOrden.setItems(objO.FIND(1));
    }
    private void CreateGridMesas(){
        btnMesa = new Button[mesaDAO.Count()];
        int totalMesas = mesaDAO.Count();
        int columnasPorFila = 3;
        int filasNecesarias = (int) Math.ceil((double) totalMesas / columnasPorFila);

        int count = 0;
        for (int fila = 0; fila < filasNecesarias; fila++) {
            for (int columna = 0; columna < columnasPorFila; columna++) {
                if (count < totalMesas) {
                    btnMesa[count] = new Button("Mesa " + (count + 1));
                    gdpMesas.add(btnMesa[count], columna, fila);
                    count++;
                } else {
                    break;
                }
            }
        }
    }
    private void CreateGridCategorias() {
        ObservableList<CategoriaDAO> categorias = categoriaDAO.SELECT();
        btnCategoria = new Button[categorias.size()];
        int columnasPorFila = 3;
        int filasNecesarias = (int) Math.ceil((double) categorias.size() / columnasPorFila);

        int count = 0;
        for (int fila = 0; fila < filasNecesarias; fila++) {
            for (int columna = 0; columna < columnasPorFila; columna++) {
                if (count < categorias.size()) {
                    CategoriaDAO categoria = categorias.get(count);
                    btnCategoria[count] = new Button(categoria.getNombre());
                    final int catId = categoria.getIdCategoria();

                    btnCategoria[count].setOnAction(e -> {
                        tbvProductos.setItems(productoDAO.SELECT_BY_CATEGORIA(catId));
                    });

                    gdpCategorias.add(btnCategoria[count], columna, fila);
                    count++;
                } else {
                    break;
                }
            }
        }
    }

    private void CreateProductosTable() {
        tbvProductos = new TableView<>();

        TableColumn<ProductoDAO, String> tbcNombre = new TableColumn<>("Producto");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_producto"));

        TableColumn<ProductoDAO, String> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ProductoDAO, String> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        tbvProductos.getColumns().addAll(tbcNombre, tbcPrecio, tbcCosto);

        VBox vbox = new VBox(tbvProductos);
        borderPane.setRight(vbox);
    }
}
