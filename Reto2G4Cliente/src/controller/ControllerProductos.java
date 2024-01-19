/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Producto;
import entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import service.ProductoFactoria;
import service.ProductoInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerProductos {

    private Stage stage;
    private Usuario usuario;
    private TableView tbProductos;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        handleCargeTable();
        stage.setResizable(false);
        stage.show();

    }

    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    private void handleCargeTable() {
        List<Producto> productos = new ArrayList();
        ProductoInterface ti = ProductoFactoria.createProductoImple();
        productos = ti.findAll_XML(new GenericType<List<Producto>>() {
        });

        ObservableList<Producto> productoTabla = FXCollections.observableArrayList(productos);

        for (int i = 0; i < productoTabla.size(); i++) {
            System.out.println(productoTabla.get(i).toString());
        }

        tbProductos.setItems(productoTabla);
        tbProductos.refresh();
    }

}
