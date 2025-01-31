module org.example.tap2025 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tap2025 to javafx.fxml;
    exports org.example.tap2025;
}