/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Usuario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * Esta clase es el controlador de la ventana perfil. Permite al usuario
 * visualizar sus datos de la cuenta y cambiar su contraseña mediante un
 * formulario.
 *
 * @author Iñigo
 */
public class ControllerPerfil {

    private Stage stage;

    private Usuario usuario;

    @FXML
    private MenuItem menuItemAyuda;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnAceptar;
    @FXML
    private MenuItem miCerrarSesion;
    @FXML
    private MenuItem miPrincipal;
    @FXML
    private MenuItem miProductos;
    @FXML
    private MenuItem miEventos;
    @FXML
    private MenuItem miTiendas;
    @FXML
    private MenuItem miPerfil;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelFecha;
    @FXML
    private PasswordField passwordFieldNuevaContrasenia;
    @FXML
    private PasswordField passwordFieldRepetirContrasenia;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        miCerrarSesion.setOnAction(this::handleCerrarSesion);
        miPrincipal.setOnAction(this::handleAbrirInicio);
        miProductos.setOnAction(this::handleAbrirProductos);
        miEventos.setOnAction(this::handleAbrirEventos);
        miTiendas.setOnAction(this::handleAbrirTiendas);
        miPerfil.setOnAction(this::handleAbrirPerfil);
        btnVolver.setOnAction(this::handleVolver);
        btnAceptar.setOnAction(this::handleCambiarContrasenia);
        menuItemAyuda.setOnAction(this::handleAyuda);

        cargarDatosUsuario();

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

    /**
     * Maneja el evento de cierre de la ventana, mostrando una alerta de
     * confirmación. Si el usuario elige cerrar la ventana, se cierra la
     * aplicación.
     *
     * @param windowEvent El evento de cierre de la ventana.
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
    private void handleVolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
            Parent root = loader.load();

            ControllerPrincipal principalController = loader.getController();
            principalController.setStage(stage, usuario);
            principalController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleCambiarContrasenia(ActionEvent event) {
        String nuevaContrasenia = passwordFieldNuevaContrasenia.getText();
        String repetirContrasenia = passwordFieldRepetirContrasenia.getText();

        if (nuevaContrasenia.equals(repetirContrasenia)) {
            usuario.setPassword(nuevaContrasenia);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cambiar Contraseña");
            alert.setHeaderText(null);
            alert.setContentText("Contraseña cambiada.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Las contraseñas no coinciden. Por favor, inténtalo de nuevo.");
            alert.showAndWait();
        }
    }

    private void cargarDatosUsuario() {
        String nombreUsuario = usuario.getNombre();
        String emailUsuario = usuario.getCorreo();
        Date fechaUsuario = usuario.getFechaNacimiento();

        labelNombre.setText(nombreUsuario);
        labelEmail.setText(emailUsuario);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = dateFormat.format(fechaUsuario);
        labelFecha.setText(fechaFormateada);

    }

    /**
     * Maneja el evento de ayuda en la ventana de perfil. Este método invoca el
     * método {@link ControllerAyudas#mostrarVentanaAyudaPerfil()} para mostrar
     * una ventana de ayuda específica para la interfaz de perfil.
     *
     * @param event El evento de acción que desencadena esta operación.
     * @see ControllerAyudas#mostrarVentanaAyudaPerfil()
     */
    @FXML
    public void handleAyuda(ActionEvent event) {
        ControllerAyudas.getInstance().mostrarVentanaAyudaPerfil();
    }


}
