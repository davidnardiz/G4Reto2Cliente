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
import exceptions.NotCompletedException;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
import javax.ws.rs.core.GenericType;
import service.ClienteFactoria;
import service.ClienteInterface;
import service.TiendaFactoria;
import service.TiendaInterface;

/**
 * Controlador de la ventana crearTienda.
 *
 * @author David
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

    /**
     * Método initStage del controlador.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //Cargamos la comboBox con los datos y le asociamos el método al botón.
        comboBoxTipoPago.getItems().setAll(FXCollections.observableArrayList(TipoPago.values()));
        btnCrearTienda.setOnAction(this::handleCreateTienda);
    }

    /**
     * Método que settea el stage.
     *
     * @param stage El stage
     * @param usuario El usuario que ha iniciado sesión en la aplicación.
     */
    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    /**
     * Método que crea una nueva tienda en la base de datos con los datos
     * introducidos en el formulario.
     *
     * @param actionEvent
     */
    @FXML
    public void handleCreateTienda(ActionEvent actionEvent) {
        //Obtenemos los datos del formulario.
        String nombre = txtFieldNombre.getText();
        String descripcion = txtFieldDescripcion.getText();
        String espacio = txtFieldEspacio.getText();
        TipoPago tipoPago = (TipoPago) comboBoxTipoPago.getValue();

        try {
            //Comprobamos si ha rellenado todos los datos.
            if (nombre.isEmpty() || descripcion.isEmpty() || espacio.isEmpty() || tipoPago == null || datePickerFechaCreacion.getValue() == null) {
                throw new NotCompletedException("Debes rellenar todos los datos!!");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(datePickerFechaCreacion.getValue().toString());

            //Comprobamos el formato de los datos obtenidos.
            if (!checkNameFormat(nombre)) {
                throw new InvalidFormatException("Debes introducir bien el nombre!!");
            } else if (!checkDescriptionFormat(descripcion)) {
                throw new InvalidFormatException("Debes introducir bien la descripción!!");
            } else if (!checkSpaceFormat(espacio)) {
                throw new InvalidFormatException("Debes introducir bien el espacio!!");
            } else if (!checkTypeFormat(tipoPago)) {
                throw new InvalidFormatException("Debes introducir bien el tipo de pago!!");
            } else if (!checkDateFormat(fechaCreacion)) {
                throw new InvalidFormatException("Debes introducir bien la fecha de creacion!!");
            }

            //Creamos una nueva tienda y le asignamos los datos.
            Tienda tienda = new Tienda();
            tienda.setNombre(nombre);
            tienda.setDescripcion(descripcion);
            tienda.setEspacio(Float.parseFloat(espacio));
            tienda.setFechaCreacion(fechaCreacion);
            tienda.setTipoPago(tipoPago);
            tienda.setCliente((Cliente) usuario);

            //LLamamos al metodo correspondiente que se encarga de crear la tienda
            TiendaInterface ti = TiendaFactoria.getTiendaInterface();
            ti.create_XML(tienda);
            List<Tienda> tiendas = ti.findAll_XML(new GenericType<List<Tienda>>() {
            });
            tienda.setIdTienda(tiendas.get(tiendas.size() - 1).getIdTienda());

            //Abrimos la ventana principal.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
            Parent root;
            root = (Parent) loader.load();

            ControllerPrincipal viewController = ((ControllerPrincipal) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.setTiendaACliente(tienda);
            viewController.initStage(root);

            //Informamos al usuario que la tienda se ha creado correctamente.
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
        } catch (NotCompletedException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, ex.getMessage());
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerCrearTienda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para comprobar el formato del nombre.
     *
     * @param nombre el nombre introducido por el usuario.
     * @return boolean si tiene o no el formato correcto.
     */
    private boolean checkNameFormat(String nombre) {
        return nombre.length() > 6;
    }

    /**
     * Método para comprobar el formato de la descripcion.
     *
     * @param descripcion la descripcion introducida por el usuario.
     * @return boolean si tiene o no el formato correcto.
     */
    private boolean checkDescriptionFormat(String descripcion) {
        return descripcion.length() > 15;
    }

    /**
     * Método para comprobar el formato del espacio.
     *
     * @param espacio el espacio introducido por el usuario.
     * @return boolean si tiene o no el formato correcto.
     */
    private boolean checkSpaceFormat(String espacio) {
        return Float.parseFloat(espacio) > 0;
    }

    /**
     * Método para comprobar el formato de la fecha.
     *
     * @param fechaCreacion la fecha introducida por el usuario.
     * @return boolean si tiene o no el formato correcto.
     */
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

    /**
     * Método para comprobar el tipo de pago.
     *
     * @param tipoPago el tipo de pago introducido por el usuario.
     * @return boolean si tiene o no el formato correcto.
     */
    private boolean checkTypeFormat(TipoPago tipoPago) {
        return tipoPago != null;
    }

}
