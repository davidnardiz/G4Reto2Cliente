/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cliente;
import entities.Usuario;
import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import service.ClienteFactoria;
import service.ClienteInterface;
import service.UsuarioFactoria;
import service.UsuarioInterface;

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
    
    @FXML
    public void handleSignUp(ActionEvent event) {
       
        try {
            checkCompleteFields();
            checkValidEmail();
            
            Cliente cl = new Cliente();
            cl.setCorreo(txtFieldUsuario1.getText());
            cl.setNombre(txtFieldUsuario.getText());
            cl.setPassword(PassFieldR.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion;
            fechaCreacion = dateFormat.parse(dpFehcaNace.getValue().toString());
            cl.setFechaNacimiento(fechaCreacion);
            System.out.println("Correo: "+cl.getCorreo());
            ClienteInterface ci = ClienteFactoria.getClienteInterface();
            ci.create_XML(cl);
            
            
        } catch (ParseException ex) {
            Logger.getLogger(ControllerSignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void handleOpenSignIn(ActionEvent event) {
        
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
 
    private void checkCompleteFields() {
        if (txtFieldUsuario1.getText().isEmpty() || PassFieldR.getText().isEmpty() || PassFieldR1.getText().isEmpty() || txtFieldUsuario.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor rellene todos los campos", ButtonType.OK);
            alert.setHeaderText(null);
            alert.show();
        }
    }
    
    private void checkValidEmail() {
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");
        Pattern pattern2 = Pattern.compile("^[a-zA-Z0-9._]{3,}+@(.+)$");

        Matcher matcher = pattern1.matcher(txtFieldUsuario1.getText());
        if (matcher.find() == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un correo valido", ButtonType.OK);
            alert.setHeaderText(null);
            alert.show();

        }

        matcher = pattern2.matcher(txtFieldUsuario1.getText());
        if (matcher.find() == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error de registro: \nPorfavor introduzca un correo que contenga 3 caracteres antes del @", ButtonType.OK);
            alert.setHeaderText(null);
            alert.show();
        }

    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}



