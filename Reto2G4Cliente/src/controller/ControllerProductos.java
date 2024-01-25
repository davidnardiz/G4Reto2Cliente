/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Producto;
import java.awt.Desktop;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    private TextField tfFiltro1;
    @FXML
    private TextField tfFiltro2;
    @FXML
    private ComboBox cbFiltro;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        cbFiltro.getItems().add("Mostrar todo");
        cbFiltro.getItems().add("Nombre");
        cbFiltro.getItems().add("Menor Altura");
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
        cbFiltro.setOnAction(this::handleFiltros);
        buttonFiltrar.setOnAction(this::handleFiltrar);
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
            TableCell<Producto, Date> cell = new TableCell<Producto, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item != null) {
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
        ProductoInterface ti = ProductoFactoria.createInterface();
        productos = ti.findAll_XML(new GenericType<List<Producto>>() {
        });

        ObservableList<Producto> productoTabla = FXCollections.observableArrayList(productos);

        for (int i = 0; i < productoTabla.size(); i++) {
            System.out.println(productoTabla.get(i).toString());
        }

        tbProductos.setItems(productoTabla);
        tbProductos.refresh();
    }

    private void handleCreateProducto(ActionEvent actionevent) {
        try {
            String nombre = tfNombre.getText();
            String material = tfMaterial.getText();
            Float precio = Float.parseFloat(tfPrecio.getText());
            Integer altura = Integer.parseInt(tfAltura.getText());
            Float peso = Float.parseFloat(tfPeso.getText());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFecha.getValue().toString());

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setMaterial(material);
            producto.setPrecio(precio);
            producto.setAltura(altura);
            producto.setPeso(peso);
            producto.setFechacreacion(fechaCreacion);
            producto.setCliente(null);
            producto.setTienda(null);

            ProductoInterface pi = ProductoFactoria.createInterface();
            pi.create_XML(producto);

            cleanFields();
            handleCargeTable();
        } catch (ParseException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleEditProducto(ActionEvent actionevent) {
        try {
            Producto productoSeleccionado = tbProductos.getSelectionModel().getSelectedItem();

            productoSeleccionado.setNombre(tfNombre.getText());
            productoSeleccionado.setMaterial(tfMaterial.getText());
            productoSeleccionado.setPrecio(Integer.parseInt(tfPrecio.getText()));
            productoSeleccionado.setAltura(Integer.parseInt(tfAltura.getText()));
            productoSeleccionado.setPeso(Float.parseFloat(tfPeso.getText()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFecha.getValue().toString());
            productoSeleccionado.setFechacreacion(fechaCreacion);

            ProductoInterface pi = ProductoFactoria.createInterface();
            pi.edit_XML(productoSeleccionado, productoSeleccionado.getIdProducto().toString());
            cleanFields();
            handleCargeTable();
        } catch (ParseException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleDeleteProducto(ActionEvent actionevent) {
        ObservableList<Producto> productosSeleccionados = tbProductos.getSelectionModel().getSelectedItems();

        ProductoInterface pi = ProductoFactoria.createInterface();

        for (int i = 0; i < productosSeleccionados.size(); i++) {
            pi.remove(productosSeleccionados.get(i).getIdProducto().toString());
        }

        cleanFields();
        handleCargeTable();
    }

    private void handleFiltros(Event event) {
        String filtroSeleccionado = (String) cbFiltro.getValue();
        if (filtroSeleccionado.equalsIgnoreCase("Mostrar todo")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(true);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Nombre")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor altura")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor altura")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre altura")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(false);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor precio")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor precio")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre precio")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(false);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor peso")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor peso")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre peso")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(false);
            cleanFields();
        }
    }

    private void handleFiltrar(ActionEvent actionevent) {
        String filtroSeleccionado = cbFiltro.getValue().toString();

        ProductoInterface pi = ProductoFactoria.createInterface();

        if (filtroSeleccionado.equalsIgnoreCase("Mostrar todo")) {
            handleCargeTable();
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Nombre")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getNombre().equalsIgnoreCase(tfFiltro1.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor altura")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getAltura() >= Integer.parseInt(tfFiltro1.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor altura")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getAltura() <= Integer.parseInt(tfFiltro1.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre altura")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getAltura() >= Integer.parseInt(tfFiltro1.getText()) && productos.get(i).getAltura() <= Integer.parseInt(tfFiltro2.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor precio")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPrecio()>= Float.parseFloat(tfFiltro1.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor precio")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPrecio() <= Float.parseFloat(tfFiltro1.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre precio")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPrecio() >= Float.parseFloat(tfFiltro1.getText()) && productos.get(i).getPrecio() <= Float.parseFloat(tfFiltro2.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor peso")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPeso()>= Float.parseFloat(tfFiltro1.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor peso")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPeso() <= Float.parseFloat(tfFiltro1.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Entre peso")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPeso() >= Float.parseFloat(tfFiltro1.getText()) && productos.get(i).getPeso() <= Float.parseFloat(tfFiltro2.getText())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();
        }
    }

    private void handleChargeFiltro(List<Producto> productos) {
        ObservableList<Producto> productosTabla = FXCollections.observableArrayList(productos);
        for (int i = 0; i < productosTabla.size(); i++) {
            System.out.println(productosTabla.get(i).toString());
        }

        tbProductos.setItems(productosTabla);
        tbProductos.refresh();
    }

    private void handleInforme(ActionEvent actionevent) {

    }

    private void cleanFields() {
        tfId.setText("");
        tfNombre.setText("");
        tfAltura.setText("");
        tfPeso.setText("");
        tfMaterial.setText("");
        tfPrecio.setText("");
        dpFecha.setValue(null);
    }
}
