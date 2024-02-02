/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Usuario;
import exceptions.IncorrectCredentialsException;
import exceptions.InvalidFormatException;
import exceptions.NotCompletedException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.GenericType;
import service.ClienteFactoria;
import service.ClienteInterface;
import service.UsuarioFactoria;
import service.UsuarioInterface;

/**
 * Controlador de la ventana de recuperar contraseña a la que se accede desde el
 * sign In
 *
 * @author David
 */
public class ControllerRecuperarContrasenia {

    private Stage stage;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnEnviar;

    /**
     * Método init stage que inicia el stage y declara los métodos a los
     * botones.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //Métodos de los botones y al cerrar la ventana.
        stage.setOnCloseRequest(this::handleCloseWindow);
        btnCancelar.setOnAction(this::handleVolver);
        btnEnviar.setOnAction(this::handleEnviar);

    }

    /**
     * Método que settea el Stage.
     *
     * @param stage escenario.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Método que solicita confirmación del usuario antes de cerrar la ventana.
     *
     * @param windowEvent evento de la ventana.
     */
    @FXML
    public void handleCloseWindow(WindowEvent windowEvent) {
        windowEvent.consume();

        //Creamos la alerta
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Quieres cerrar la ventana?");
        alerta.setHeaderText(null);

        //La mostramos
        Optional<ButtonType> accion = alerta.showAndWait();
        if (accion.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Método que vuelve a la anterior ventana, en este caso SignIn.
     *
     * @param actionEvent evento de la acción.
     */
    @FXML
    public void handleVolver(ActionEvent actionEvent) {
        try {
            //Cargamos el fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root;

            root = (Parent) loader.load();
            //Creamos un objeto del controlador que queremos abrir.
            ControllerSignIn viewController = ((ControllerSignIn) loader.getController());
            //Llamamos a los métodos necesarios del controlador nuevo.
            viewController.setStage(stage);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que se encarga de generar una nueva contraseña para el usuario e
     * informarle enviandole un email al correo que introduzca el propio
     * usuario.
     *
     * @param actionEvent
     */
    @FXML
    public void handleEnviar(ActionEvent actionEvent) {
        //Obtenemos el email.
        String email = txtFieldEmail.getText();
        String passNueva;
        try {
            if (email == null) {
                throw new NotCompletedException("Debes introducir el email!!");
            } else if (!checkEmailFormat(email)) {
                throw new InvalidFormatException("El email no tiene el formato correcto!!");
            }

            //Buscamos si el correo introducido pertenece a algun usuario.
            UsuarioInterface ui = UsuarioFactoria.getUserInterface();
            Usuario us = ui.findByCorreo_XML(new GenericType<Usuario>() {
            }, email);
            System.out.println(us.toString());
            if (us == null) {
                throw new IncorrectCredentialsException("El email introducido no pertenece a ninguna cuenta!!");
            }

            //Generamos la nueva contraseña para el usuario y se la asignamos.
            passNueva = ui.olvidarContrasenia(new GenericType<String>() {
            }, email);
            System.out.println(passNueva);
            us.setPassword(passNueva);
            System.out.println(us.toString());

            //Actualizamos la información del usuario con la nueva contraseña.
            ClienteInterface ci = ClienteFactoria.getClienteInterface();
            ci.edit_XML(us, us.getIdUsuario().toString());

            //Informamos al usuario del cambio.
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Se ha enviado su nueva contraseña al email!!", ButtonType.OK);
            alerta.setHeaderText(null);
            alerta.show();

            handleVolver(actionEvent);

        } catch (NotCompletedException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerRecuperarContrasenia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectCredentialsException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerRecuperarContrasenia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ControllerRecuperarContrasenia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para comprobar que el formato del email es correcto.
     *
     * @param texto Correo introducido por el usuario.
     * @return Variable boolean para informar si tiene o no el formato correcto.
     */
    private boolean checkEmailFormat(String texto) {
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._]{3,}+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");

        Matcher matcher = pattern1.matcher(texto);
        return matcher.find();
    }

    /**
     * Método que genera un número aleatorio entre dos rangos.
     *
     * @param minimo El valor mínimo que puede tener el número
     * @param maximo El valor máximo que puede tener el número.
     * @return Número generado aleatoriamente.
     */
    public static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

}
