/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g4cliente;

import controller.ControllerPrincipal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Gonzalo
 */
public class Reto2G4Cliente extends Application {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // System.out.println(greeting);
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/principal.fxml"));
            Parent root = (Parent) loader.load();
            //Get controller for graph 
            ControllerPrincipal control
                    = ((ControllerPrincipal) loader.getController());
            //Set greeting to be used in JavaFX view controller
            control.setStage(primaryStage);
            control.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(Reto2G4Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
