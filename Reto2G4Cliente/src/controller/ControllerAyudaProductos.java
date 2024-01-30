/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Gonzalo
 */
public class ControllerAyudaProductos {
    private static final ControllerAyudaProductos INSTANCE = new ControllerAyudaProductos();

    private ControllerAyudaProductos() {
    }

    public static ControllerAyudaProductos getInstance() {
        return INSTANCE;
    }

    @FXML
    public void mostrarVentanaAyuda(ActionEvent event) {
        WebView webView = new WebView();

        webView.getEngine().load(getClass().getResource("C:\\Users\\inigo\\Desktop\\MarketMaker\\G4Reto2Servidor\\G4Reto2Cliente\\G4Reto2Cliente\\Reto2G4Cliente\\src\\view\\Ayudas.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);

        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda");
        stage.setScene(scene);
        stage.show();
    }
}
