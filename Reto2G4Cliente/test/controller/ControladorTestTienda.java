package controller;

import entities.Tienda;
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
 * Tests de la venta de Tiendas
 *
 * @author David
 */
public class ControladorTestTienda extends ApplicationTest {

    private TableView<Tienda> tbTiendas;

    @Override
    public void start(Stage stage) throws Exception {
        new Reto2G4Cliente().start(stage);
    }

    /* Test que comprueba que la tabla se carga correctamente con los 10 Eventos existentes
     * que los botones de Editar, Eliminar y Editar estan habilitaods y por ser un Admin
     */
    //@Ignore
    @Test
    public void test1() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Tiendas");
        verifyThat("#btnCrear", isDisabled());
        verifyThat("#btnEditar", isEnabled());
        verifyThat("#btnEliminar", isEnabled());
    }

    /* Test que comprueba que la tabla se carga correctamente con los 10 Eventos existentes
     * que los botones de Eliminar y Editar estan deshabilitados y el boton Editar esta habilitado
     * por se un Cliente
     */
    //@Ignore
    @Test
    public void test2() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario4@example.com");
        doubleClickOn("#passField");
        write("password4");
        clickOn("Iniciar sesión");
        clickOn("Tiendas");
        verifyThat("#btnCrear", isDisabled());
        verifyThat("#btnEditar", isEnabled());
        verifyThat("#btnEliminar", isDisabled());
    }

    //Test que comprueba el boton de crear Tienda
    //@Ignore
    @Test
    public void test3() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("davidnardiztartanga@gmail.com");
        doubleClickOn("#passField");
        write("password8");
        clickOn("Iniciar sesión");
        clickOn("#txtFieldNombre");
        write("Tienda1");
        clickOn("#txtFieldDescripcion");
        write("Tienda1 venta de productos varios.");
        clickOn("#txtFieldEspacio");
        write("10");
        clickOn("#comboBoxTipoPago");
        clickOn("Efectivo");
        clickOn("#datePickerFechaCreacion");
        write("17/12/2023");
        clickOn("#btnCrearTienda");
        clickOn("Aceptar");

    }

    //Test que comprueba el boton de editar Tienda
    //@Ignore
    @Test
    public void test4() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Tiendas");
        tbTiendas = lookup("#tbTiendas").query();
        int rowCount = tbTiendas.getItems().size();
        clickOn("Tienda1");
        doubleClickOn("#txtFieldEspacio");
        write("50");
        clickOn("#btnEditar");
        clickOn("Aceptar");
        assertEquals("Ha habido un fallo en la edicion", rowCount, tbTiendas.getItems().size());
        List<Tienda> tiendas = tbTiendas.getItems();
        assertEquals("Ha habido un fallo en la edicion",
                tiendas.stream().filter(u -> u.getEspacio() == 50).
                        count(),
                1);
    }

    //Test que comprueba el boton de eliminar Tienda
    //@Ignore
    @Test
    public void test5() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Tiendas");
        tbTiendas = lookup("#tbTiendas").query();
        int rowCount = tbTiendas.getItems().size();
        clickOn("Tienda1");
        clickOn("#btnEliminar");
        clickOn("Aceptar");
        assertEquals("Ha habido un fallo en la eliminacion", rowCount - 1, tbTiendas.getItems().size());
        List<Tienda> tiendas = tbTiendas.getItems();
        assertEquals("Ha habido un fallo en la eliminacion",
                tiendas.stream().filter(u -> u.getNombre().equals("Tienda1Mod")).count(), 0);

    }

    //Test que comprueba el boton de flitrar Tienda por espacio
    //@Ignore
    @Test
    public void test6() {
        doubleClickOn("#txtFieldEmail");
        clickOn("#txtFieldEmail");
        write("usuario9@example.com");
        doubleClickOn("#passField");
        write("password9");
        clickOn("Iniciar sesión");
        clickOn("Tiendas");
        tbTiendas = lookup("#tbTiendas").query();
        int rowCount = tbTiendas.getItems().size();
        clickOn("#cbFiltros");
        clickOn("Menor espacio");
        clickOn("#txtFieldFiltro1");
        write("140");
        clickOn("#btnFiltrar");
        assertEquals("Ha habido un fallo en la eliminacion", rowCount = 3, tbTiendas.getItems().size());
        List<Tienda> tiendas = tbTiendas.getItems();
        assertEquals("Ha habido un fallo en la eliminacion",
                tiendas.stream().filter(u -> u.getEspacio() < 140).count(), 3);

    }
}
