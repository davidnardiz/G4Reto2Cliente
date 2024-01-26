/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Evento;
import entities.Usuario;
import exceptions.InvalidFormatException;
import exceptions.NotSelectedException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import service.EventoFactoria;
import service.EventoInterface;




/**
 *
 * Controlador para la ventana de Eventos. Permite realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Eliminar) respecto a eventos. Tambíen se pueden
 * aplicar filtros para visualizar eventos en base a párametros. El menú permite
 * navegar en la aplicación y mostrar una ventana de ayuda con instrucciones.
 *
 * @author Iñigo
 */
public class ControllerEventos {

    private Stage stage;
    private Usuario usuario;
    
    private MenuBar menu;
    @FXML
    private MenuItem menuItemCerrarSesion;
    @FXML
    private MenuItem menuItemPrincipal;
    @FXML
    private MenuItem menuItemProductos;
    @FXML
    private MenuItem menuItemEventos;
    @FXML
    private MenuItem menuItemPerfil;
    @FXML 
    MenuItem menuItemAyuda;
    @FXML
    private TextField txtFieldId;
    @FXML
    private DatePicker dpfechaEvento;
    @FXML
    private TextField txtFieldTotalRecaudado;
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
    private Button btnImprimir;
    @FXML
    private ComboBox comboFiltros;
    @FXML
    private TextField txtFieldParametro1;
    @FXML
    private TextField txtFieldParametro2;
    @FXML
    private TableView<Evento> tbEventos;
    @FXML
    private TableColumn<Evento, Integer> columnaId;
    @FXML
    private TableColumn<Evento, Date> columnaFecha;
    @FXML
    private TableColumn<Evento, Integer> columnaNumParticipantes;
    @FXML
    private TableColumn<Evento, Double> columnaTotalRecaudado;
    

    // Esto es una lista observable para almacenar eventos
    private ObservableList<Evento> eventosData = FXCollections.observableArrayList();

