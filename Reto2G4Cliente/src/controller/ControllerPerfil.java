/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static encriptation.Asimetrico.encriptar;
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
import javax.ws.rs.core.GenericType;
import service.ClienteFactoria;
import service.ClienteInterface;
import service.UsuarioFactoria;
import service.UsuarioInterface;

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

    /**
     * Método init stage que inicia la ventana.
     *
     * @param root el root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //Declaracion de los métodos de los botones
        miCerrarSesion.setOnAction(this::handleCerrarSesion);
        miPrincipal.setOnAction(this::handleAbrirInicio);
        miProductos.setOnAction(this::handleAbrirProductos);
        miEventos.setOnAction(this::handleAbrirEventos);
        miTiendas.setOnAction(this::handleAbrirTiendas);
        miPerfil.setOnAction(this::handleAbrirPerfil);
        btnVolver.setOnAction(this::handleAbrirInicio);
        btnAceptar.setOnAction(this::handleCambiarContrasenia);
        menuItemAyuda.setOnAction(this::handleAyuda);

        //Cargamos los datos del usuario.
        cargarDatosUsuario();

    }

    /**
     * Método que settea el stage y recibe el usuario que ha iniciado sesión.
     *
     * @param stage el stage
     * @param usuario el usuario
     */
    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    /**
     * Maneja el evento de abrir la ventana de tiendas. Carga la ventana desde
     * el archivo FXML correspondiente y configura el controlador de la ventana
     * de tiendas. Finalmente, muestra la ventana.
     *
     * @param actionEvent El evento que desencadena la apertura de la ventana de
     * tiendas.
     */
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

    /**
     * Maneja el evento de cerrar sesión. Carga la interfaz de inicio de sesión
     * (signIn.fxml) utilizando un FXMLLoader y establece el controlador
     * correspondiente. Después, inicializa y muestra la nueva ventana de inicio
     * de sesión.
     *
     * @param event El evento que desencadena la llamada a este método.
     */
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

    /**
     * Maneja el evento de abrir la ventana de Inicio. Carga la ventana
     * principal
     *
     * @param actionEvent Evento que desencadenó la apertura de la ventana
     * principal.
     */
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

    /**
     * Maneja el evento de abrir la ventana de productos. Carga la vista de
     * productos, establece el escenario y el controlador, y muestra la nueva
     * ventana.
     *
     * @param actionEvent Evento que desencadenó la apertura de la ventana de
     * productos.
     */
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

    /**
     * Maneja el evento de abrir la ventana de eventos. Carga la vista de
     * eventos, establece el escenario y el controlador, y muestra la nueva
     * ventana.
     *
     * @param actionEvent Evento que desencadenó la apertura de la ventana de
     * productos.
     */
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

    /**
     * Maneja el evento de abrir la ventana de perfil. Carga la vista de perfil
     * utilizando, establece el escenario y el controlador, y muestra la nueva
     * ventana.
     *
     * @param actionEvent Evento que desencadenó la apertura de la ventana de
     * perfil.
     */
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

    /**
     * Método que cambia la contraseña del usuario por la que introudce y le
     * informa mediante un email.
     *
     * @param event
     */
    @FXML
    private void handleCambiarContrasenia(ActionEvent event) {
        //Obtenemos las dos conraseñas.
        String nuevaContrasenia = passwordFieldNuevaContrasenia.getText();
        String repetirContrasenia = passwordFieldRepetirContrasenia.getText();

        //Comparamos si son iguales.
        if (nuevaContrasenia.equals(repetirContrasenia)) {
            //La actualizamos y informamos al usuario.
            usuario.setPassword(encriptar(nuevaContrasenia));

            UsuarioInterface ci = UsuarioFactoria.getUserInterface();
            ci.cambiarContrasenia(new GenericType<String>() {
            }, usuario.getCorreo(), encriptar(nuevaContrasenia));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cambiar Contraseña");
            alert.setHeaderText(null);
            alert.setContentText("Contraseña cambiada.");
            alert.showAndWait();
        } else {
            //Informamos del error.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Las contraseñas no coinciden. Por favor, inténtalo de nuevo.");
            alert.showAndWait();
        }
    }

    /**
     * Método que ccarga los datos del usuario.
     */
    private void cargarDatosUsuario() {
        //Obtenemos los datos del usuario y los mostramos.
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
