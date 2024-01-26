/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Producto;
import entities.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import service.ProductoFactoria;
import service.ProductoInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerProductos {

    private Stage stage;
    private Usuario usuario;
    @FXML
    private MenuItem menuItemCerrarSesion;
    @FXML
    private MenuItem menuItemPrincipal;
    @FXML
    private MenuItem menuItemProductos;
    @FXML
    private MenuItem menuItemEventos;
    @FXML
    private MenuItem menuItemPerfil;
    @FXML
    private MenuItem menuItemTiendas;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        menuItemCerrarSesion.setOnAction(this::handleCerrarSesion);
        menuItemPrincipal.setOnAction(this::handleAbrirInicio);
        menuItemProductos.setOnAction(this::handleAbrirProductos);
        menuItemEventos.setOnAction(this::handleAbrirEventos);
        menuItemTiendas.setOnAction(this::handleAbrirTiendas);
        menuItemPerfil.setOnAction(this::handleAbrirPerfil);
    }

    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    @FXML
    public void handleAbrirTiendas(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Tiendas.fxml"));
            Parent root = loader.load();

            ControllerTiendas productController = ((ControllerTiendas) loader.getController());
            productController.setStage(stage, usuario);
            productController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void handleCerrarSesion(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root = (Parent) loader.load();
            ControllerSignIn viewController = ((ControllerSignIn) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleAbrirInicio(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
            Parent root = loader.load();
            ControllerPrincipal viewController = ((ControllerPrincipal) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleAbrirProductos(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Productos.fxml"));
            Parent root = loader.load();
            ControllerProductos viewController = ((ControllerProductos) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleAbrirEventos(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Eventos.fxml"));
            Parent root = loader.load();
            ControllerEventos viewController = ((ControllerEventos) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleAbrirPerfil(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Perfil.fxml"));
            Parent root = loader.load();
            ControllerPerfil viewController = ((ControllerPerfil) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