    /**
     * Establece el escenario para el controlador de la ventana de eventos.
     *
     * @param stage El escenario de la ventana de inicio de sesión.
     */
    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    /**
     * Inicializa la escena y configura los elementos de la interfaz de usuario
     * para la ventana de eventos. Establece acciones para los botones del menú,
     * configura las columnas de la tabla y gestiona eventos de clic en la
     * tabla.
     *
     * @param root El nodo raíz de la escena que se va a mostrar.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        menuItemCerrarSesion.setOnAction(this::handleCerrarSesion);
        menuItemPrincipal.setOnAction(this::handleAbrirInicio);
        menuItemProductos.setOnAction(this::handleAbrirProductos);
        menuItemEventos.setOnAction(this::handleAbrirEventos);
        menuItemPerfil.setOnAction(this::handleAbrirPerfil);
        menuItemAyuda.setOnAction(this::handleAyuda);
        

        tbEventos.getColumns().clear();
        tbEventos.getColumns().addAll(columnaId, columnaFecha, columnaNumParticipantes, columnaTotalRecaudado);
        tbEventos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        txtFieldId.setDisable(true);

        tbEventos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Evento eventoSeleccionado = tbEventos.getSelectionModel().getSelectedItem();
                if (eventoSeleccionado != null) {
                    txtFieldId.setText(eventoSeleccionado.getIdEvento().toString());
                    txtFieldNumParticipantes.setText(eventoSeleccionado.getNumParticipantes() + "");
                    txtFieldTotalRecaudado.setText(String.valueOf(eventoSeleccionado.getTotalRecaudado()));
                    dpfechaEvento.setValue(eventoSeleccionado.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                }
            } else {
                if (event.getClickCount() == 2) {
                    tbEventos.getSelectionModel().clearSelection();
                    cleanFields();
                }
            }

        });

        comboFiltros.getItems().add("Mostrar todos");
        comboFiltros.getItems().add("Menor recaudado que");
        comboFiltros.getItems().add("Mayor recaudado que");
        comboFiltros.getItems().add("Cantidad recaudada entre");
        comboFiltros.getItems().add("Menos participantes que");
        comboFiltros.getItems().add("Más participantes que");
        comboFiltros.getItems().add("Participantes entre");

        columnaId.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaNumParticipantes.setCellValueFactory(new PropertyValueFactory<>("numParticipantes"));
        columnaTotalRecaudado.setCellValueFactory(new PropertyValueFactory<>("totalRecaudado"));

        stage.setOnCloseRequest(this::handleCloseWindow);
        btnCrear.setOnAction(this::handleCreateEvento);
        btnEditar.setOnAction(this::handleEditEvento);
        btnEliminar.setOnAction(this::handleDeleteEvento);
        btnImprimir.setOnAction(this::handleImprimirEvento);

        handleCargeTable();
        comboFiltros.setOnAction(this::handleFiltros);
        btnFiltrar.setOnAction(this::handleEjecutarFiltros);
        txtFieldParametro1.setDisable(true);
        txtFieldParametro2.setDisable(true);

        stage.show();
    }

    /**
     * Maneja el evento de cierre de la ventana, mostrando una alerta de
     * confirmación. Si el usuario elige cerrar la ventana, se cierra la
     * aplicación.
     *
     * @param windowEvent El evento de cierre de la ventana.
     */
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
     * Maneja el evento de creación de un nuevo evento, obteniendo los datos de
     * los campos de entrada, validando la información y creando un nuevo evento
     * utilizando el servicio web. Si los datos no son válidos, muestra una
     * alerta informativa.
     *
     * @param actionEvent El evento de acción que desencadenó la llamada a este
     * método.
     */
   @FXML
    public void handleCreateEvento(ActionEvent actionEvent) {
    System.out.println("hola");
    Float total = Float.parseFloat(txtFieldTotalRecaudado.getText());
    int numParticipantes = Integer.parseInt(txtFieldNumParticipantes.getText());

    try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha;
        fecha = dateFormat.parse(dpfechaEvento.getValue().toString());

        if (!checkDateFormat(fecha)) {
            throw new InvalidFormatException("La fecha del evento debe ser posterior a hoy");
        } else if (!checkTotalFormat(total)) {
            throw new InvalidFormatException("El total recaudado debe ser un número entero o decimal");
        } else if (!checkParticipantesFormat(numParticipantes)) {
            throw new InvalidFormatException("Deben haber al menos dos participante sara crear un Evento");
        }

        Evento evento = new Evento();
        evento.setFecha(fecha);
        evento.setNumParticipantes(numParticipantes);
        evento.setTotalRecaudado(total);

        EventoInterface eventoInterface = EventoFactoria.getEventoInterface();
        eventoInterface.create_XML(evento);
        
        // Después de crear el evento, actualiza la tabla
        handleCargeTable();

    } catch (InvalidFormatException ex) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Debes introducir bien los datos!!");
        alerta.setHeaderText(null);
        alerta.show();
        Logger.getLogger(ControllerEventos.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
        Logger.getLogger(ControllerEventos.class.getName()).log(Level.SEVERE, null, ex);
    }
    cleanFields();
}
    /**
     * Maneja el evento de edición de un evento seleccionado. Obtiene el evento
     * seleccionado de la tabla, valida si hay alguno seleccionado, y luego
     * edita el evento. Si no hay ningún evento seleccionado, muestra una
     * excepción. Limpia los campos y actualiza la tabla de eventos después de
     * la editarlo.
     *
     * @param actionEvent El evento de acción que desencadenó la llamada a este
     * método.
     */

    @FXML
    public void handleEditEvento(ActionEvent actionEvent) {
        Evento eventoSeleccionado = tbEventos.getSelectionModel().getSelectedItem();
        try {
            if (eventoSeleccionado == null) {
                throw new NotSelectedException("Para editar un Evento debes seleccionarlo.");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date fechaEvento = dateFormat.parse(dpfechaEvento.getValue().toString());
                eventoSeleccionado.setFecha(fechaEvento);
            } catch (ParseException ex) {
                // Manejo de errores al convertir la fecha
                ex.printStackTrace();
            }

            eventoSeleccionado.setTotalRecaudado(Float.parseFloat(txtFieldTotalRecaudado.getText()));
            eventoSeleccionado.setNumParticipantes(Integer.parseInt(txtFieldNumParticipantes.getText()));

            EventoInterface eventoInterface = EventoFactoria.getEventoInterface();
            eventoInterface.edit_XML(eventoSeleccionado, eventoSeleccionado.getIdEvento().toString());

            // Después de editar el evento, actualiza la tabla
            handleCargeTable();

        } catch (NotSelectedException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Debes seleccionar un evento para editarlo.");
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerEventos.class.getName()).log(Level.SEVERE, null, ex);
        }
        cleanFields();
    }

    /**
     * Maneja el evento de eliminar los eventos seleccionados. Obtiene la lista
     * de eventos seleccionados en la tabla, utiliza el servicio web para
     * eliminarlos y luego limpia los campos y actualiza la tabla de eventos
     * después de la eliminación.
     *
     * @param actionEvent El evento de acción que desencadenó la llamada a este
     * método.
     */
    @FXML
    public void handleDeleteEvento(ActionEvent actionEvent) {
        ObservableList<Evento> eventosSeleccionados = tbEventos.getSelectionModel().getSelectedItems();
        EventoInterface eventoInterface = EventoFactoria.getEventoInterface();
        for (int i = 0; i < eventosSeleccionados.size(); i++) {
            eventoInterface.remove(eventosSeleccionados.get(i).getIdEvento().toString());
        }

        cleanFields();
        handleCargeTable();
    }

    /**
     * Maneja la carga de datos en la tabla de eventos. Utiliza el servicio web
     * para obtener todos los eventos disponibles, crea una lista observable de
     * eventos y actualiza la tabla con estos datos. Además, imprime cada evento
     * en la consola.
     */
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

    /**
     * Maneja la carga de datos en la tabla de eventos con un filtro específico.
     * Utiliza una lista de eventos filtrada, crea una lista observable de
     * eventos y actualiza la tabla con estos datos.
     *
     * @param eventos Lista de eventos filtrada que se utilizará para actualizar
     * la tabla.
     */
    private void handleCargeTableFiltro(List<Evento> eventos) {
        ObservableList<Evento> eventosTabla = FXCollections.observableArrayList(eventos);

        for (int i = 0; i < eventosTabla.size(); i++) {
            System.out.println(eventosTabla.get(i).toString());
        }

        tbEventos.setItems(eventosTabla);
        tbEventos.refresh();
    }

    /**
     * Maneja la selección de filtros en respuesta a un evento.
     *
     * Este método se encarga de gestionar los diferentes casos según el filtro
     * seleccionado en un componente de interfaz de usuario. Deshabilita o
     * habilita campos de entrada de acuerdo con la opción seleccionada y
     * realiza limpieza de campos en caso necesario.
     *
     * @param event El evento que desencadena la llamada a este método.
     */
    private void handleFiltros(Event event) {
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
        } else if (filtroSeleccionado.equalsIgnoreCase("Menos participantes que")) {
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

    /**
     * Maneja la configuración de la interfaz de usuario según el filtro
     * seleccionado. Habilita o deshabilita los campos de texto según el filtro
     * elegido. Además, limpia los campos de texto cuando se cambia el filtro.
     *
     * @param evento Evento asociado al filtro seleccionado.
     */
    private void handleEjecutarFiltros(Event event) {
        String filtroSeleccionado = comboFiltros.getValue().toString();

        EventoInterface eventoInterface = EventoFactoria.getEventoInterface();

        if (filtroSeleccionado.equalsIgnoreCase("Mostrar todas")) {
            GenericType<List<Evento>> responseType = new GenericType<List<Evento>>() {
            };
            List<Evento> eventos = eventoInterface.findAll_XML(responseType);
            handleCargeTableFiltro(eventos);

        } else if (filtroSeleccionado.equalsIgnoreCase("Menor recaudado que")) {
            String total = txtFieldParametro1.getText();
            if (checkTotalFormat(Float.parseFloat(total))) {
                List<Evento> eventos = new ArrayList();
                eventos = eventoInterface.encontrarEventoMenorRecaudado_XML(new GenericType<List<Evento>>() {
                }, total);
                handleCargeTableFiltro(eventos);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor recaudado que")) {
            String total = txtFieldParametro2.getText();
            if (checkTotalFormat(Integer.parseInt(total))) {
                List<Evento> eventos = new ArrayList();
                eventos = eventoInterface.encontrarEventoMayorRecaudado_XML(new GenericType<List<Evento>>() {
                }, total);
                handleCargeTableFiltro(eventos);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Cantidad recaudada entre")) {
            String total = txtFieldParametro1.getText();
            String total2 = txtFieldParametro2.getText();
            if (checkTotalFormat(Integer.parseInt(total)) && checkTotalFormat(Integer.parseInt(total2))) {
                List<Evento> eventos = new ArrayList();
                eventos = eventoInterface.encontrarEventoEntreRecaudado_XML(new GenericType<List<Evento>>() {
                }, total, total2);
                handleCargeTableFiltro(eventos);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Menos participantes que")) {
            String numParticipantes = txtFieldParametro1.getText();
            if (checkParticipantesFormat(Integer.parseInt(numParticipantes))) {
                List<Evento> eventos = new ArrayList();
                eventos = eventoInterface.encontrarEventoMenorNumParticipantes_XML(new GenericType<List<Evento>>() {
                }, numParticipantes);
                handleCargeTableFiltro(eventos);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Más participantes que")) {
            String numParticipantes = txtFieldParametro1.getText();
            if (checkParticipantesFormat(Integer.parseInt(numParticipantes))) {
                List<Evento> eventos = new ArrayList();
                eventos = eventoInterface.encontrarEventoMayorRecaudado_XML(new GenericType<List<Evento>>() {
                }, numParticipantes);
                handleCargeTableFiltro(eventos);
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Cantidad participantes entre")) {
            String numPartcipantes = txtFieldParametro1.getText();
            String numPartcipantes2 = txtFieldParametro2.getText();
            if (checkParticipantesFormat(Integer.parseInt(numPartcipantes)) && checkParticipantesFormat(Integer.parseInt(numPartcipantes2))) {
                List<Evento> eventos = new ArrayList();
                eventos = eventoInterface.encontrarEventoEntreParticipantes_XML(new GenericType<List<Evento>>() {
                }, numPartcipantes, numPartcipantes2);
                handleCargeTableFiltro(eventos);
            }
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
    private void handleCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root;

            root = (Parent) loader.load();

            ControllerSignIn viewController = ((ControllerSignIn) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerEventos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Maneja el evento de impresión de un informe de eventos. Compila un
     * informe Jasper a partir de un archivo JRXML, lo llena con datos de
     * eventos, lo exporta a un archivo PDF ("informe_eventos.pdf") y finalmente
     * abre el archivo PDF con el visor de PDF predeterminado en el sistema.
     *
     * @param actionEvent El evento que desencadena la llamada a este método.
     */
    @FXML
    public void handleImprimirEvento(ActionEvent actionEvent) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/resources/informe.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(eventosData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "informe_eventos.pdf");

            File file = new File("informe_eventos.pdf");
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de cerrar sesión. Carga la vista de inicio de
     * sesión,establece el escenario y el controlador, y muestra la nueva
     * ventana.
     *
     * @param actionEvent
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
     * Verifica si el formato del valor total proporcionado cumple con los
     * requisitos especificados.
     *
     * @param total El valor total a ser validado.
     * @return true si el formato del valor total es válido (mayor que cero);
     * false en caso contrario.
     */
    private boolean checkTotalFormat(float total) {
        return total > 0;
    }

    /**
     * Verifica si la fecha proporcionada es posterior a la fecha actual.
     *
     * @param fecha La fecha que se va a comparar con la fecha actual.
     * @return true si la fecha es posterior a la fecha actual, false de lo
     * contrario.
     */
    private boolean checkDateFormat(Date fecha) {
        Date date = null;
        try {
            LocalDate localDate = LocalDate.now();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(localDate.toString());
        } catch (ParseException ex) {
            Logger.getLogger(ControllerEventos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha.after(date);
    }

    /**
     * Verifica si la fecha de creación proporcionada cumple con los requisitos
     * especificados.
     *
     * @param fechaCreacion La fecha de creación a ser validada.
     * @return true si la fecha de creación es anterior a la fecha actual; false
     * en caso contrario.
     */
    private boolean checkParticipantesFormat(int numParticipantes) {
        return numParticipantes > 2;
    }

    /**
     * Convierte un objeto LocalDate a un objeto Date.
     *
     * @param localDate El objeto LocalDate a ser convertido.
     * @return Un objeto Date correspondiente al LocalDate proporcionado.
     */
    public static Date convertToLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Limpia los campos de entrada de datos en la interfaz de usuario.
     */
    private void cleanFields() {
        txtFieldId.setText("");
        txtFieldNumParticipantes.setText("");
        txtFieldTotalRecaudado.setText("");
        dpfechaEvento.setValue(null);
        txtFieldParametro1.setText("");
        txtFieldParametro2.setText("");
    }

    /**
     * Genera un informe de eventos y lo muestra en un archivo PDF. Utiliza un
     * diseño de informe (.jrxml) para compilar y llenar el informe con datos de
     * eventos. Exporta el informe a un formato PDF y abre el archivo resultante
     * en el visor de PDF predeterminado del sistema.
     *
     * @param eventos Lista de eventos que se utilizarán para llenar el informe.
     */
    public void generarInformeEventos(List<Evento> eventos) {
        try {

            String rutaInforme = "C:\\Users\\inigo\\Desktop\\MarketMaker\\G4Reto2Servidor\\G4Reto2Cliente\\G4Reto2Cliente\\Reto2G4Cliente\\src\\reports\\tiendaReport.jrxml";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaInforme);
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(eventos);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            String pdfFilePath = desktopPath + "informe_eventos.pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);

            Desktop.getDesktop().open(new File(pdfFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de ayuda. Llama al método que muestra una ventana de
     * ayuda con información sobre la funcionalidad de la ventana de eventos.
     *
     * @param actionEvent El evento que desencadena la llamada a este método.
     */
    /*@FXML
    public void handleAyuda(ActionEvent actionEvent) {
        mostrarVentanaAyuda();
    } */

    @FXML
    public void handleAyuda(ActionEvent event) {
        AyudaControllerSingletone.getInstance().mostrarVentanaAyudaEvento();
    }

    /**
     * Muestra una ventana de ayuda que describe la funcionalidad de la ventana
     * de eventos. La información incluye detalles sobre el formulario de gestor
     * de eventos, creación y edición de eventos, tabla de eventos, formulario
     * de filtrado y funcionalidad adicional.
     */
    private void mostrarVentanaAyuda() {
        Stage ventanaAyuda = new Stage();
        ventanaAyuda.initModality(Modality.APPLICATION_MODAL);
        ventanaAyuda.setTitle("¿Qué hace esta ventana?");

        VBox vbox = new VBox(10);

        Label contenidoAyuda = new Label(
                "Esta es la ventana de Eventos:\n\n"
               + "- Formulario de gestor de eventos: Crear, editar o eliminar eventos en la tabla.\n\n"
               + "- Crear eventos: Rellenar campos y hacer clic en \"Crear\".\n\n"
               + "- Editar eventos: Seleccionar evento en la tabla, editar y clic en \"Modificar\".\n\n"
               + "- Eliminar eventos: Seleccionar evento y clic en \"Eliminar\".\n\n"
               + "- Tabla: Ver todos los eventos, los cambios se reflejan desde el formulario.\n\n"
               + "- Formulario de filtrado: Filtrar eventos según criterios.\n\n"
               + "- Funcionalidad adicional: Clic en \"Informe\" para generar un documento con datos de eventos.\n\n"
        );

        vbox.getChildren().add(contenidoAyuda);

        String rutaImagen = "C:\\Users\\inigo\\Desktop\\MarketMaker\\IMAGENES\\Eventos.png";

        ImageView imageView = new ImageView("file:" + rutaImagen);

        imageView.setFitWidth(400);
        imageView.setFitHeight(300);

        vbox.getChildren().add(imageView);

        Scene escena = new Scene(vbox, 400, 500);
        ventanaAyuda.setScene(escena);
        ventanaAyuda.showAndWait();
    }

    
}
