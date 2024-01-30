/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.framework.junit.ApplicationTest;
import reto2g4cliente.Reto2G4Cliente;

/**
 *
 * @author Gonzalo
 */
public class ControladorTestProducto extends ApplicationTest{
    
   @Override
    public void start(Stage stage) throws Exception {
        new Reto2G4Cliente().start(stage);
    }
    
    @Test
    public void test1(){
        clickOn("Iniciar sesión");
        clickOn("Productos");
        verifyThat("#tbProductos",isVisible());
    }
    
//    @BeforeClass
//    public static void setUpClass() {
//        
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
    
}
