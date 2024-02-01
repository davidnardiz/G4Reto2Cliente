/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Producto;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.framework.junit.ApplicationTest;
import reto2g4cliente.Reto2G4Cliente;

/**
 * Tests de la ventana de Productos
 * @author Gonzalo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControladorTestProducto extends ApplicationTest {

    private TableView<Producto> tbProductos;
    private ComboBox cbFiltro;

    @Override
    public void start(Stage stage) throws Exception {

        new Reto2G4Cliente().start(stage);

    }
    
    //Test que comprueba que se cargen solo los productos de un cliente en caso del cliente usado 4
    @Ignore
    @Test
    public void test1() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario4@example.com");
        doubleClickOn("#passField");
        write("password4");
        clickOn("Iniciar sesión");
        clickOn("Productos");
        tbProductos = lookup("#tbProductos").query();
        int rowCount;
        assertEquals("Ha habido un fallo en la carga de tabla", rowCount = 4, tbProductos.getItems().size());

    }
    //Test que comprueba que se cargen todos los productos en este caso 11
    @Ignore
    @Test
    public void test2() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Productos");
        tbProductos = lookup("#tbProductos").query();
        int rowCount;
        assertEquals("Ha habido un fallo en la carga de tabla", rowCount = 11, tbProductos.getItems().size());

    }
    //Test que comprueba que el boton de crear Productos
    @Ignore
    @Test
    public void test3() {
        clickOn("Iniciar sesión");
        clickOn("Productos");
        tbProductos = lookup("#tbProductos").query();
        int rowCount = tbProductos.getItems().size();
        clickOn("#tfNombre");
        write("NTest");
        clickOn("#tfMaterial");
        write("MTest");
        clickOn("#tfPrecio");
        write("7");
        clickOn("#tfAltura");
        write("7");
        clickOn("#tfPeso");
        write("7");
        clickOn("#dpFecha");
        write("31/01/2024");
        clickOn("#buttonAñadir");
        assertEquals("Ha habido un fallo en la creacion", rowCount + 1, tbProductos.getItems().size());
        clickOn("Aceptar");
        List<Producto> productos = tbProductos.getItems();
        assertEquals("Ha habido un fallo en la creacion",
                productos.stream().filter(u -> u.getNombre().equals("NTest")).count(), 1);

    }
    //Test que comprueba el boton de modificar Productos
    @Ignore
    @Test
    public void test4() {
        clickOn("Iniciar sesión");
        clickOn("Productos");
        tbProductos = lookup("#tbProductos").query();
        int rowCount = tbProductos.getItems().size();
        clickOn("NTest");
        doubleClickOn("#tfNombre");
        write("NTestMod");
        clickOn("#buttonModificar");
        assertEquals("Ha habido un fallo en la modificacion", rowCount, tbProductos.getItems().size());
        clickOn("Aceptar");
        List<Producto> productos = tbProductos.getItems();
        assertEquals("Ha habido un fallo en la modificacion",
                productos.stream().filter(u -> u.getNombre().equals("NTestMod")).count(), 1);

    }
    //Test que comprueba el boton de eliminar Productos
    @Ignore
    @Test
    public void test5() {
        clickOn("Iniciar sesión");
        clickOn("Productos");
        tbProductos = lookup("#tbProductos").query();
        int rowCount = tbProductos.getItems().size();
        clickOn("NTestMod");
        clickOn("#buttonEliminar");
        assertEquals("Ha habido un fallo en la eliminacion", rowCount - 1, tbProductos.getItems().size());
        clickOn("Aceptar");
        List<Producto> productos = tbProductos.getItems();
        assertEquals("Ha habido un fallo en la eliminacion",
                productos.stream().filter(u -> u.getNombre().equals("NTestMod")).count(), 0);

    }
    //Test que comprueba el boton de filtrar Productos
    @Ignore
    @Test
    public void test6() {
        clickOn("Iniciar sesión");
        clickOn("Productos");
        tbProductos = lookup("#tbProductos").query();
        int rowCount = tbProductos.getItems().size();
        clickOn("#cbFiltro");
        clickOn("Por Nombre");
        clickOn("#tfFiltro1");
        write("Cho");
        clickOn("#buttonFiltrar");
        assertEquals("Ha habido un fallo en el filtro", rowCount - 2, tbProductos.getItems().size());
        List<Producto> productos = tbProductos.getItems();
        assertEquals("Ha habido un fallo en el filtro",
                productos.stream().filter(u -> u.getNombre().toLowerCase().contains("Cho".toLowerCase())).count(), 2);
    }
}
