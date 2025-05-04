package org.example.tap2025.vistas;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private HBox hboxMOP;
    private Scene escena;
    private Button btnOrdenes, btnEmpleados;
    private Button[] btnMesa;
    private Button[] btnCategoria;
    private GridPane gdpMesas;
    private BorderPane borderPane;
    private TableView<OrdenDAO> tbvOrden;
    private GridPane gdpCategorias;
    private CategoriaDAO categoriaDAO;
    private GridPane gdpProductos;
    private ProductoDAO productoDAO;

    public OrdenesRestaurantes() {
        CrearUI();
        this.setTitle("Ordenes Restaurante");
        this.setScene(escena);
        this.setMinHeight(800);
        this.setMinWidth(1000);
        this.show();
    }

    private void CrearUI() {
        btnOrdenes = new Button("Ordenes");
        btnEmpleados = new Button("Empleados");
        categoriaDAO = new CategoriaDAO();
        gdpMesas = new GridPane();
        gdpCategorias = new GridPane();
        productoDAO = new ProductoDAO();
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
        hBoxOptions.getChildren().addAll(btnOrdenes, btnEmpleados);
        borderPane = new BorderPane();
        hboxMOP = new HBox();
        hboxMOP.getChildren().addAll(gdpMesas,tbvOrden,gdpCategorias);
        borderPane.setTop(hBoxOptions);
        borderPane.setCenter(hboxMOP);

        escena = new Scene(borderPane);
    }

    private void CreateTableOrden() {
        tbvOrden = new TableView<>();
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
        tbvOrden.getColumns().addAll(tbcID, tbcFecha, tbcTotal, tbcCliente, tbcNomesa, tbcFinalizar);
        tbvOrden.resize(25,25);
    }

    private void CreateGridMesas() {
        MesaDAO mesaDAO = new MesaDAO();
        btnMesa = new Button[mesaDAO.Count()];
        int totalMesas = mesaDAO.Count();
        int columnasPorFila = 3;
        int filasNecesarias = (int) Math.ceil((double) totalMesas / columnasPorFila);
        int count = 0;
        for (int fila = 0; fila < filasNecesarias; fila++) {
            for (int columna = 0; columna < columnasPorFila; columna++) {
                if (count < totalMesas) {
                    final int mesaId = count + 1;
                    btnMesa[count] = new Button("Mesa " + mesaId);
                    btnMesa[count].setOnAction(e -> {
                        UpdateTableOrden(mesaId);
                    });
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
                    Button btnCat = new Button(categoria.getNombre());
                    final int catId = categoria.getIdCategoria();
                    btnCat.setOnAction(e -> {
                        CreateGridProductos(catId);
                    });
                    gdpCategorias.add(btnCat, columna, fila);
                    count++;
                } else {
                    break;
                }
            }
        }
    }

    private void CreateGridProductos(int categoriaId) {
        ObservableList<ProductoDAO> productos = productoDAO.SELECT_BY_CATEGORIA(categoriaId);
        gdpProductos = new GridPane();
        int columnasPorFila = 3;
        int filasNecesarias = (int) Math.ceil((double) productos.size() / columnasPorFila);
        int count = 0;
        for (int fila = 0; fila < filasNecesarias; fila++) {
            for (int columna = 0; columna < columnasPorFila; columna++) {
                if (count < productos.size()) {
                    ProductoDAO producto = productos.get(count);
                    Button btnProd = new Button(producto.getNombre_producto());
                    gdpProductos.add(btnProd, columna, fila);
                    count++;
                } else {
                    break;
                }
            }
        }
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setOnAction(e -> borderPane.setRight(gdpCategorias));
        VBox vboxProductos = new VBox(gdpProductos, btnRegresar);
        vboxProductos.setSpacing(10);
        borderPane.setRight(vboxProductos);
    }

    private void UpdateTableOrden(int mesaId) {
        OrdenDAO ordenDAO = new OrdenDAO();
        tbvOrden.setItems(ordenDAO.FIND(mesaId));
        tbvOrden.refresh();
    }
}
