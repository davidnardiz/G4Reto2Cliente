/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cliente;
import entities.Tienda;
import entities.TiendaEvento;
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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Alert.AlertType;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import service.ClienteFactoria;
import service.ClienteInterface;
import service.TiendaEventoFactoria;
import service.TiendaEventoInterface;
import service.TiendaFactoria;
import service.TiendaInterface;

/**
 * Controlador de la venta de Tiedas
 *
 * @author David
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
    private Button btnInforme;
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

    /**
     ** La venta debe ser Modal La ventana no debe ser redimensionable. La
     * tabla de tiendas mostrará la información de todas las tiendas existentes
     * en la app. Si eres administrador el boton“Crear” (btnCrear) estará
     * deshabilitado y los botones “Editar” (btnEditar) y “Eliminar”
     * (btnEliminar) se mantendrán habilitados. El botón “Filtrar” (btnFiltrar)
     * se mantiene habilitado. Si eres un Cliente los botones “Crear” (btnCrear)
     * y “Eliminar” (btnEliminar) se ocultaran y los botones “Filtrar”
     * (btnFiltrar) y “Editar” (btnEditar) se mantienen habilitados. Los campos
     * “Nombre” (textFieldNombre), “Breve Descripción” (textFieldDescripcion),
     * “Espacio”(textFieldEspacio) y “Fecha de creación”(dpFechaCreacion) están
     * habilitados. La ComboBox de “Tipo de Pago” (cbTipoPago) se carga con las
     * descripciones de los diferentes tipos de pago. Si se produce cualquier
     * excepción en alguno de los procesos, mostrar un mensaje al usuario con el
     * texto de la excepción. El foco se pone en el campo de “Nombre”. Si eres
     * cliente, te aparecerán los datos de tu tienda en los campos del
     * formulario.
     *
     * @param root e
     */
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
            btnCrear.setDisable(true);
            btnEliminar.setDisable(false);
        }
        //Menú items del menú bar
        miCerrarSesion.setOnAction(this::handleCerrarSesion);
        miPrincipal.setOnAction(this::handleAbrirInicio);
        miProductos.setOnAction(this::handleAbrirProductos);
        miEventos.setOnAction(this::handleAbrirEventos);
        miPerfil.setOnAction(this::handleAbrirPerfil);
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
        //menuItemMenuContextoCrear.setOnAction(this::handleCreateTienda);
        menuItemMenuContextoModificar.setOnAction(this::handleEditTienda);
        menuItemMenuContextoEliminar.setOnAction(this::handleDeleteTienda);
        //menuItemMenuContextoVer.setOnAction(this::handle);

        //ComboBox
        cbTipoPago.getItems().setAll(FXCollections.observableArrayList(TipoPago.values()));
        cbTipoPago.setValue("Efectivo");
        cbFiltros.getItems().add("Mostrar todas");
        cbFiltros.getItems().add("Menor espacio");
        cbFiltros.getItems().add("Mayor espacio");
        cbFiltros.getItems().add("Entre espacios");
        cbFiltros.getItems().add("Tipo pago");
        cbFiltros.setValue("Mostrar todos");

        //Botones crear, eliminar y editar
        //btnCrear.setOnAction(this::handleCreateTienda);
        btnCrear.setDefaultButton(true);
        btnEditar.setOnAction(this::handleEditTienda);
        btnEliminar.setOnAction(this::handleDeleteTienda);
        btnInforme.setOnAction(this::handleCrearInforme);

        //Filtros
        cbFiltros.setOnAction(this::handleFiltros);
        btnFiltrar.setOnAction(this::handleEjecutarFiltros);
        txtFieldFiltro1.setDisable(true);
        txtFieldFiltro2.setDisable(true);

        //Cargar tabla
        handleCargeTable();

        stage.show();
    }

    /**
     * Método que settea el stage y guarda la infromacion del usuario.
     *
     * @param stage e
     * @param usuario e
     */
    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    /**
     * Método que solicita confirmación antes de cerrar la ventana cuando se
     * pulsa la x de la parte superior derecha.
     *
     * @param windowEvent e
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
    /*@FXML
    public void handleCreateTienda(ActionEvent actionEvent) {
        //Obtenemos los datos del formulario.
        String nombre = txtFieldNombre.getText();
        String descripcion = txtFieldDescripcion.getText();
        float espacio = Float.parseFloat(txtFieldEspacio.getText());
        TipoPago tipoPago = (TipoPago) cbTipoPago.getValue();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFechaCreacion.getValue().toString());

            //Comprobamos el formato de los adros introducidos.
            if (!checkNameFormat(nombre)) {
                throw new InvalidFormatException("Debes introducir bien el nombre!!");
            } else if (!checkDescriptionFormat(descripcion)) {
                throw new InvalidFormatException("Debes introducir bien la descripcion!!");
            } else if (!checkSpaceFormat(espacio)) {
                throw new InvalidFormatException("Debes introducir bien el espacio!!");
            } else if (!checkTypeFormat(tipoPago)) {
                throw new InvalidFormatException("Debes introducir bien el tipo de pago!!");
            } else if (!checkDateFormat(fechaCreacion)) {
                throw new InvalidFormatException("Debes introducir bien la fecha de creción!!");
            }

            //Creamos una nueva tienda y le introducimos sus datos.
            Tienda tienda = new Tienda();
            tienda.setNombre(nombre);
            tienda.setDescripcion(descripcion);
            tienda.setEspacio(espacio);
            tienda.setFechaCreacion(fechaCreacion);
            tienda.setTipoPago(tipoPago);
            tienda.setCliente(null);

            //Creamos la tienda.
            TiendaInterface ti = TiendaFactoria.getTiendaInterface();
            ti.create_XML(tienda);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Tienda creada correctamente");
            alerta.setHeaderText(null);
            alerta.show();
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
    }*/
    /**
     * Esto solo se aplicará tanto para el cliente, que solo podrá editar su
     * tienda, como para el administrador, que podrá editar todas. Comprobar si
     * los campos obligatorios están rellenados. Si no lo están, no actualizar
     * la información e informar al usuario. Si lo están modificar la tienda
     * seleccionada con los datos de los campos, actualizar la base de datos, la
     * tabla y vaciar todos los campos.
     *
     * @param actionEvent e
     */
    @FXML
    public void handleEditTienda(ActionEvent actionEvent) {
        //Obtenemos los datos de la tienda seleccionada.
        Tienda tiendaSeleccionada = tbTiendas.getSelectionModel().getSelectedItem();
        try {
            if (tiendaSeleccionada == null) {
                throw new NotSelectedException("No hay seleccionada ninguna tienda!!");
            }
            //Actualizamos la información de la tienda con los datos introducidos.
            tiendaSeleccionada.setNombre(txtFieldNombre.getText());
            tiendaSeleccionada.setDescripcion(txtFieldDescripcion.getText());
            tiendaSeleccionada.setEspacio(Float.parseFloat(txtFieldEspacio.getText()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFechaCreacion.getValue().toString());
            tiendaSeleccionada.setFechaCreacion(fechaCreacion);
            tiendaSeleccionada.setTipoPago((TipoPago) cbTipoPago.getValue());

            //Comprobamos si el cliente ha seleccionado su tienda y si es asi le dejamos editarla. Si entra un administrador, puede editar todas las que quiera.
            if (usuario instanceof Cliente) {
                if (((Cliente) usuario).getTienda().getIdTienda() == tiendaSeleccionada.getIdTienda()) {
                    TiendaInterface ti = TiendaFactoria.getTiendaInterface();
                    ti.edit_XML(tiendaSeleccionada, tiendaSeleccionada.getIdTienda().toString());
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Tienda correctamente editada!!");
                    alerta.setHeaderText(null);
                    alerta.show();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION, "No puedes editar una tienda que no te pertenezca!!");
                    alerta.setHeaderText(null);
                    alerta.show();
                    cleanFields();
                }
            } else {
                TiendaInterface ti = TiendaFactoria.getTiendaInterface();
                ti.edit_XML(tiendaSeleccionada, tiendaSeleccionada.getIdTienda().toString());
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Tienda correctamente editada!!");
                alerta.setHeaderText(null);
                alerta.show();
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

    /**
     * Esto solo se aplicará si el usuario es administrador: Mostrar un diálogo
     * en el que se pida confirmar al usuario si quiere borrar la tienda: En
     * caso de cancelar, cerrar el diálogo de confirmación. En caso de
     * confirmar, eliminar la tienda, actualizar la tabla, deseleccionar la fila
     * seleccionada de la tabla, vaciar los campos “Nombre” (textFieldNombre),
     * “Descripción” (textFieldDescripcion), “Tipo de Pago” (textFieldTipoPago),
     * “Espacio” (columnaEspacio) y “Fecha de Creación” (columnaFecha). Enfocar
     * el campo “Nombre”.
     *
     * @param actionEvent e
     */
    @FXML
    public void handleDeleteTienda(ActionEvent actionEvent) {
        //Obtenemos las tiendas seleccionadas.
        ObservableList<Tienda> tiendasSeleccionadas = tbTiendas.getSelectionModel().getSelectedItems();

        TiendaInterface ti = TiendaFactoria.getTiendaInterface();
        TiendaEventoInterface tei = TiendaEventoFactoria.getTiendaEventoInterface();

        List<TiendaEvento> tiendasEventos = tei.findAll_XML(new GenericType<List<TiendaEvento>>() {
        });

        //Borramos todas las tiendas que haya seleccionado el usuario.
        for (Tienda t : tiendasSeleccionadas) {
            for (int i = 0; i < tiendasEventos.size(); i++) {
                if (tiendasEventos.get(i).getTienda().getIdTienda().equals(t.getIdTienda())) {
                    tei.remove(tiendasEventos.get(i).getTienda().getIdTienda() + "", tiendasEventos.get(i).getEvento().getIdEvento() + "");
                }
            }

            ti.remove(t.getIdTienda().toString());
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Tienda eliminada correctamente");
            alerta.setHeaderText(null);
            alerta.show();
        }

        cleanFields();

        handleCargeTable();
    }

    /**
     * Al pulsar el botón se genera un informe con la información que contiene
     * la información de la tabla.
     *
     * @param actionEvent e
     */
    @FXML
    public void handleCrearInforme(ActionEvent actionEvent) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/tiendaReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Tienda>) this.tbTiendas.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alerta.setHeaderText(null);
            alerta.show();
        }

    }

    /**
     * Metodo para cargar la tabla con los datos.
     */
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

    /**
     * Metodo para cargar la tabla con los filtros seleccionados
     *
     * @param tiendas
     */
    private void handleCargeTableFiltro(List<Tienda> tiendas) {
        ObservableList<Tienda> tiendasTabla = FXCollections.observableArrayList(tiendas);

        for (int i = 0; i < tiendasTabla.size(); i++) {
            System.out.println(tiendasTabla.get(i).toString());
        }

        tbTiendas.setItems(tiendasTabla);
        tbTiendas.refresh();
    }

    /**
     * Metodo que habilita los campos de filtro en funcion del filtro
     * seleccionado
     *
     * @param event el tipo de evento del metodo
     */
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

    /**
     * Se comprueba si los valores introducidos en los textFields necesarios
     * para el filtro seleccionado, en caso de que el filtro los necesite,
     * tienen un formato correcto. En caso de que no lo tenga, se lanza una
     * excepción informando del error. En caso de que tengan un formato
     * correcto, se actualizará la información de la tabla aplicando el filtro
     * seleccionado por el usuario
     *
     * @param event
     */
    private void handleEjecutarFiltros(Event event) {
        String filtroSeleccionado = cbFiltros.getValue().toString();

        TiendaInterface ti = TiendaFactoria.getTiendaInterface();

        //Comprobamos que filtro ha seleccionado y cargamos la informacion en función del filtro seleccionado.
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
            if (checkSpaceFormat(Integer.parseInt(espacio)) && checkSpaceFormat(Integer.parseInt(espacio2)) && Integer.parseInt(espacio) < Integer.parseInt(espacio2)) {
                List<Tienda> tiendas = new ArrayList();
                tiendas = ti.encontrarTiendaEntreEspacio_XML(new GenericType<List<Tienda>>() {
                }, espacio, espacio2);
                handleCargeTableFiltro(tiendas);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Tipo pago")) {
            String tipoPago = txtFieldFiltro1.getText();
            List<Tienda> tiendas = new ArrayList();
            tiendas = ti.encontrarTiendaTipoPago_XML(new GenericType<List<Tienda>>() {
            }, tipoPago);
            handleCargeTableFiltro(tiendas);
        } else {

        }
    }

    /**
     * Metodo para cerrar sesion
     *
     * @param event
     */
    @FXML
    private void handleCerrarSesion(Event event) {
        try {
            //Cargamos el nuevo fxml.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root;

            root = (Parent) loader.load();
            //Creamos el nuevo controlador.
            ControllerSignIn viewController = ((ControllerSignIn) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerTiendas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para abrir la ventana de Inicio
     *
     * @param actionEvent e
     */
    @FXML
    public void handleAbrirInicio(ActionEvent actionEvent) {
        try {
            //Cargamos el nuevo fxml.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
            Parent root = loader.load();

            //Creamos el nuevo controlador.
            ControllerPrincipal viewController = ((ControllerPrincipal) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para abrir la ventana Productos
     *
     * @param actionEvent e
     */
    @FXML
    public void handleAbrirProductos(ActionEvent actionEvent) {
        try {
            //Cargamos el nuevo fxml.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Productos.fxml"));
            Parent root = loader.load();
            //Creamos el nuevo controlador.
            ControllerProductos viewController = ((ControllerProductos) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para abrir la ventana de eventos
     *
     * @param actionEvent
     */
    @FXML
    public void handleAbrirEventos(ActionEvent actionEvent) {
        try {
            //Cargamos el nuevo fxml.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Eventos.fxml"));
            Parent root = loader.load();
            //Creamos el nuevo controlador.
            ControllerEventos viewController = ((ControllerEventos) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para abrir el perfil del usuario con el que se ha iniciado sesion
     *
     * @param actionEvent
     */
    @FXML
    public void handleAbrirPerfil(ActionEvent actionEvent) {
        try {
            //Cargamos el nuevo fxml.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Perfil.fxml"));
            Parent root = loader.load();
            //Creamos el nuevo controlador.
            ControllerPerfil viewController = ((ControllerPerfil) loader.getController());
            viewController.setStage(stage, usuario);
            viewController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para comprobar el formato del nombre
     *
     * @param nombre
     * @return devuelve un boolean informando si el dato introducido tiene o no
     * el formato correcto.
     */
    private boolean checkNameFormat(String nombre) {
        return nombre.length() > 6;
    }

    /**
     * Metodo para comprobar el formato del Descripcion
     *
     * @param descripcion
     * @return devuelve un boolean informando si el dato introducido tiene o no
     * el formato correcto.
     */
    private boolean checkDescriptionFormat(String descripcion) {
        return descripcion.length() > 15;
    }

    /**
     * Metodo para comprobar el formato del espacio
     *
     * @param espacio
     * @return devuelve un boolean informando si el dato introducido tiene o no
     * el formato correcto.
     */
    private boolean checkSpaceFormat(float espacio) {
        return espacio > 0;
    }

    /**
     * Metodo para comprobar el formato de la fecha de creacion
     *
     * @param fechaCreacion
     * @return devuelve un boolean informando si el dato introducido tiene o no
     * el formato correcto.
     */
    private boolean checkDateFormat(Date fechaCreacion) {
        Date date = null;
        try {
            LocalDate localDate = LocalDate.now();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(localDate.toString());

        } catch (ParseException ex) {
            Logger.getLogger(ControllerTiendas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return fechaCreacion.before(date);
    }

    /**
     * Metodo para comprobar el formato del tipo de pago
     *
     * @param tipoPago
     * @return devuelve un boolean informando si el dato introducido tiene o no
     * el formato correcto.
     */
    private boolean checkTypeFormat(TipoPago tipoPago) {
        return tipoPago != null;
    }

    /**
     * Metodo para cambiar el formato de la fecha el formato
     *
     * @param localDate
     * @return devuelve una fecha.
     */
    public static Date convertToLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Metodo para limpiar los campos del formulario.
     */
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
