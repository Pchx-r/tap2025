package org.example.tap2025.vistas;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;


public class VentasRestaurante extends Stage {

    private Panel pnlRestaurante;
    private Scene escena;

    public VentasRestaurante() {
        CreateUI();
        this.setTitle("Restaurante");
        this.setScene(escena);
        this.show();
    }

    public void CreateUI(){
        pnlRestaurante=new Panel("Tacos el inge.");
        pnlRestaurante.getStyleClass().add("panel-danger");
        escena = new Scene(pnlRestaurante, 300, 200);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }
}
