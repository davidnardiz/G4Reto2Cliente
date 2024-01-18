/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cliente;
import entities.Evento;
import entities.Tienda;
import entities.TipoPago;
import exceptions.InvalidFormatException;
import exceptions.NotSelectedTiendaException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.Table;
import javax.ws.rs.core.GenericType;
import service.EventoFactoria;
import service.EventoInterface;
import service.TiendaFactoria;
import service.TiendaInterface;

/**
 *
 * @author Iñigo
 */
public class ControllerEventos {

    private Stage stage;
    @FXML
    private TextField txtFieldId;
    @FXML
    private DatePicker dpfechaEvento;
    @FXML
    private TextField txtFieldTotal;
    @FXML
    private TextField txtFieldNumParticipantes;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Table tablaEventos;
    @FXML
    private ComboBox comboFiltros;
    @FXML
    private TextField txtFieldParametro1;
    @FXML
    private TextField txtFieldParametro2;
    @FXML
    private TableView<Evento> tbEventos;
    @FXML
    private ComboBox comboTipoPago;
    @FXML
    private TableColumn<Evento, Integer> cmnId;
    @FXML
    private TableColumn<Evento, Integer> cmnNumParticipantes;
    @FXML
    private TableColumn<Evento, Date> cmnFechaCreacion;
    @FXML
    private TableColumn<Evento, Cliente> cmnAdmin;
    @FXML
    private TableColumn<Evento, Double> cmnTotal;

    // Esto es una lista observable para almacenar eventos
    private ObservableList<Evento> eventosData = FXCollections.observableArrayList();

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        miCerrarSesion.setOnAction(this::handleCerrarSesion);
        miPrincipal.setOnAction(this::handleAbrirInicio);
        miProductos.setOnAction(this::handleAbrirProductos);
        miEventos.setOnAction(this::handleAbrirEventos);
        miPerfil.setOnAction(this::handleAbrirPerfil);

