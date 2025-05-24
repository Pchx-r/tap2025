package org.example.tap2025.vistas;


import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.example.tap2025.modelos.OrdenProductoDAO;
import org.example.tap2025.modelos.ProductoDAO;
import org.example.tap2025.modelos.EmpleadoDAO;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import java.awt.image.BufferedImage;


public class Graficas extends Stage {
    private HBox hbox;
    private VBox vbox;
    private Button btnPDF;
    private Scene escena;
    private OrdenProductoDAO ordenProductoDAO;
    private ProductoDAO productoDAO;
    private EmpleadoDAO empleadoDAO;

    public Graficas() {
        ordenProductoDAO = new OrdenProductoDAO();
        productoDAO = new ProductoDAO();
        empleadoDAO = new EmpleadoDAO();
        CrearUI();
        this.setTitle("Graficas de Ventas");
        this.setScene(escena);
        this.setMinHeight(600);
        this.setMinWidth(800);
        this.show();
    }

    private void CrearUI() {
        vbox = new VBox();
        hbox = new HBox();
        btnPDF = new Button("PDF");
        BarChart<String, Number> chart1 = createProductosMasVendidosChart();
        LineChart<String, Number> chart2 = createVentasPorDiaChart();
        BarChart<String, Number> chart3 = createEmpleadoMasVentasChart();
        hbox.getChildren().addAll(chart1, chart2, chart3);
        vbox.getChildren().addAll(hbox, btnPDF);
        escena = new Scene(vbox);

        btnPDF.setOnAction(event -> {
            try{
                WritableImage img1 = chart1.snapshot(new SnapshotParameters(), null);
                WritableImage img2 = chart2.snapshot(new SnapshotParameters(), null);
                WritableImage img3 = chart3.snapshot(new SnapshotParameters(), null);

                BufferedImage bImg1 = SwingFXUtils.fromFXImage(img1,null);
                BufferedImage bImg2 = SwingFXUtils.fromFXImage(img2,null);
                BufferedImage bImg3 = SwingFXUtils.fromFXImage(img3,null);

                File temp1 = File.createTempFile("chart1",".png");
                File temp2 = File.createTempFile("chart2",".png");
                File temp3 = File.createTempFile("chart3",".png");
                ImageIO.write(bImg1, "png",temp1);
                ImageIO.write(bImg2, "png",temp2);
                ImageIO.write(bImg3, "png",temp3);

                PDDocument doc = new PDDocument();
                for (File imgFile : new File[]{temp1,temp2,temp3}) {
                    PDPage page = new PDPage(PDRectangle.LETTER);
                    doc.addPage(page);
                    PDImageXObject pdImage = PDImageXObject.createFromFileByContent(imgFile, doc);
                    PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                    contentStream.drawImage(pdImage, 50, 250,500,300);
                    contentStream.close();
                }
                doc.save("graficas.pdf");
                doc.close();
                if (Desktop.isDesktopSupported()){
                    Desktop.getDesktop().open(new File("graficas.pdfq"));
                }
            }catch (Exception e){

            }
        });
    }

    private BarChart<String, Number> createProductosMasVendidosChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Producto");
        yAxis.setLabel("Cantidad Vendida");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Productos Más Vendidos");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas");

        ObservableList<ProductoDAO> productos = productoDAO.SELECT();
        if (productos != null) {
            for (ProductoDAO producto : productos) {
                if (producto != null && producto.getNombre_producto() != null) {
                    int cantidadVendida = ordenProductoDAO.getCantidadVendidaPorProducto(producto.getId_producto());
                    series.getData().add(new XYChart.Data<>(producto.getNombre_producto(), cantidadVendida));
                }
            }
        }



        barChart.getData().add(series);
        return barChart;
    }

    private LineChart<String, Number> createVentasPorDiaChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Fecha");
        yAxis.setLabel("Total Ventas");
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Ventas por Día");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas Diarias");

        ObservableList<OrdenProductoDAO> ordenes = ordenProductoDAO.SELECT();
        if (ordenes != null) {
            for (OrdenProductoDAO orden : ordenes) {
                if (orden != null && orden.getFecha() != null) {
                    double totalVentas = ordenProductoDAO.getTotalVentasPorDia(orden.getFecha());
                    series.getData().add(new XYChart.Data<>(orden.getFecha(), totalVentas));
                }
            }
        }

        lineChart.getData().add(series);
        return lineChart;
    }

    private BarChart<String, Number> createEmpleadoMasVentasChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Empleado");
        yAxis.setLabel("Total Ventas");
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Empleado con Más Ventas Realizadas");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas");

        ObservableList<EmpleadoDAO> empleados = empleadoDAO.SELECT();
        if (empleados != null) {
            for (EmpleadoDAO empleado : empleados) {
                if (empleado != null && empleado.getNombre() != null) {
                    double totalVentas = ordenProductoDAO.getTotalVentasPorEmpleado(empleado.getIdEmpleado());
                    series.getData().add(new XYChart.Data<>(empleado.getNombre(), totalVentas));
                }
            }
        }

        barChart.getData().add(series);
        return barChart;
    }


}
