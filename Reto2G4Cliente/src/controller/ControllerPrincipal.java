/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Gonzalo
 */
public class ControllerPrincipal {

    private Stage stage;

    @FXML
    private Button buttonProductos;
    @FXML
    private Button buttonTiendas;
    @FXML
    private Button buttonEventos;

    /**
     * Metodo para inicializar la ventana
     * @param root 
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        buttonProductos.setOnAction(this::handleProductos);
        
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    @FXML
    public void handleProductos(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Productos.fxml"));
            Parent root = loader.load();
            ControllerProductos productController = ((ControllerProductos)loader.getController());
            productController.setStage(stage);
            productController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
