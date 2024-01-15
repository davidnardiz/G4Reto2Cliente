/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Evento;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.Table;

/**
 *
 * @author Iñigo
 */
public class ControllerEventos {

    private Stage stage;
    @FXML
    private TextField txtFieldId;
    @FXML
    private LocalDate txtFieldFecha;
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
    private Table tablaEventos;
    @FXML
    private ComboBox combroBoxFiltros;
    @FXML
    private TextField txtFieldParametro1;
    @FXML
    private TextField txtFieldParametro2;

    // Esto es una lista observable para almacenar eventos
    private ObservableList<Evento> eventosData = FXCollections.observableArrayList();

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

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
                handleLimpiarCampos();
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Limpia los campos del formulario.
     *
     */
    private void handleLimpiarCampos() {

        txtFieldId.clear();
        txtFieldFecha.adjustInto(null);
        txtFieldTotal.clear();
        txtFieldNumParticipantes.clear();
    }

    /**
     * Verifica si los campos de ID, Fecha de creación, Total recaudado y Número
     * de Participantes están informados.
     *
     * @param idEvento El ID del evento.
     * @param fechaCreacion La fecha del evento.
     * @param totalRecaudado El total recaudado del evento.
     * @param numParticipantes El número de participantes.
     * @return true si ambos campos están completos, false en caso contrario.
     */
    private boolean checkCompleteFields(String idEvento, Date fechaCreacion, double totalRecaudado, int numParticipantes) {
        return idEvento != 0 && fechaCreacion != null && totalRecaudado > 0 && numParticipantes > 0;
    }

}
