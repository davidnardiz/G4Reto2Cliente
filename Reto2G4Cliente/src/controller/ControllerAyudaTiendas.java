/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author David
 */
public class ControllerAyudaTiendas {

    private Stage stage;
    private WebView webView;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
