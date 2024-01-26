/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author inigo
 */
public class AyudaControllerSingletone {
    private static final AyudaControllerSingletone INSTANCE = new AyudaControllerSingletone();

    private AyudaControllerSingletone() {
    }

    public static AyudaControllerSingletone getInstance() {
        return INSTANCE;
    }

    public void mostrarVentanaAyudaEvento() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/view/AyudaEvento.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Eventos");
        stage.setScene(scene);
        stage.show();
    }

    public void mostrarVentanaAyudaPerfil() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/view/AyudaPerfil.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Perfil");
        stage.setScene(scene);
        stage.show();
    }

}
