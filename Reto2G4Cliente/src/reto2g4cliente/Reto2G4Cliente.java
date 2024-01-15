/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g4cliente;

import controller.ControllerEventos;
import controller.ControllerSignIn;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Gonzalo
 */
public class Reto2G4Cliente extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // System.out.println(greeting);
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/Eventos.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller for graph
            ControllerEventos viewController
                    = ((ControllerEventos) loader.getController());
            //Set greeting to be used in JavaFX view controller
            viewController.setStage(primaryStage);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(Reto2G4Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