        tbEventos.getColumns().clear();
        tbEventos.getColumns().addAll(cmnId, cmnFechaCreacion, cmnNumParticipantes, cmnTotal, cmnAdmin);
        tbEventos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tbEventos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Evento eventoSeleccionado = tbEventos.getSelectionModel().getSelectedItem();
                if (eventoSeleccionado != null) {
                    txtFieldId.setText(eventoSeleccionado.getId());
                    txtFieldNumParticipantes.setText(eventoSeleccionado.getNumParticipantes() + "");
                    txtFieldTotal.setText(eventoSeleccionado.getTotalRecaudado());
                    dpfechaEvento.setValue(eventoSeleccionado.getFechaCreacion());
                }
            } else {
                if (event.getClickCount() == 2) {
                    tbEventos.getSelectionModel().clearSelection();
                    cleanFields();
                }
            }

        });

        comboTipoPago.getItems().setAll(FXCollections.observableArrayList(TipoPago.values()));
        comboFiltros.getItems().add("Mostrar todas");
        comboFiltros.getItems().add("Menor espacio");
        comboFiltros.getItems().add("Mayor espacio");
        comboFiltros.getItems().add("Entre espacios");
        comboFiltros.getItems().add("Tipo pago");

        cmnId.setCellValueFactory(new PropertyValueFactory<>("idTienda"));
        cmnFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        cmnNumParticipantes.setCellValueFactory(new PropertyValueFactory<>("numParticipantes"));
        cmnAdministrador.setCellValueFactory(new PropertyValueFactory<>("admin"));

        stage.setOnCloseRequest(this::handleCloseWindow);
        btnCrear.setOnAction(this::handleCreateTienda);
        btnEditar.setOnAction(this::handleEditTienda);
        btnEliminar.setOnAction(this::handleDeleteEvento);

        handleCargeTable();

        comboFiltros.setOnAction(this::handleFiltros);
        btnFiltrar.setOnAction(this::handleEjecutarFiltros);
        txtFieldParametro1.setDisable(true);
        txtFieldParametro2.setDisable(true);

        stage.show();

    }

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
    public void handleCreateEvento(ActionEvent actionEvent) {
        String id = txtFieldId.getText();
        LocalDate fechaEvento = dpfechaEvento.getValue();
        Double total = Double.parseDouble(txtFieldTotal.getText());
        TipoPago tipoPago = (TipoPago) comboTipoPago.getValue();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DatePicker fechaCreacion;
            fechaCreacion = dateFormat.parse(dpfechaEvento.getAccessibleHelp().getValue().toString());
            /*
            if (!checkNameFormat(nombre) || !checkDescriptionFormat(descripcion) || !checkSpaceFormat(espacio) || checkDateFormat(fechaCreacion) || !checkTypeFormat(tipoPago)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            }
             */
            if (!checkIdFormat(id)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            } else if (!checkDateFormat(fechaCreacion)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            } else if (!checkEarningsFormat(total)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            } else if (!checkGuestFormat(numParticipantes)) {
                throw new InvalidFormatException("Debes introducir bien los datos!!");
            }

            Evento evento = new Evento();
            evento.setId(id);
            evento.setFechaCreacion(fechaCreacion);
            evento.setNumParticipantes(numParticipantes);
            evento.setFecha(fechaCreacion);
            evento.setAdmin(null);

            EventoInterface eventoInterface = EventoFactoria.getEventoInterface();
            eventoInterface.create_XML(evento);

            cleanFields();
            handleCargeTable();
        } catch (InvalidFormatException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Debes introducir bien los datos!!");
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerEventos.class.getId()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleEditTienda(ActionEvent actionEvent) {

        Evento eventoSeleccionado = tbEventos.getSelectionModel().getSelectedItem();
        try {
            if (eventoSeleccionado == null) {
                throw new NotSelectedTiendaException("No hay seleccionada ninguna tienda!!");
            }

            TiendaInterface ti = TiendaFactoria.getTiendaInterface();
            ti.edit_XML(eventoSeleccionado, eventoSeleccionado.getIdEvento());

        } catch (NotSelectedTiendaException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
        cleanFields();
        handleCargeTable();

    }

    @FXML
    public void handleDeleteEvento(ActionEvent actionEvent) {

        ObservableList<Evento> eventosSeleccionados = tbEventos.getSelectionModel().getSelectedItems();

        EventoInterface eventoInterface = EventoFactoria.getEventoInterface();

        for (int i = 0; i < eventoInterface.size(); i++) {
            eventoInterface.remove(eventosSeleccionados.get(i).getIdEvento().toString());
        }
        cleanFields();
        handleCargeTable();
    }

    private void handleCargeTable() {
        List<Evento> eventos = new ArrayList();
        EventoInterface eventoInterface = EventoFactoria.getEventoInterface();
        eventos = eventoInterface.findAll_XML(new GenericType<List<Evento>>() {
        });

        ObservableList<Evento> eventosTabla = FXCollections.observableArrayList(eventos);

        for (int i = 0; i < eventosTabla.size(); i++) {
            System.out.println(eventosTabla.get(i).toString());
        }

        tbEventos.setItems(eventosTabla);
        tbEventos.refresh();
    }

    private void handleCargeTableFiltro(List<Tienda> tiendas) {
        ObservableList<Evento> eventosTabla = FXCollections.observableArrayList(evento);

        for (int i = 0; i < eventosTabla.size(); i++) {
            System.out.println(eventosTabla.get(i).toString());
        }

        tbEventos.setItems(eventosTabla);
        tbEventos.refresh();
    }

    private void handleFiltros(Evento evento) {
        String filtroSeleccionado = (String) comboFiltros.getValue();
        if (filtroSeleccionado.equalsIgnoreCase("Mostrar todos")) {
            txtFieldParametro1.setDisable(true);
            txtFieldParametro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor recaudado que")) {
            txtFieldParametro1.setDisable(false);
            txtFieldParametro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor recaudado que")) {
            txtFieldParametro1.setDisable(false);
            txtFieldParametro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Cantidad recaudada entre")) {
            txtFieldParametro1.setDisable(false);
            txtFieldParametro2.setDisable(false);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Más participantes que")) {
            txtFieldParametro1.setDisable(false);
            txtFieldParametro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Más participantes que")) {
            txtFieldParametro1.setDisable(false);
            txtFieldParametro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Participantes entre")) {
            txtFieldParametro1.setDisable(false);
            txtFieldParametro2.setDisable(false);
            cleanFields();
        }
    }

    private void handleEjecutarFiltros(Event event) {
        String filtroSeleccionado = comboFiltros.getValue().toString();

        EventoInterface eventoInterface = EventoFactoria.getEventoInterface();

        if (filtroSeleccionado.equalsIgnoreCase("Mostrar todas")) {
            handleCargeTable();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor recaudado que")) {
            String total = txtFieldParametro1.getText();
            if (checkSpaceFormat(Float.parseFloat(total))) {
                List<Evento> eventos = new ArrayList();
                total = eventoInterface.encontrarEventoMenorRecaudado_XML(responseType, total)
                _XML(new GenericType<List<Evento>>() {
                }, total);
                handleCargeTableFiltro(eventos);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor recaudado")) {
            String total = txtFieldParametro2.getText();
            if (checkSpaceFormat(Integer.parseInt(total))) {
                List<Evento> eventos = new ArrayList();
                eventos = eventoInterface.encontrarEventoMayorRecaudado_XML(new GenericType<List<Evento>>() {
                }, total);
                handleCargeTableFiltro(evento);
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
            viewController.setStage(stage);
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
            viewController.setStage(stage);
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
            viewController.setStage(stage);
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
        txtFieldId.setText("");
        txtFieldNumParticipantes.setText("");
        txtFieldTotal.setText("");
        dpfechaEvento.setValue(null);
        txtFieldParametro1.setText("");
        txtFieldParametro2.setText("");
    }

    /**
     * Establece el escenario para el controlador.
     *
     * @param stage El escenario de la ventana de inicio de sesión.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Este metodo se encarga de crear eventos en base a los valores que el
     * usuario ha introducido en el formulario.
     *
     * @param stage El escenario de la ventana de inicio de sesión.
     */
    public void handleCreateEvent(ActionEvent actionEvent) {
        // Se obtienen los valores del formulario
        int idEvento = Integer.parseInt(txtFieldId.getText());
        Date fechaCreacion = java.sql.Date.valueOf(txtFieldFecha.toString());
        double totalRecaudado = Double.parseDouble(txtFieldTotal.getText());
        int numParticipantes = Integer.parseInt(txtFieldNumParticipantes.getText());
        try {
            if (checkCompleteFields(idEvento, fechaCreacion, totalRecaudado, numParticipantes)) {

                // Crear una instancia del objeto Evento
                Evento nuevoEvento = new Evento();
                nuevoEvento.setIdEvento(idEvento);
                nuevoEvento.setFechaCreacion(fechaCreacion);
                nuevoEvento.setTotalRecaudado(totalRecaudado);
                nuevoEvento.setNumParticipantes(numParticipantes);

                // Añadir el nuevo evento a la lista declarada previamente
                eventosData.add(nuevoEvento);

                // Limpiar los campos después de añadir el evento
                handle();
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}

}

}
