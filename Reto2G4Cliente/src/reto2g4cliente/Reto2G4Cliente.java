/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g4cliente;

import controller.ControllerPrincipal;
import controller.ControllerSignIn;
import entities.Usuario;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Método que startea la aplicación y carga la primera ventana.
 *
 * @author David
 */
public class Reto2G4Cliente extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            //Cargar el fxml.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root;
            root = (Parent) loader.load();

            //Crea el controlador de la ventana.
            ControllerSignIn viewController = ((ControllerSignIn) loader.getController());
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
