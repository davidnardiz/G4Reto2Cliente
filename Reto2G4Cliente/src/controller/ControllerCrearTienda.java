/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cliente;
import entities.Tienda;
import entities.TipoPago;
import entities.Usuario;
import exceptions.InvalidFormatException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.ClienteFactoria;
import service.ClienteInterface;
import service.TiendaFactoria;
import service.TiendaInterface;
import service.UsuarioFactoria;
import service.UsuarioInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerCrearTienda {

    private Stage stage;
    private Usuario usuario;
    @FXML
    private TextField txtFieldNombre;
    @FXML
    private TextField txtFieldDescripcion;
    @FXML
    private TextField txtFieldEspacio;
    @FXML
    private ComboBox comboBoxTipoPago;
    @FXML
    private DatePicker datePickerFechaCreacion;
    @FXML
    private Button btnCrearTienda;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        comboBoxTipoPago.getItems().setAll(FXCollections.observableArrayList(TipoPago.values()));

        btnCrearTienda.setOnAction(this::handleCreateTienda);
    }

    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    /**
     * Método que solicita confirmación antes de cerrar la ventana cuando se
     * pulsa la x de la parte superior derecha.
     *
     * @param actionEvent
     */
    @FXML
    public void handleCreateTienda(ActionEvent actionEvent) {
        String nombre = txtFieldNombre.getText();
        String descripcion = txtFieldDescripcion.getText();
        float espacio = Float.parseFloat(txtFieldEspacio.getText());
        TipoPago tipoPago = (TipoPago) comboBoxTipoPago.getValue();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(datePickerFechaCreacion.getValue().toString());
            /*
            if (!checkNameFormat(nombre) || !checkDescriptionFormat(descripcion) || !checkSpaceFormat(espacio) || checkDateFormat(fechaCreacion) || !checkTypeFormat(tipoPago)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            }
             */
            if (!checkNameFormat(nombre)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            } else if (!checkDescriptionFormat(descripcion)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            } else if (!checkSpaceFormat(espacio)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            } else if (!checkTypeFormat(tipoPago)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            } else if (!checkDateFormat(fechaCreacion)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            }

            Tienda tienda = new Tienda();
            tienda.setNombre(nombre);
            tienda.setDescripcion(descripcion);
            tienda.setEspacio(espacio);
            tienda.setFechaCreacion(fechaCreacion);
            tienda.setTipoPago(tipoPago);

            ((Cliente) usuario).setTienda(tienda);
            TiendaInterface ti = TiendaFactoria.getTiendaInterface();
            ti.create_XML(tienda);

            ClienteInterface ci = ClienteFactoria.getClienteInterface();
            ci.edit_XML(usuario, usuario.getIdUsuario().toString());

            System.out.println(usuario.toString());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
            Parent root;
            root = (Parent) loader.load();

            ControllerPrincipal viewController = ((ControllerPrincipal) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Se ha creado correctamente la tienda!!");
            alerta.setHeaderText(null);
            alerta.show();

        } catch (InvalidFormatException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, ex.getMessage());
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControllerCrearTienda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkNameFormat(String nombre) {
        return nombre.length() > 6;
    }

    private boolean checkDescriptionFormat(String descripcion) {
        return descripcion.length() > 15;
    }

    private boolean checkSpaceFormat(float espacio) {
        return espacio > 0;
    }

    private boolean checkDateFormat(Date fechaCreacion) {
        Date date = null;
        try {
            LocalDate localDate = LocalDate.now();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(localDate.toString());
        } catch (ParseException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaCreacion.before(date);
    }

    private boolean checkTypeFormat(TipoPago tipoPago) {
        return tipoPago != null;
    }

}
