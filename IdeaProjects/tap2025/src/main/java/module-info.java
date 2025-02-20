module org.example.tap2025 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tap2025 to javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    exports org.example.tap2025;
    requires mysql.connector.j;
    requires java.sql;
}