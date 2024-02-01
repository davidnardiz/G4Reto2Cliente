/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cliente;
import entities.Producto;
import entities.Usuario;
import exceptions.InvalidFormatException;
import exceptions.NotCompletedException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import service.ProductoFactoria;
import service.ProductoInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerProductos {

    private Stage stage;
    private Usuario usuario;
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
    @FXML
    private MenuItem miInicio;
    @FXML
    private MenuItem miPerfil;
    @FXML
    private MenuItem miProductos;
    @FXML
    private MenuItem miTiendas;
    @FXML
    private MenuItem miEventos;
    @FXML
    private MenuItem miCerrarSesion;
    @FXML
    private MenuItem miAyuda;

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
        cbFiltro.getItems().add("Por Nombre");
        cbFiltro.getItems().add("Menor Altura");
        cbFiltro.getItems().add("Mayor Altura");
        cbFiltro.getItems().add("Entre Altura");
        cbFiltro.getItems().add("Menor Precio");
        cbFiltro.getItems().add("Mayor Precio");
        cbFiltro.getItems().add("Entre Precio");
        cbFiltro.getItems().add("Menor Peso");
        cbFiltro.getItems().add("Mayor Peso");
        cbFiltro.getItems().add("Entre peso");

        //Metodos de los botones
        buttonAñadir.setOnAction(this::handleCreateProducto);
        buttonEliminar.setOnAction(this::handleDeleteProducto);
        buttonModificar.setOnAction(this::handleEditProducto);
        cbFiltro.setOnAction(this::handleFiltros);
        buttonFiltrar.setOnAction(this::handleFiltrar);
        buttonInforme.setOnAction(this::handleInforme);
        stage.setOnCloseRequest(this::handleCloseWindow);

        //MenuBar
        miInicio.setOnAction(this::handleAbrirInicio);
        miPerfil.setOnAction(this::handleAbrirPerfil);
        miProductos.setOnAction(this::handleAbrirProductos);
        miTiendas.setOnAction(this::handleAbrirTiendas);
        miEventos.setOnAction(this::handleAbrirEventos);
        miCerrarSesion.setOnAction(this::handleCerrarSesion);
        miAyuda.setOnAction(this::handleAyuda);

        //Asignar las celdas de la tabla
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

        //Seleccionar un objeto de la tabla
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

        //Carga la tabla con distintos datos en funcion del tipo 
        if (usuario instanceof Cliente) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
            }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
            handleChargeFiltro(productos);
        } else {
            handleCargeTable();
        }

        stage.show();

    }

    //Pasar la stage y el usuario en la clase
    public void setStage(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
    }

    /**
     * Carga la tabla con todos los Productos
     */
    private void handleCargeTable() {
        List<Producto> productos = new ArrayList();
        ProductoInterface ti = ProductoFactoria.createInterface();
        productos = ti.findAll_XML(new GenericType<List<Producto>>() {
        });
        ObservableList<Producto> productosTabla = FXCollections.observableArrayList(productos);
        for (int i = 0; i < productosTabla.size(); i++) {
            System.out.println(productosTabla.get(i).toString());
        }

        tbProductos.setItems(productosTabla);
        tbProductos.refresh();

    }

    /**
     * Accion del menubar para ir a la ventana de tiendas
     * @param actionEvent 
     */
    @FXML
    public void handleAbrirTiendas(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Tiendas.fxml"));
            Parent root = loader.load();

            ControllerTiendas productController = ((ControllerTiendas) loader.getController());
            productController.setStage(stage, usuario);
            productController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Accion del menubar para volver a la pagina de SignIn
     * @param event 
     */
    private void handleCerrarSesion(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root = (Parent) loader.load();
            ControllerSignIn viewController = ((ControllerSignIn) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Accion del menubar para abrir la pagina principal de la aplicacion
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
     * Accion de menubar para abrir la ventana de productos
     * @param actionEvent 
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
     * Accion de menubar para abrir la ventana de eventos
     * @param actionEvent 
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
     * Accion de menubar para abrir la ventana de perfil
     * @param actionEvent 
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

    @FXML
    public void handleAbrirAyuda(ActionEvent actionEvent) {
        AyudaControllerSingletone.getInstance().mostrarVentanaAyudaProductos();

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
            if (usuario instanceof Cliente) {
                producto.setCliente((Cliente) usuario);
                producto.setTienda(((Cliente) usuario).getTienda());
                System.out.println("Tienda: " + producto.getTienda());
                ProductoInterface pi = ProductoFactoria.createInterface();
                pi.create_XML(producto);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha creado correctamente el producto", ButtonType.OK);
                alert.setHeaderText(null);
                tfNombre.setText("");
                alert.show();
                cleanFields();
                List<Producto> productos = new ArrayList();
                ProductoInterface ti = ProductoFactoria.createInterface();
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                producto.setCliente(null);
                producto.setTienda(null);
                ProductoInterface pi = ProductoFactoria.createInterface();
                pi.create_XML(producto);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha creado correctamente el producto", ButtonType.OK);
                alert.setHeaderText(null);
                tfNombre.setText("");
                alert.show();
                cleanFields();
                handleCargeTable();
            }
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error : \nPorfavor rellene todos los campos", ButtonType.OK);
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
            Producto productoSelec = tbProductos.getSelectionModel().getSelectedItem();
            if (productoSelec == null) {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de edicion: \nPorfavor seleccione un producto en la tabla", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.show();
                    throw new NotCompletedException();
                } catch (NotCompletedException ex) {
                    Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                checkCompleteFields();
                Pattern pattern1 = Pattern.compile("[^\\p{L}\\p{M}\\dñÑáéíóúÁÉÍÓÚ\\s]");
                Pattern pattern2 = Pattern.compile("[a-zA-Z]");
                Producto productoSeleccionado = tbProductos.getSelectionModel().getSelectedItem();

                if (pattern1.matcher(tfNombre.getText()).find() == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de edicion: \nPorfavor introduzca un nombre valido que no contenga ni numeros ni caracteres especiales", ButtonType.OK);
                    alert.setHeaderText(null);
                    tfNombre.setText("");
                    alert.show();
                    throw new InvalidFormatException();
                } else {
                    productoSeleccionado.setNombre(tfNombre.getText());
                }

                if (pattern1.matcher(tfMaterial.getText()).find() == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de edicion: \nPorfavor introduzca un material valido que no contenga ni numeros ni caracteres especiales", ButtonType.OK);
                    alert.setHeaderText(null);
                    tfMaterial.setText("");
                    alert.show();
                    throw new InvalidFormatException();
                } else {
                    productoSeleccionado.setMaterial(tfMaterial.getText());
                }

                if (pattern2.matcher(tfPrecio.getText()).find() == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de edicion: \nPorfavor introduzca un precio valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                    alert.setHeaderText(null);
                    tfPrecio.setText("");
                    alert.show();
                    throw new InvalidFormatException();
                } else {
                    productoSeleccionado.setPrecio(Float.parseFloat(tfPrecio.getText()));
                }

                if (pattern2.matcher(tfAltura.getText()).find() == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de edicion: \nPorfavor introduzca una altura valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
                    alert.setHeaderText(null);
                    tfAltura.setText("");
                    alert.show();
                    throw new InvalidFormatException();
                } else {
                    productoSeleccionado.setAltura(Integer.parseInt(tfAltura.getText()));
                }

                if (pattern2.matcher(tfPeso.getText()).find() == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error de edicion: \nPorfavor introduzca un peso valido que no contenga ni letras ni caracteres especiales (A excepcion de . y ,)", ButtonType.OK);
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
                if (usuario instanceof Cliente) {
                    List<Producto> productos = new ArrayList();
                    ProductoInterface ti = ProductoFactoria.createInterface();
                    productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                    }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha editado conrrectamenta el producto");
                    alert.setHeaderText(null);
                    alert.show();
                    handleChargeFiltro(productos);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha editado conrrectamenta el producto");
                    alert.setHeaderText(null);
                    alert.show();
                    cleanFields();
                    handleCargeTable();
                }
            }

        } catch (ParseException | InvalidFormatException | NotCompletedException ex) {
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
        Producto productoSelec = tbProductos.getSelectionModel().getSelectedItem();
        if (productoSelec == null) {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de eliminacion: \nPorfavor seleccione un producto en la tabla", ButtonType.OK);
                alert.setHeaderText(null);
                alert.show();
                throw new NotCompletedException();
            } catch (NotCompletedException ex) {
                Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            ObservableList<Producto> productosSeleccionados = tbProductos.getSelectionModel().getSelectedItems();

            ProductoInterface pi = ProductoFactoria.createInterface();

            for (int i = 0; i < productosSeleccionados.size(); i++) {
                pi.remove(productosSeleccionados.get(i).getIdProducto().toString());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Eliminacion realizada correctamente");
                alert.setHeaderText(null);
                alert.show();
            }

            cleanFields();
            if (usuario instanceof Cliente) {
                List<Producto> productos = new ArrayList();
                ProductoInterface ti = ProductoFactoria.createInterface();
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                handleCargeTable();
            }
        }
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
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
            tfFiltro1.setDisable(true);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Por Nombre")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Menor altura")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor altura")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
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
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor precio")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
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
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
            tfFiltro1.setDisable(false);
            tfFiltro2.setDisable(true);
            cleanFields();
        } else if (filtroSeleccionado.equalsIgnoreCase("Mayor peso")) {
            tfFiltro1.setText("");
            tfFiltro2.setText("");
            tfFiltro1.setPromptText("");
            tfFiltro2.setPromptText("");
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
            if (usuario instanceof Cliente) {
                List<Producto> productos = new ArrayList();
                ProductoInterface ti = ProductoFactoria.createInterface();
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
                cleanFields();

            } else {
                handleCargeTable();
                cleanFields();

            }
        } else if (filtroSeleccionado.equalsIgnoreCase("Por Nombre")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            if (usuario instanceof Cliente) {
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                });
            }
            List<Producto> productoTabla = new ArrayList();
            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getNombre().toLowerCase().contains(tfFiltro1.getText().toLowerCase())) {
                    productoTabla.add(productos.get(i));
                }
            }
            handleChargeFiltro(productoTabla);
            cleanFields();

        } else if (filtroSeleccionado.equalsIgnoreCase("Menor altura")) {
            List<Producto> productos = new ArrayList();
            ProductoInterface ti = ProductoFactoria.createInterface();
            if (usuario instanceof Cliente) {
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                });
            }

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
            if (usuario instanceof Cliente) {
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                });
            }

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
                if (usuario instanceof Cliente) {
                    productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                    }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                    handleChargeFiltro(productos);
                } else {
                    productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                    });
                }

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
            if (usuario instanceof Cliente) {
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                });
            }

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
            if (usuario instanceof Cliente) {
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                });
            }

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
                if (usuario instanceof Cliente) {
                    productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                    }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                    handleChargeFiltro(productos);
                } else {
                    productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                    });
                }

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
            if (usuario instanceof Cliente) {
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                });
            }

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
            if (usuario instanceof Cliente) {
                productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                handleChargeFiltro(productos);
            } else {
                productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                });
            }

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
                if (usuario instanceof Cliente) {
                    productos = ti.encontrarProductoTodosTienda_XML(new GenericType<List<Producto>>() {
                    }, String.valueOf(((Cliente) usuario).getTienda().getIdTienda()));
                    handleChargeFiltro(productos);
                } else {
                    productos = ti.findAll_XML(new GenericType<List<Producto>>() {
                    });
                }

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

    /**
     * Metodo para generar un imforme con los datos de la tabla de productos
     * @param actionevent 
     */
    @FXML
    private void handleInforme(ActionEvent actionevent) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/productoReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Producto>) this.tbProductos.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alerta.setHeaderText(null);
            System.out.println(e.getMessage());
            alerta.show();
        }
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

    /**
     * Maneja el evento de ayuda en la ventana de productos. Este método invoca
     * el método {@link ControllerAyudas#mostrarVentanaAyudaProductos()} para
     * mostrar una ventana de ayuda específica para la interfaz de productos.
     *
     * @param event El evento de acción que desencadena esta operación.
     * @see ControllerAyudas#mostrarVentanaAyudaProductos()
     */
    @FXML
    public void handleAyuda(ActionEvent event) {
        ControllerAyudas.getInstance().mostrarVentanaAyudaProductos();
    }

}
