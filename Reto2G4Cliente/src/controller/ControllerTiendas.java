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
import exceptions.NotSelectedException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Usuario usuario;
    @FXML
    private MenuBar menu;
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
    private MenuItem miAyuda;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem menuItemMenuContextoCrear;
    @FXML
    private MenuItem menuItemMenuContextoModificar;
    @FXML
    private MenuItem menuItemMenuContextoEliminar;
    @FXML
    private TextField txtFieldNombre;
    @FXML
    private TextField txtFieldNombreColumna;
    @FXML
    private TextField txtFieldEspacio;
    @FXML
    private TextField txtFieldEspacioColumna;
    @FXML
    private TextField txtFieldDescripcion;
    @FXML
    private TextField txtFieldDescripcionColumna;
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
    private TableView<Tienda> tbTiendas;
    @FXML
    private TableColumn<Tienda, Integer> cmnId;
    @FXML
    private TableColumn<Tienda, String> cmnNombre;
    @FXML
    private TableColumn<Tienda, String> cmnDescripcion;
    @FXML
    private TableColumn<Tienda, TipoPago> cmnTipoPago;
    @FXML
    private TableColumn<Tienda, Integer> cmnEspacio;
    @FXML
    private TableColumn<Tienda, Date> cmnFechaCreacion;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        //Método cerrar ventana
        stage.setOnCloseRequest(this::handleCloseWindow);

        //Diferencias entre cliente y usuario
        if (usuario instanceof Cliente) {
            btnCrear.setDisable(true);
            btnEliminar.setDisable(true);
        } else {
            btnCrear.setDisable(false);
            btnEliminar.setDisable(false);
        }
        //Menú items del menú bar
        miCerrarSesion.setOnAction(this::handleCerrarSesion);
        miPrincipal.setOnAction(this::handleAbrirInicio);
        miProductos.setOnAction(this::handleAbrirProductos);
        miEventos.setOnAction(this::handleAbrirEventos);
        miPerfil.setOnAction(this::handleAbrirPerfil);
        miAyuda.setOnAction(this::handleAbrirAyuda);
        miAyuda.setOnAction(this::handleAyuda);


        //Declaraciones de las columnas de la tabla
        tbTiendas.getColumns().clear();
        tbTiendas.getColumns().addAll(cmnId, cmnNombre, cmnDescripcion, cmnTipoPago, cmnEspacio, cmnFechaCreacion);
        tbTiendas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cmnId.setCellValueFactory(new PropertyValueFactory<>("idTienda"));
        cmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cmnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        cmnTipoPago.setCellValueFactory(new PropertyValueFactory<>("tipoPago"));
        cmnEspacio.setCellValueFactory(new PropertyValueFactory<>("espacio"));
        cmnFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        //Seleccionar fila
        tbTiendas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Tienda tiendaSeleccionada = tbTiendas.getSelectionModel().getSelectedItem();
                if (tiendaSeleccionada != null) {
                    txtFieldNombre.setText(tiendaSeleccionada.getNombre());
                    txtFieldEspacio.setText(tiendaSeleccionada.getEspacio() + "");
                    txtFieldDescripcion.setText(tiendaSeleccionada.getDescripcion());
                    cbTipoPago.setValue(tiendaSeleccionada.getTipoPago());
                    dpFechaCreacion.setValue(tiendaSeleccionada.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                }
            } else {
                if (event.getClickCount() == 2) {
                    tbTiendas.getSelectionModel().clearSelection();
                    cleanFields();
                }
            }

        });

        //Menú de contexto
        menuItemMenuContextoCrear.setOnAction(this::handleCreateTienda);
        menuItemMenuContextoModificar.setOnAction(this::handleEditTienda);
        menuItemMenuContextoEliminar.setOnAction(this::handleDeleteTienda);
        //menuItemMenuContextoVer.setOnAction(this::handle);

        //ComboBox
        cbTipoPago.getItems().setAll(FXCollections.observableArrayList(TipoPago.values()));
        cbFiltros.getItems().add("Mostrar todas");
        cbFiltros.getItems().add("Menor espacio");
        cbFiltros.getItems().add("Mayor espacio");
        cbFiltros.getItems().add("Entre espacios");
        cbFiltros.getItems().add("Tipo pago");

        //Botones crear, eliminar y editar
        btnCrear.setOnAction(this::handleCreateTienda);
        btnEditar.setOnAction(this::handleEditTienda);
        btnEliminar.setOnAction(this::handleDeleteTienda);

        //Filtros
        cbFiltros.setOnAction(this::handleFiltros);
        btnFiltrar.setOnAction(this::handleEjecutarFiltros);
        txtFieldFiltro1.setDisable(true);
        txtFieldFiltro2.setDisable(true);

        //Cargar tabla
        handleCargeTable();

        stage.show();
    }

    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
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
     * @param actionEvent
     */
    @FXML
    public void handleCreateTienda(ActionEvent actionEvent) {
        String nombre = txtFieldNombre.getText();
        String descripcion = txtFieldDescripcion.getText();
        float espacio = Float.parseFloat(txtFieldEspacio.getText());
        TipoPago tipoPago = (TipoPago) cbTipoPago.getValue();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFechaCreacion.getValue().toString());
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
            tienda.setCliente(null);

            TiendaInterface ti = TiendaFactoria.getTiendaInterface();
            ti.create_XML(tienda);

            cleanFields();
            handleCargeTable();
        } catch (InvalidFormatException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Debes introducir bien los datos!!");
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleEditTienda(ActionEvent actionEvent) {
        Tienda tiendaSeleccionada = tbTiendas.getSelectionModel().getSelectedItem();
        try {
            if (tiendaSeleccionada == null) {
                throw new NotSelectedException("No hay seleccionada ninguna tienda!!");
            }

            tiendaSeleccionada.setNombre(txtFieldNombre.getText());
            tiendaSeleccionada.setDescripcion(txtFieldDescripcion.getText());
            tiendaSeleccionada.setEspacio(Float.parseFloat(txtFieldEspacio.getText()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFechaCreacion.getValue().toString());
            tiendaSeleccionada.setFechaCreacion(fechaCreacion);
            tiendaSeleccionada.setTipoPago((TipoPago) cbTipoPago.getValue());

            if (usuario instanceof Cliente) {
                if (((Cliente) usuario).getTienda().getIdTienda() == tiendaSeleccionada.getIdTienda()) {
                    TiendaInterface ti = TiendaFactoria.getTiendaInterface();
                    ti.edit_XML(tiendaSeleccionada, tiendaSeleccionada.getIdTienda().toString());
                } else {
                    cleanFields();
                }
            } else {
                TiendaInterface ti = TiendaFactoria.getTiendaInterface();
                ti.edit_XML(tiendaSeleccionada, tiendaSeleccionada.getIdTienda().toString());
            }

        } catch (NotSelectedException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "No hay ninguna tienda seleccionada!!");
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
        cleanFields();
        handleCargeTable();

    }

    @FXML
    public void handleDeleteTienda(ActionEvent actionEvent) {
        ObservableList<Tienda> tiendasSeleccionadas = tbTiendas.getSelectionModel().getSelectedItems();

        TiendaInterface ti = TiendaFactoria.getTiendaInterface();

        for (int i = 0; i < tiendasSeleccionadas.size(); i++) {
            ti.remove(tiendasSeleccionadas.get(i).getIdTienda().toString());
        }
        cleanFields();
        handleCargeTable();
    }

    private void handleCargeTable() {
        List<Tienda> tiendas = new ArrayList();
        TiendaInterface ti = TiendaFactoria.getTiendaInterface();
        tiendas = ti.findAll_XML(new GenericType<List<Tienda>>() {
        });

        ObservableList<Tienda> tiendasTabla = FXCollections.observableArrayList(tiendas);

        for (int i = 0; i < tiendasTabla.size(); i++) {
            System.out.println(tiendasTabla.get(i).toString());
        }

        tbTiendas.setItems(tiendasTabla);
        tbTiendas.refresh();
    }

    private void handleCargeTableFiltro(List<Tienda> tiendas) {
        ObservableList<Tienda> tiendasTabla = FXCollections.observableArrayList(tiendas);

        for (int i = 0; i < tiendasTabla.size(); i++) {
            System.out.println(tiendasTabla.get(i).toString());
        }

        tbTiendas.setItems(tiendasTabla);
        tbTiendas.refresh();
    }

    private void handleFiltros(Event event) {
        String filtroSeleccionado = (String) cbFiltros.getValue();
        if (filtroSeleccionado.equalsIgnoreCase("Mostrar todas")) {
            txtFieldFiltro1.setDisable(true);
            txtFieldFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor espacio")) {
            txtFieldFiltro1.setDisable(false);
            txtFieldFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor espacio")) {
            txtFieldFiltro1.setDisable(false);
            txtFieldFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre espacios")) {
            txtFieldFiltro1.setDisable(false);
            txtFieldFiltro2.setDisable(false);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Tipo pago")) {
            txtFieldFiltro1.setDisable(false);
            txtFieldFiltro2.setDisable(true);
            cleanFields();
        } else {

        }
    }

    private void handleEjecutarFiltros(Event event) {
        String filtroSeleccionado = cbFiltros.getValue().toString();

        TiendaInterface ti = TiendaFactoria.getTiendaInterface();

        if (filtroSeleccionado.equalsIgnoreCase("Mostrar todas")) {
            handleCargeTable();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor espacio")) {
            String espacio = txtFieldFiltro1.getText();
            if (checkSpaceFormat(Integer.parseInt(espacio))) {
                List<Tienda> tiendas = new ArrayList();
                tiendas = ti.encontrarTiendaMenorEspacio_XML(new GenericType<List<Tienda>>() {
                }, espacio);
                handleCargeTableFiltro(tiendas);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor espacio")) {
            String espacio = txtFieldFiltro1.getText();
            if (checkSpaceFormat(Integer.parseInt(espacio))) {
                List<Tienda> tiendas = new ArrayList();
                tiendas = ti.encontrarTiendaMayorEspacio_XML(new GenericType<List<Tienda>>() {
                }, espacio);
                handleCargeTableFiltro(tiendas);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre espacios")) {
            String espacio = txtFieldFiltro1.getText();
            String espacio2 = txtFieldFiltro2.getText();
            if (checkSpaceFormat(Integer.parseInt(espacio)) && checkSpaceFormat(Integer.parseInt(espacio2))) {
                List<Tienda> tiendas = new ArrayList();
                tiendas = ti.encontrarTiendaEntreEspacio_XML(new GenericType<List<Tienda>>() {
                }, espacio, espacio2);
                handleCargeTableFiltro(tiendas);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Tipo pago")) {
            String tipoPago = txtFieldFiltro1.getText();
            List<Tienda> tiendas = new ArrayList();
            tiendas = ti.encontrarTiendaMayorEspacio_XML(new GenericType<List<Tienda>>() {
            }, tipoPago);
            handleCargeTableFiltro(tiendas);
        } else {

        }
    }

    private void handleCerrarSesion(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root;

            root = (Parent) loader.load();

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

    @FXML
    public void handleAbrirAyuda(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AyudaTiendas.fxml"));
            Parent root = loader.load();
            ControllerAyudaTiendas viewController = ((ControllerAyudaTiendas) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Date convertToLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private void cleanFields() {
        txtFieldNombre.setText("");
        txtFieldDescripcion.setText("");
        txtFieldEspacio.setText("");
        cbTipoPago.setValue(null);
        dpFechaCreacion.setValue(null);
        txtFieldFiltro1.setText("");
        txtFieldFiltro2.setText("");
    }

    /**
     * Maneja el evento de ayuda en la interfaz gráfica de eventos. Este método
     * invoca el método {@link ControllerAyudas#mostrarVentanaAyudaEvento()}
     * para mostrar una ventana de ayuda específica para la interfaz de eventos.
     *
     * @param event El evento de acción que desencadena esta operación.
     * @see ControllerAyudas#mostrarVentanaAyudaEvento()
     */
    @FXML
    public void handleAyuda(ActionEvent event) {
        ControllerAyudas.getInstance().mostrarVentanaAyudaTienda();
    }


}
