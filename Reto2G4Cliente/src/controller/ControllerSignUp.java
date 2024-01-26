/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cliente;
import exceptions.InvalidEmailFormatException;
import exceptions.InvalidFormatException;
import exceptions.NotCompletedException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.ClienteFactoria;
import service.ClienteInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerSignUp {

    Stage stage;

    @FXML
    private Button btnRegistrarse;
    @FXML
    private TextField txtFieldUsuario1;
    @FXML
    private TextField txtFieldUsuario;
    @FXML
    private PasswordField PassFieldR;
    @FXML
    private PasswordField PassFieldR1;
    @FXML
    private DatePicker dpFehcaNace;
    @FXML
    private Hyperlink hyperLinkR;

    /**
     * Mostrar la ventana. La venta debe ser Modal La ventana no debe ser
     * redimensionable. Todo debe estar habilitado. Establecer el focus en campo
     * del email (txtFieldEmail) El botón por defecto debe ser el botón de
     * Iniciar sesión (btnIniciarSesion) Establecer el título de ventana al
     * valor: “Sign Up”.
     *
     * @param root pasamos el root para incializar la escena
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignUp");
        stage.setResizable(false);
        txtFieldUsuario1.requestFocus();
        btnRegistrarse.setDefaultButton(true);
        btnRegistrarse.setOnAction(this::handleSignUp);
        hyperLinkR.setOnAction(this::handleOpenSignIn);

        stage.setOnCloseRequest(this::handleCloseRequest);
        stage.show();
    }

    /**
     * Si se cumplen todas las condiciones se llamará a un método de lógica
     * llamado SingUp en la capa de lógica y enviará los datos introducidos al
     * lado servidor para registrarlos: Si el registro no se a podido completar
     * lanzar una excepcion con invalidSignUp con el texto “Email repetido” Si
     * el registro resulta exitoso mostrar un aviso con el texto “Registro
     * realizado correctamente”. Una vez se cierre el aviso, cerrar la ventana
     * Sign Up y abrir ventana de singIn. Si se produce cualquier excepción en
     * el proceso, mostrar un mensaje al usuario con el texto de la excepción.
     *
     * @param event El evento de acción.
     */
    @FXML
    public void handleSignUp(ActionEvent event) {

        try {
            checkCompleteFields();
            checkValidEmail();

            Cliente cl = new Cliente();
            Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9]{1,20}$");
            Pattern pattern2 = Pattern.compile("^[a-zA-Z0-9]{8,}$");

            cl.setCorreo(txtFieldUsuario1.getText());
            if (pattern1.matcher(txtFieldUsuario.getText()).find() == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un nombre valido que no contenga caracteres especiales y como maximo 20 caracteres o digitos", ButtonType.OK);
                alert.setHeaderText(null);
                alert.show();
                throw new InvalidFormatException();
            } else {
                cl.setNombre(txtFieldUsuario.getText());
            }

            if (pattern2.matcher(PassFieldR.getText()).find() == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un nombre valido que no contenga caracteres especiales y como minimo 8 letras o digitos", ButtonType.OK);
                alert.setHeaderText(null);
                alert.show();
                throw new InvalidFormatException();
            } else if (!PassFieldR.getText().equals(PassFieldR1.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor haga que coincidan ambas contraseñas", ButtonType.OK);
                alert.setHeaderText(null);
                alert.show();
                throw new InvalidFormatException();
            } else {
                cl.setPassword(PassFieldR.getText());
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFehcaNace.getValue().toString());
            cl.setFechaNacimiento(fechaCreacion);
            System.out.println("Correo: " + cl.getCorreo());
            ClienteInterface ci = ClienteFactoria.getClienteInterface();
            ci.create_XML(cl);
            handleOpenSignIn(event);

        } catch (ParseException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotCompletedException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidEmailFormatException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hyperlink: abrir la ventana signUp con el metodo openSignUp y cerrar la
     * ventana signIn.
     *
     * @param event El evento de acción.
     */
    @FXML
    public void handleOpenSignIn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/signIn.fxml"));
            Parent root = (Parent) loader.load();
            ControllerSignIn viewController
                    = ((ControllerSignIn) loader.getController());
//            viewController.setStage(stage);
//            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleCloseRequest(WindowEvent windowEvent) {
        windowEvent.consume();

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Quieres cerrar la ventana?");
        alerta.setHeaderText(null);

        Optional<ButtonType> accion = alerta.showAndWait();
        if (accion.get() == ButtonType.OK) {
            Platform.exit();
        }

    }

    /**
     * Validar que todos los textfield estén informados mediante el método
     * checkCompleteFields. Si no están informados lanzar una excepción
     * notCompleteException con el texto “Los campos no están informados”.
     *
     * @throws NotCompleteExceptionException
     */
    private void checkCompleteFields() throws NotCompletedException {
        if (txtFieldUsuario1.getText().isEmpty() || PassFieldR.getText().isEmpty() || PassFieldR1.getText().isEmpty() || txtFieldUsuario.getText().isEmpty()||dpFehcaNace.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor rellene todos los campos", ButtonType.OK);
            alert.setHeaderText(null);
            alert.show();
            throw new NotCompletedException();
        }
    }

    /**
     * Validar que la dirección de email contenga un @ y al menos un punto
     * mediante el método de lógica checkValidEmail. Implementar en este
     * expresiones regulares, la clase Pattern de Java y el método match. Si no
     * se cumplen dichas condiciones lanzar un excepción invalidEmailFormat con
     * el texto “La dirección de email debe tener un @ y al menos un punto” . *
     *
     * Validar que el correo escrito tenga un mínimo de tres caracteres antes
     * del @ mediante el método de lógica checkValidEmail. Implementar en este
     * expresiones regulares, la clase Pattern de Java y el método match. Si no
     * se cumplen dicho número de caracteres lanzar una excepción
     * invalidEmailFormat con el texto “La dirección de email debe tener al
     * menos tres caracteres antes del @”
     *
     * @throws InvalidEmailFormatException
     */
    private void checkValidEmail() throws InvalidEmailFormatException {
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");
        Pattern pattern2 = Pattern.compile("^[a-zA-Z0-9._]{3,}+@(.+)$");

        Matcher matcher = pattern1.matcher(txtFieldUsuario1.getText());
        if (matcher.find() == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un correo valido", ButtonType.OK);
            alert.setHeaderText(null);
            alert.show();
            throw new InvalidEmailFormatException();
        }

        matcher = pattern2.matcher(txtFieldUsuario1.getText());
        if (matcher.find() == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un correo que contenga 3 caracteres antes del @", ButtonType.OK);
            alert.setHeaderText(null);
            alert.show();
            throw new InvalidEmailFormatException();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
