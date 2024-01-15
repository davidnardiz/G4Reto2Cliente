/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Usuario;
import exceptions.InvalidFormatException;
import exceptions.NotCompleteException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import service.UsuarioFactoria;
import service.UsuarioInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerSignIn {

    private Stage stage;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private PasswordField passField;
    @FXML
    private Hyperlink hplNoCuenta;
    @FXML
    private Hyperlink hplRecuperarPass;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        txtFieldEmail.setText("davidavid@gmail.com");
        passField.setText("contraco");

        stage.setOnCloseRequest(this::handleCloseWindow);

        //Método para iniciar sesión
        btnIniciarSesion.setOnAction(this::handleSignIn);

        //Método para abrir el registrarse
        hplNoCuenta.setOnAction(this::handleOpenSignUp);

        //Método para abrir el registrarse
        //hplRecuperarPass.setOnAction(this::handleRecoverPass);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Método que solicita confirmación antes de cerrar la ventana cuando se
     * pulsa la x de la parte superior derecha.
     *
     * @param windowEvent
     */
    @FXML
    public void handleCloseWindow(WindowEvent windowEvent) {
        windowEvent.consume();

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Quieres cerrar la ventana?");
        alerta.setHeaderText(null);

        Optional<ButtonType> accion = alerta.showAndWait();
        if (accion.get() == ButtonType.OK) {
            Platform.exit();
        }

    }

    @FXML
    public void handleSignIn(ActionEvent actionEvent) {
        String texto = txtFieldEmail.getText();
        String pass = passField.getText();

        try {
            if (texto == null || pass == null) {
                throw new NotCompleteException("Error de inicio de sesión: debes rellenar todos campos!!");
            } else if (!checkEmailFormat(texto) || !checkPassFormat(pass)) {
                throw new InvalidFormatException("Error de inicio de sesión: has introducido algun dato mal!!");
            }
            /*
            UsuarioInterface ui = UsuarioFactoria.getUserInterface();
            ui.iniciarSesion_XML(new GenericType<Usuario>() {
            }, texto, pass);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
            Parent root;
            root = (Parent) loader.load();

            ControllerPrincipal viewController = ((ControllerPrincipal) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);
             */

            if (texto.equalsIgnoreCase("davidavid@gmail.com") && pass.equalsIgnoreCase("contraco")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Tiendas.fxml"));
                Parent root;
                root = (Parent) loader.load();

                ControllerTiendas viewController = ((ControllerTiendas) loader.getController());
                viewController.setStage(stage);
                viewController.initStage(root);
            }

        } catch (NotCompleteException ex) {
            Logger.getLogger(ControllerSignIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ControllerSignIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControllerSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Abrir la ventana signUp y cerrar la ventana signIn.
     *
     * @param actionEvent
     */
    @FXML
    public void handleOpenSignUp(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signUp.fxml"));

            Parent root = loader.load();

            ControllerSignUp viewController = ((ControllerSignUp) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleRecoverPass(WindowEvent windowEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecuperarContrasenia.fxml"));

            Parent root = loader.load();

            ControllerRecuperarContrasenia viewController = ((ControllerRecuperarContrasenia) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkEmailFormat(String texto) {
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._]{3,}+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");

        Matcher matcher = pattern1.matcher(texto);
        return matcher.find();
    }

    private boolean checkPassFormat(String pass) {
        return pass.length() >= 8;
    }
}
