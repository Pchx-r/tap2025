module org.example.tap2025 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tap2025 to javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    exports org.example.tap2025;
    requires mysql.connector.j;
    requires java.sql;
    requires java.xml.crypto;
    requires org.apache.pdfbox;
    requires javafx.swing;
    requires kernel;
    requires layout;
    opens org.example.tap2025.modelos;
}