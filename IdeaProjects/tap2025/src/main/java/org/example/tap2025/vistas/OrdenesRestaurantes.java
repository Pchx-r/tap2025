package org.example.tap2025.vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2025.componentes.ButtonCellOrden;
import org.example.tap2025.modelos.*;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrdenesRestaurantes extends Stage {
    private HBox hBoxOptions;
    private Scene escena;
    private int mesaSeleccionada;
    private Button btnOrdenes, btnEmpleados, btnAdmProd, btnAdmCategory, btnTicket, btnReservaciones;
    private Button[] btnMesa;
    private Button[] btnCategoria;
    private GridPane gdpMesas;
    private BorderPane borderPane;
    private TableView<OrdenProductoDAO> tbvOrden;
    private GridPane gdpCategorias;
    private CategoriaDAO categoriaDAO;
    private GridPane gdpProductos;
    private ProductoDAO productoDAO;
    private OrdenDAO ordenDAO;
    private OrdenProductoDAO ordenProdDAO;
    private ObservableList<OrdenProductoDAO> ordenes;
    private int id_empleado;

    public OrdenesRestaurantes(int id_empleado) {
        this.id_empleado = id_empleado;
        CrearUI();
        this.setTitle("Ordenes Restaurante");
        this.setScene(escena);
        this.setMinHeight(800);
        this.setMinWidth(1200);
        this.show();
    }

    private void CrearUI() {
        ordenDAO = new OrdenDAO();
        ordenProdDAO = new OrdenProductoDAO();
        ordenes = FXCollections.observableArrayList();
        btnOrdenes = new Button("Ordenes");
        btnEmpleados = new Button("Empleados");
        btnAdmProd = new Button("Administrar Productos");
        btnAdmCategory = new Button("Administrar categorias");
        btnTicket = new Button("Ticket");
        btnReservaciones = new Button("Reservaciones");
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
        btnEmpleados.setOnAction(event -> new PantallaAcceso());
        imv = new ImageView(getClass().getResource("/images/comida.jpg").toString());
        imv.setFitHeight(60);
        imv.setFitWidth(60);
        btnAdmProd.setGraphic(imv);
        btnAdmProd.setOnAction(event -> new PantallaAccesoAlimentos());
        imv = new ImageView(getClass().getResource("/images/añadir.jpg").toString());
        imv.setFitHeight(60);
        imv.setFitWidth(60);
        btnAdmCategory.setGraphic(imv);
        btnAdmCategory.setOnAction(event -> new PantallaAccesoCategorias());
        imv = new ImageView(getClass().getResource("/images/ticket.png").toString());
        imv.setFitHeight(60);
        imv.setFitWidth(60);
        btnTicket.setGraphic(imv);
        btnTicket.setOnAction(event -> new PantallaTicket());
        imv = new ImageView(getClass().getResource("/images/reservaciones.jpg").toString());
        imv.setFitHeight(60);
        imv.setFitWidth(60);
        btnReservaciones.setGraphic(imv);
        btnReservaciones.setOnAction(event -> new Reservaciones());
        hBoxOptions = new HBox();
        hBoxOptions.getChildren().addAll(btnOrdenes, btnEmpleados,btnAdmProd, btnAdmCategory,btnTicket,btnReservaciones);
        borderPane = new BorderPane();
        borderPane.setTop(hBoxOptions);
        borderPane.setLeft(gdpMesas);
        borderPane.setRight(gdpCategorias);
        borderPane.setCenter(tbvOrden);

        escena = new Scene(borderPane);
    }

    private void CreateTableOrden() {
        tbvOrden = new TableView<>(ordenes);
        TableColumn<OrdenProductoDAO, String> tbcID = new TableColumn<>("ID Orden");
        tbcID.setCellValueFactory(new PropertyValueFactory<>("id_orden"));
        TableColumn<OrdenProductoDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<OrdenProductoDAO, String> tbcTotal = new TableColumn<>("Total");
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrdenProductoDAO, String> tbcCliente = new TableColumn<>("Cliente");
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("nomCte"));
        TableColumn<OrdenProductoDAO, String> tbcNomesa = new TableColumn<>("Número de Mesa");
        tbcNomesa.setCellValueFactory(new PropertyValueFactory<>("no_mesa"));
        TableColumn<OrdenProductoDAO, String> tbcFinalizar = new TableColumn<>("Finalizar");
        tbcFinalizar.setCellFactory(new Callback<TableColumn<OrdenProductoDAO, String>, TableCell<OrdenProductoDAO, String>>() {
            @Override
            public TableCell<OrdenProductoDAO, String> call(TableColumn<OrdenProductoDAO, String> ordenProdDAOStringTableColumn) {
                return new ButtonCellOrden("Finalizar") {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            btnCelda.setOnAction(event -> {
                                OrdenProductoDAO orden_producto = getTableView().getItems().get(getIndex());
                                orden_producto.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                orden_producto.UPDATE();
                                getTableView().getItems().remove(getIndex());
                                getTableView().refresh();
                                if (getTableView().getItems().isEmpty()) {
                                    try {
                                        ordenDAO.setFecha( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                        ordenDAO.INSERT();

                                        ordenDAO.UPDATE();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                };
            }
        });
        tbvOrden.getColumns().addAll(tbcID, tbcFecha, tbcTotal, tbcCliente, tbcNomesa, tbcFinalizar);
        tbvOrden.resize(25, 25);
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
                    btnMesa[count].setMinSize(100, 100);
                    btnMesa[count].setOnAction(e -> {
                        mesaSeleccionada = mesaId;
                        try {
                            int idOrdenActivo = ordenDAO.COMPROBANTE(mesaId);
                            ordenDAO.setIdOrden(idOrdenActivo);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
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
                    btnCat.setMinSize(100,100);
                    if(categoria.getImagen() != null){
                        ImageView imv = new ImageView(new Image(new ByteArrayInputStream(categoria.getImagen())));
                        imv.setFitWidth(40);
                        imv.setFitHeight(40);
                        btnCat.setGraphic(imv);
                    }
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
                    if (producto.getImagen() != null) {
                        ImageView imv = new ImageView(new Image(new ByteArrayInputStream(producto.getImagen())));
                        imv.setFitWidth(40);
                        imv.setFitHeight(40);
                        btnProd.setGraphic(imv);
                    }
                    btnProd.setOnAction(event -> {
                        OrdenDAO nuevaOrden = new OrdenDAO();
                        if (ordenProdDAO.COUNT() == 0){
                            nuevaOrden.setTotal(0);
                            nuevaOrden.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                        OrdenProductoDAO nuevaOrdenProd = new OrdenProductoDAO();
                        nuevaOrdenProd.setId_empleado(id_empleado);
                        nuevaOrdenProd.setId_orden(ordenDAO.getIdOrden());
                        nuevaOrdenProd.setIdCte(1);
                        nuevaOrdenProd.setNo_mesa(mesaSeleccionada);
                        nuevaOrdenProd.setTotal(producto.getPrecio());

                        if (nuevaOrdenProd != null ) {
                            ordenes.add(nuevaOrdenProd);
                            nuevaOrdenProd.INSERT(id_empleado, producto.getId_producto());
                            UpdateTableOrden(mesaSeleccionada);
                        }else {
                            System.out.println("error");
                        }
                    });
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
        OrdenProductoDAO ordenProdDAO = new OrdenProductoDAO();
        tbvOrden.setItems(ordenProdDAO.FIND(mesaId));
        tbvOrden.refresh();
    }

}
