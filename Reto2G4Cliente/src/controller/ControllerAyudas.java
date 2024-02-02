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
 * La clase ControllerAyudasgestiona y muestra ventanas de ayuda relacionadas
 * con diferentes secciones de la aplicación.
 *
 * Cada método en esta clase se encarga de abrir una ventana de ayuda específica
 * cargando un archivo HTML correspondiente en un WebView.
 *
 * La clase sigue el patrón de diseño Singleton, asegurando que solo haya una
 * instancia de ControllerAyudas en la aplicación.
 *
 * @author Iñigo
 */
public class ControllerAyudas {
    private static final ControllerAyudas INSTANCE = new ControllerAyudas();

    private ControllerAyudas() {
    }

    public static ControllerAyudas getInstance() {
        return INSTANCE;
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana eventos.
     */
    public void mostrarVentanaAyudaEvento() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/ayudas/AyudaEvento.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Eventos");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda relacionada con el perfil del usuario.
     */
    public void mostrarVentanaAyudaPerfil() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/ayudas/AyudaPerfil.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Perfil");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda de la ventana principal.
     */
    public void mostrarVentanaAyudaPrincipal() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/ayudas/AyudaPrincipal.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Principal");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana tiendas.
     */
    public void mostrarVentanaAyudaTienda() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/ayudas/AyudaTiendas.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Tiendas");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana productos.
     */
    public void mostrarVentanaAyudaProductos() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/ayudas/AyudaProductos.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Tiendas");
        stage.setScene(scene);
        stage.show();
    }

}
