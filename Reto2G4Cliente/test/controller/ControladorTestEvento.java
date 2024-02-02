/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Evento;
import entities.Producto;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;

import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import reto2g4cliente.Reto2G4Cliente;

/**
 * Tests de la ventana de Eventos
 *
 * @author Iñigo
 */
public class ControladorTestEvento extends ApplicationTest {

    private TableView<Evento> tbEventos;

    @Override
    public void start(Stage stage) throws Exception {
        new Reto2G4Cliente().start(stage);
    }

    /* Test que comprueba que la tabla se carga correctamente con los 10 Eventos existentes
     * que los botones de Editar, Eliminar y Editar estan habilitaods y en el boton para
     * unirse a un evento sale con el texto Adjuntar por ser un Admin
     */
    @Ignore
    @Test
    public void test1() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Eventos");
        tbEventos = lookup("#tbEventos").query();
        int rowCount = tbEventos.getItems().size();
        verifyThat("#btnCrear", isEnabled());
        verifyThat("#btnEditar", isEnabled());
        verifyThat("#btnEliminar", isEnabled());
        //verifyThat("Adjuntar", isVisible());
        assertEquals("Ha habido un fallo en la carga de la tabla", rowCount = 9, tbEventos.getItems().size());

    }

    /* Test que comprueba que la tabla se carga correctamente con los 10 Eventos existentes
     * que los botones de Editar, Eliminar y Editar estan deshabilitados y en el boton para
     * unirse a un evento sale con el texto Apuntarse por ser un Cliente
     */
    @Ignore
    @Test
    public void test2() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario4@example.com");
        doubleClickOn("#passField");
        write("password4");
        clickOn("Iniciar sesión");
        clickOn("Eventos");
        tbEventos = lookup("#tbEventos").query();
        int rowCount = tbEventos.getItems().size();
        verifyThat("#btnCrear", isDisabled());
        verifyThat("#btnEditar", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("Apuntarse", isVisible());
        assertEquals("Ha habido un fallo en la carga de la tabla", rowCount = 8, tbEventos.getItems().size());

    }

    //Test que comprueba el boton de crear Eventos
    //@Ignore
    @Test
    public void test3() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Eventos");
        tbEventos = lookup("#tbEventos").query();
        int rowCount = tbEventos.getItems().size();
        clickOn("#txtFieldTotalRecaudado");
        write("7777");
        clickOn("#txtFieldNumParticipantes");
        write("77");
        clickOn("#dpfechaEvento");
        write("07/07/2027");
        clickOn("#btnCrear");
        clickOn("Aceptar");
        assertEquals("Ha habido un fallo en la creacion", rowCount + 1, tbEventos.getItems().size());
        List<Evento> eventos = tbEventos.getItems();
        assertEquals("Ha habido un fallo en la creacion",
                eventos.stream().filter(u -> u.getNumParticipantes() == 77).count(), 1);

    }

    //Test que comprueba el boton de editar Eventos
    //@Ignore
    @Test
    public void test4() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Eventos");
        tbEventos = lookup("#tbEventos").query();
        int rowCount = tbEventos.getItems().size();
        clickOn("77");
        doubleClickOn("#txtFieldNumParticipantes");
        write("775");
        clickOn("#btnEditar");
        clickOn("Aceptar");
        assertEquals("Ha habido un fallo en la modificacion", rowCount, tbEventos.getItems().size());
        List<Evento> eventos = tbEventos.getItems();
        assertEquals("Ha habido un fallo en la modificacion",
                eventos.stream().filter(u -> u.getNumParticipantes() == 775).count(), 1);

    }

    //Test que comprueba el boton de eliminar Eventos
    //@Ignore
    @Test
    public void test5() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Eventos");
        tbEventos = lookup("#tbEventos").query();
        int rowCount = tbEventos.getItems().size();
        clickOn("775");
        clickOn("#btnEliminar");
        clickOn("Aceptar");
        assertEquals("Ha habido un fallo en la eliminacion", rowCount - 1, tbEventos.getItems().size());
        List<Evento> eventos = tbEventos.getItems();
        assertEquals("Ha habido un fallo en la eliminacion",
                eventos.stream().filter(u -> u.getNumParticipantes() == 775).count(), 0);

    }

    //Test que comprueba el boton de filtrar Eventos por Total Recaudado
    //@Ignore
    @Test
    public void test6() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Eventos");
        tbEventos = lookup("#tbEventos").query();
        int rowCount = tbEventos.getItems().size();
        clickOn("#comboFiltros");
        clickOn("Menor recaudado que");
        clickOn("#txtFieldParametro1");
        write("1000");
        clickOn("#btnFiltrar");
        assertEquals("Ha habido un fallo en el filtro ", rowCount = 2, tbEventos.getItems().size());
        List<Evento> eventos = tbEventos.getItems();
        assertEquals("Ha habido un fallo en el filtro",
                eventos.stream().filter(u -> u.getTotalRecaudado() < 1000).count(), 2);
    }
}
