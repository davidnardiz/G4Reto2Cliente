/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Producto;
import java.awt.Desktop;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private SimpleObjectProperty<LocalDate> date;
    @FXML
    private TableView<Producto> tbProductos;
    @FXML
    private TableColumn<Producto, Integer> tcId;
    @FXML
    private TableColumn<Producto, String> tcNombre; 
    @FXML
    private TableColumn<Producto, Float> tcPrecio;
    @FXML
    private TableColumn<Producto, Integer> tcAltura;
    @FXML
    private TableColumn<Producto, String> tcMaterial;
    @FXML
    private TableColumn<Producto, Float> tcPeso;
    @FXML
    private TableColumn<Producto, Date> tcFecha;
    @FXML
    private Button buttonAñadir;
    @FXML
    private Button buttonModificar;
    @FXML
    private Button buttonEliminar;
    @FXML
    private Button buttonFiltrar;
    @FXML
    private Button buttonInforme;
    @FXML
    private TextField tfId;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField tfPrecio;
    @FXML
    private TextField tfAltura;
    @FXML
    private TextField tfMaterial;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfFiltro;
    @FXML
    private ComboBox cbFiltro;
    
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        cbFiltro.getItems().add("Mostrar todo");
        cbFiltro.getItems().add("Nombre");
        cbFiltro.getItems().add("Menor ALtura");
        cbFiltro.getItems().add("Mayor Altura");
        cbFiltro.getItems().add("Entre Altura");
        cbFiltro.getItems().add("Menor Precio");
        cbFiltro.getItems().add("Mayor Precio");
        cbFiltro.getItems().add("Entre Precio");
        cbFiltro.getItems().add("Menor Peso");
        cbFiltro.getItems().add("Mayor Peso");
        cbFiltro.getItems().add("Entre peso");
       
        buttonAñadir.setOnAction(this::handleCreateProducto);
        buttonEliminar.setOnAction(this::handleDeleteProducto);
        buttonModificar.setOnAction(this::handleEditProducto);
        buttonFiltrar.setOnAction(this::handleFiltros);
        buttonInforme.setOnAction(this::handleInforme);


        tbProductos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tcId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tcAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        tcMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
        tcPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fechacreacion"));
        tcFecha.setCellFactory(column -> {
            TableCell<Producto, Date> cell = new TableCell<Producto, Date>(){
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                @Override
                protected void updateItem(Date item , boolean empty){
                    super.updateItem(item, empty);
                    if(empty){
                        setText(null);
                    }else {
                        if (item != null){
                            setText(format.format(item));
                        }
                    }
                }
            };
            return cell;
            
        });
        
        tbProductos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Producto productoSelecionado = tbProductos.getSelectionModel().getSelectedItem();
                if (productoSelecionado != null) {
                    tfId.setText(String.valueOf(productoSelecionado.getIdProducto()));
                    tfNombre.setText(productoSelecionado.getNombre());
                    tfPrecio.setText(String.valueOf(productoSelecionado.getPrecio()));
                    tfAltura.setText(String.valueOf(productoSelecionado.getAltura()));
                    tfMaterial.setText(productoSelecionado.getMaterial());
                    tfPeso.setText(String.valueOf(productoSelecionado.getPeso()));
                    dpFecha.setValue(productoSelecionado.getFechacreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
            } else {
                if (event.getClickCount() == 2) {
                    tbProductos.getSelectionModel().clearSelection();
                    cleanFields();
                }
            }

        });
        
        tbProductos.getColumns().clear();
        tbProductos.getColumns().addAll(tcId, tcNombre, tcPrecio, tcAltura, tcMaterial, tcPeso, tcFecha);
        
        handleCargeTable();
        stage.show();
        
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
    
    private void handleCreateProducto(ActionEvent actionevent){
        
    }
    
    private void handleEditProducto(ActionEvent actionevent){
        
    }
    
    private void handleDeleteProducto(ActionEvent actionevent){
        
    }

    private void handleFiltros(ActionEvent actionevent){
        
    }
    
    private void handleInforme(ActionEvent actionevent){
        
    }
    
    private void cleanFields() {
        tfId.setText("");
        tfNombre.setText("");
        tfAltura.setText("");
        tfPeso.setText("");
    }
}