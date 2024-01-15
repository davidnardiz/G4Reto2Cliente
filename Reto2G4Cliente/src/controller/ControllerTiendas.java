/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Tienda;
import entities.TipoPago;
import exceptions.InvalidFormatException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import service.TiendaFactoria;
import service.TiendaInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerTiendas {

    private Stage stage;
    @FXML
    private TextField txtFieldNombre;
    @FXML
    private TextField txtFieldEspacio;
    @FXML
    private TextField txtFieldDescripcion;
    @FXML
    private ComboBox cbTipoPago;
    @FXML
    private DatePicker dpFechaCreacion;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ComboBox cbFiltros;
    @FXML
    private TextField txtFieldFiltro1;
    @FXML
    private TextField txtFieldFiltro2;
    @FXML
    private Button btnFiltrar;
    @FXML
    private TableView<Tienda> tablaTiendas;
    @FXML
    private TableColumn<Tienda, Integer> cmnId;
    @FXML
    private TableColumn<Tienda, Integer> cmnNombre;
    @FXML
    private TableColumn<Tienda, Integer> cmnDescripcion;
    @FXML
    private TableColumn<Tienda, Integer> cmnTipoPago;
    @FXML
    private TableColumn<Tienda, Integer> cmnEspacio;
    @FXML
    private TableColumn<Tienda, Integer> cmnFechaCreacion;
    @FXML
    private TableColumn<Tienda, Integer> cmnCliente;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(this::handleCloseWindow);

        btnCrear.setOnAction(this::handleCreateTienda);
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

    /**
     * Método que solicita confirmación antes de cerrar la ventana cuando se
     * pulsa la x de la parte superior derecha.
     *
     * @param windowEvent
     */
    @FXML
    public void handleCreateTienda(WindowEvent windowEvent) {
        String nombre = txtFieldNombre.getText();
        String descripcion = txtFieldDescripcion.getText();
        float espacio = Integer.parseInt(txtFieldEspacio.getText());
        Date fechaCreacion = null;/*LocalDate.now();dpFechaCreacion.getDate();*/
        TipoPago tipoPago = null;/*cbTipoPago.getSelectedItem();*/

        try {
            if (checkNameFormat(nombre) || checkDescriptionFormat(descripcion) || checkSpaceFormat(espacio) || checkDateFormat(fechaCreacion) || checkTypeFormat(tipoPago)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            }

            Tienda tienda = new Tienda();
            tienda.setNombre(nombre);
            tienda.setDescripcion(descripcion);
            tienda.setEspacio(espacio);
            tienda.setFechaCreacion(fechaCreacion);
            tienda.setTipoPago(tipoPago);
            tienda.setCliente(null);

            TiendaInterface ti = TiendaFactoria.getTiendaInterface();
            /*ti.create_XML(new GenericType<Tienda>() {
            }, tienda);
             */
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkNameFormat(String nombre) {
        return nombre.length() < 6;
    }

    private boolean checkDescriptionFormat(String descripcion) {
        return descripcion.length() < 15;
    }

    private boolean checkSpaceFormat(float espacio) {
        return espacio < 0;
    }

    private boolean checkDateFormat(Date fechaCreacion) {
        return fechaCreacion.after(null);
    }

    private boolean checkTypeFormat(TipoPago tipoPago) {
        return tipoPago.toString() == null;
    }

}
