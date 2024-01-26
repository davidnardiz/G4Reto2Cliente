/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Producto;
import exceptions.InvalidFormatException;
import exceptions.NotCompletedException;
import java.awt.Desktop;
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
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

    /**
     * La venta debe ser Modal La ventana no debe ser redimensionable. La tabla
     * de productos mostrará la información de todos los productos existentes.
     * Los campos “Nombre”, “Precio”, “Talla”, “Fecha”, “Material”, “Peso” y
     * “Descripción” están habilitados. El foco se pone en el campo de “Nombre”.
     *
     * @param root
     */
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
        stage.setOnCloseRequest(this::handleCloseWindow);

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
                    dpFecha.setDisable(true);
                }
            } else {
                if (event.getClickCount() == 2) {
                    tbProductos.getSelectionModel().clearSelection();
                    cleanFields();
                }
            }

        });

        tfId.setDisable(true);
        tfFiltro1.setDisable(true);
        tfFiltro2.setDisable(true);
        tbProductos.getColumns().clear();
        tbProductos.getColumns().addAll(tcId, tcNombre, tcPrecio, tcAltura, tcMaterial, tcPeso, tcFecha);

        handleCargeTable();
        stage.show();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carga la tabla con todos los Productos
     */
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

    /**
     * Validar que los campos “Nombre” (columnaNombre) , “Precio”
     * (columnaPrecio), “Talla” (columnaTalla), “Fecha” (columnaFecha),
     * “Material” (columnaMaterial) , “Peso” (columnaPeso) y “Descripción”
     * (columnaDescripcion) están informados y tengan un formato correcto. En
     * caso de que no lo estén, informar al usuario que debe rellenar todos los
     * campos para poder continuar. En caso de que lo estén, agregar el nuevo
     * producto a la base de datos, actualizar la tienda y vaciar los campos del
     * formulario.
     *
     * @param actionevent
     */
    private void handleCreateProducto(ActionEvent actionevent) {
        try {
            checkCompleteFields();
            Pattern pattern1 = Pattern.compile("[\\d\\W]+");
            Pattern pattern2 = Pattern.compile("[a-zA-Z]");
            Producto producto = new Producto();

            if (pattern1.matcher(tfNombre.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un nombre valido que no contenga ni numeros ni caracteres especiales", ButtonType.OK);
                alert.setHeaderText(null);
                tfNombre.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                producto.setNombre(tfNombre.getText());
            }

            if (pattern1.matcher(tfMaterial.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un material valido que no contenga ni numeros ni caracteres especiales", ButtonType.OK);
                alert.setHeaderText(null);
                tfMaterial.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                producto.setMaterial(tfMaterial.getText());
            }

            if (pattern2.matcher(tfPrecio.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un precio valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                alert.setHeaderText(null);
                tfPrecio.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                producto.setPrecio(Float.parseFloat(tfPrecio.getText()));
            }

            if (pattern2.matcher(tfAltura.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca una altura valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                alert.setHeaderText(null);
                tfAltura.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                producto.setAltura(Integer.parseInt(tfPrecio.getText()));
            }

            if (pattern2.matcher(tfPeso.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un peso valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                alert.setHeaderText(null);
                tfPeso.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                producto.setPeso(Float.parseFloat(tfPeso.getText()));
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFecha.getValue().toString());

            producto.setFechacreacion(fechaCreacion);
            producto.setCliente(null);
            producto.setTienda(null);
            ProductoInterface pi = ProductoFactoria.createInterface();
            pi.create_XML(producto);

            cleanFields();
            handleCargeTable();
        } catch (ParseException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotCompletedException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Comprueba que los campos estan informados
     *
     * @throws NotCompletedException
     */
    private void checkCompleteFields() throws NotCompletedException {
        if (tfNombre.getText().isEmpty() || tfAltura.getText().isEmpty() || tfPeso.getText().isEmpty() || tfPrecio.getText().isEmpty() || tfMaterial.getText().isEmpty() || dpFecha.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor rellene todos los campos", ButtonType.OK);
            alert.setHeaderText(null);
            alert.show();
            throw new NotCompletedException();
        }
    }

    /**
     * Comprobar si los datos están rellenados. Si no lo están, informar al
     * usuario que debe rellenar todos los campos para poder continuar. Si lo
     * están, comprobar que tienen el formato correcto y guardar los nuevos
     * datos en la base de datos. Si no lo tienen, informar al usuario y no
     * actualizar la información.
     *
     * @param actionevent
     */
    private void handleEditProducto(ActionEvent actionevent) {
        try {

            checkCompleteFields();
            Pattern pattern1 = Pattern.compile("[\\d\\W]+");
            Pattern pattern2 = Pattern.compile("[a-zA-Z]");
            Producto productoSeleccionado = tbProductos.getSelectionModel().getSelectedItem();

            if (pattern1.matcher(tfNombre.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un nombre valido que no contenga ni numeros ni caracteres especiales", ButtonType.OK);
                alert.setHeaderText(null);
                tfNombre.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                productoSeleccionado.setNombre(tfNombre.getText());
            }

            if (pattern1.matcher(tfMaterial.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un material valido que no contenga ni numeros ni caracteres especiales", ButtonType.OK);
                alert.setHeaderText(null);
                tfMaterial.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                productoSeleccionado.setMaterial(tfMaterial.getText());
            }

            if (pattern2.matcher(tfPrecio.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un precio valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                alert.setHeaderText(null);
                tfPrecio.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                productoSeleccionado.setPrecio(Float.parseFloat(tfPrecio.getText()));
            }

            if (pattern2.matcher(tfAltura.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca una altura valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                alert.setHeaderText(null);
                tfAltura.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                productoSeleccionado.setAltura(Integer.parseInt(tfPrecio.getText()));
            }

            if (pattern2.matcher(tfPeso.getText()).find() == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un peso valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                alert.setHeaderText(null);
                tfPeso.setText("");
                alert.show();
                throw new InvalidFormatException();
            } else {
                productoSeleccionado.setPeso(Float.parseFloat(tfPeso.getText()));
            }

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
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotCompletedException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Mostrar un diálogo en el que se pida confirmar al usuario si quiere
     * borrar el producto: En caso de cancelar, cerrar el diálogo de
     * confirmación. En caso de confirmar, eliminar producto, actualizar la
     * tabla, deseleccionar la fila seleccionada de la tabla, vaciar los campos
     * “Nombre” (txtFieldNombre), “Precio” (txtFieldPrecio), “Talla”
     * (txtFieldTalla), “Fecha” (dateFecha), “Material” (txtFieldMaterial),
     * “Peso” (txtFieldPeso) y “Descripción” (txtFieldDescripcion) y enfocar el
     * campo “Nombre (txtFieldNombre)”.
     *
     * @param actionevent
     */
    private void handleDeleteProducto(ActionEvent actionevent) {
        ObservableList<Producto> productosSeleccionados = tbProductos.getSelectionModel().getSelectedItems();

        ProductoInterface pi = ProductoFactoria.createInterface();

        for (int i = 0; i < productosSeleccionados.size(); i++) {
            pi.remove(productosSeleccionados.get(i).getIdProducto().toString());
        }

        cleanFields();
        handleCargeTable();
    }

    /**
     * Habilita o desabilita los textfield de filtros segun el tipo de filtro
     * seleccionado en la combobox
     *
     * @param event
     */
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
            tfFiltro1.setPromptText("Valor Minimo");
            tfFiltro2.setPromptText("Valor Maximo");
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
            tfFiltro1.setPromptText("Valor Minimo");
            tfFiltro2.setPromptText("Valor Maximo");
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
            tfFiltro1.setPromptText("Valor Minimo");
            tfFiltro2.setPromptText("Valor Maximo");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(false);
            cleanFields();
        }
    }

    /**
     * Se comprueba si los valores introducidos en los textFields necesarios
     * para el filtro seleccionado, en caso de que el filtro los necesite,
     * tienen un formato correcto. En caso de que no lo tenga, se lanza una
     * excepción informando del error. En caso de que tengan un formato
     * correcto, se actualizará la información de la tabla aplicando el filtro
     * seleccionado por el usuario.
     *
     * @param actionevent
     */
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
            if (Integer.parseInt(tfFiltro1.getText()) > Integer.parseInt(tfFiltro2.getText())) {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de filtro: \nPorfavor introduzca en el campo superior el valor mas pequeño y en el inferior el mas grande", ButtonType.OK);
                    alert.setHeaderText(null);
                    tfFiltro1.setText("");
                    tfFiltro2.setText("");
                    alert.show();
                    throw new InvalidFormatException();
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
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
            }

        } else if (filtroSeleccionado.equalsIgnoreCase("Menor precio")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPrecio() >= Float.parseFloat(tfFiltro1.getText())) {
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
            if (Integer.parseInt(tfFiltro1.getText()) > Integer.parseInt(tfFiltro2.getText())) {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de filtro: \nPorfavor introduzca en el campo superior el valor mas pequeño y en el inferior el mas grande", ButtonType.OK);
                    alert.setHeaderText(null);
                    tfFiltro1.setText("");
                    tfFiltro2.setText("");
                    alert.show();
                    throw new InvalidFormatException();
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
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
            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor peso")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.findAll_XML(new GenericType<List<Producto>>() {
            });

            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getPeso() >= Float.parseFloat(tfFiltro1.getText())) {
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
            if (Integer.parseInt(tfFiltro1.getText()) > Integer.parseInt(tfFiltro2.getText())) {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de filtro: \nPorfavor introduzca en el campo superior el valor mas pequeño y en el inferior el mas grande", ButtonType.OK);
                    alert.setHeaderText(null);
                    tfFiltro1.setText("");
                    tfFiltro2.setText("");
                    alert.show();
                    throw new InvalidFormatException();
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
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
    }

    /**
     * Carga la tabla aplicando los filtros
     *
     * @param productos
     */
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

    /**
     * Vacia los campos del formulario
     */
    private void cleanFields() {
        tfId.setText("");
        tfNombre.setText("");
        tfAltura.setText("");
        tfPeso.setText("");
        tfMaterial.setText("");
        tfPrecio.setText("");
        dpFecha.setValue(null);
        dpFecha.setDisable(false);
    }

    /**
     * Método que solicita confirmación antes de cerrar la ventana cuando se
     * pulsa la x de la parte superior derecha.
     *
     * @param windowEvent
     */
    @FXML
    public void handleCloseWindow(WindowEvent windowEvent) {
        windowEvent.consume();

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Quieres cerrar la ventana?");
        alerta.setHeaderText(null);

        Optional<ButtonType> accion = alerta.showAndWait();
        if (accion.get() == ButtonType.OK) {
            Platform.exit();
        }

    }

}
