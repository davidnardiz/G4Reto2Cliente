/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cliente;
import entities.Tienda;
import entities.Usuario;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import service.ClienteFactoria;
import service.ClienteInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerPrincipal {

    private Stage stage;
    private Usuario usuario;
    @FXML
    private MenuItem miCerrarSesion;
    @FXML
    private MenuItem miPrincipal;
    @FXML
    private MenuItem miProductos;
    @FXML
    private MenuItem miEventos;
    @FXML
    private MenuItem miPerfil;
    @FXML
    private MenuItem miTiendas;
    @FXML
    private Button buttonProductos;
    @FXML
    private Button buttonTiendas;
    @FXML
    private Button buttonEventos;
    @FXML
    private MenuItem menuItemAyuda;

    /**
     * Metodo para inicializar la ventana
     *
     * @param root el root
     */
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
        menuItemAyuda.setOnAction(this::handleAyuda);

        buttonProductos.setOnAction(this::handleAbrirProductos);
        buttonTiendas.setOnAction(this::handleAbrirTiendas);
        buttonEventos.setOnAction(this::handleAbrirEventos);
    }

    /**
     * Método que settea el stage y carga la información del usuario.
     *
     * @param stage el stage
     * @param usuario el usuario
     */
    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    /**
     * Método que asocia la tienda al cliente.
     *
     * @param tienda la tienda
     */
    public void setTiendaACliente(Tienda tienda) {
        //Actualizamos la informacion del usuario para asignarle la tienda que ha creado.
        System.out.println(tienda.toString());
        System.out.println("Usuario --> " + usuario.toString());
        ((Cliente) usuario).setTienda(tienda);
        System.out.println(usuario.toString());
        ClienteInterface ci = ClienteFactoria.getClienteInterface();
        ci.edit_XML(usuario, usuario.getIdUsuario().toString());
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
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, "Error loading Perfil.fxml", ex);
        }
    }

    /**
     * Maneja el evento de ayuda en la ventana principal. Este método invoca el
     * método {@link ControllerAyudas#mostrarVentanaAyudaPrincipal()} para
     * mostrar una ventana de ayuda específica para la ventana principal.
     *
     * @param event El evento de acción que desencadena esta operación.
     * @see ControllerAyudas#mostrarVentanaAyudaPrincipal()
     */
    @FXML
    public void handleAyuda(ActionEvent event) {
        ControllerAyudas.getInstance().mostrarVentanaAyudaPrincipal();
    }

}
