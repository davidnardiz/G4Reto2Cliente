/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Usuario;
import exceptions.IncorrectCredentialsException;
import exceptions.InvalidFormatException;
import exceptions.NotCompletedException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.GenericType;
import service.ClienteFactoria;
import service.ClienteInterface;
import service.UsuarioFactoria;
import service.UsuarioInterface;

/**
 *
 * @author Gonzalo
 */
public class ControllerRecuperarContrasenia {

    private Stage stage;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnEnviar;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        txtFieldEmail.setText("daviznardiz2004@gmail.com");

        stage.setOnCloseRequest(this::handleCloseWindow);
        btnCancelar.setOnAction(this::handleVolver);
        btnEnviar.setOnAction(this::handleEnviar);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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

    @FXML
    public void handleVolver(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signIn.fxml"));
            Parent root;

            root = (Parent) loader.load();

            ControllerSignIn viewController = ((ControllerSignIn) loader.getController());
            viewController.setStage(stage);
            viewController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ControllerTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleEnviar(ActionEvent actionEvent) {
        String email = txtFieldEmail.getText();
        String passNueva;
        try {
            if (email == null) {
                throw new NotCompletedException("Debes introducir el email!!");
            } else if (!checkEmailFormat(email)) {
                throw new InvalidFormatException("El email no tiene el formato correcto!!");
            }

            UsuarioInterface ui = UsuarioFactoria.getUserInterface();
            Usuario us = ui.findByCorreo_XML(new GenericType<Usuario>() {
            }, email);
            System.out.println(us.toString());
            if (us == null) {
                throw new IncorrectCredentialsException("El email introducido no pertenece a ninguna cuenta!!");
            }

            passNueva = enviarEmail(email);
            System.out.println(passNueva);
            us.setPassword(passNueva);
            System.out.println(us.toString());

            ClienteInterface ci = ClienteFactoria.getClienteInterface();
            ci.edit_XML(us, us.getIdUsuario().toString());

            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Se ha enviado su nueva contraseña al email!!", ButtonType.OK);
            alerta.setHeaderText(null);
            alerta.show();

            handleVolver(actionEvent);

        } catch (NotCompletedException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerRecuperarContrasenia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectCredentialsException ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            alerta.setHeaderText(null);
            alerta.show();
            Logger.getLogger(ControllerRecuperarContrasenia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ControllerRecuperarContrasenia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String enviarEmail(String email) {
        final String ZOHO_HOST = "smtp.zoho.eu";
        final String TLS_PORT = "897";

        final String SENDER_USERNAME = "g4reto2@zohomail.eu";
        final String SENDER_PASSWORD = "5t80H73np8Nw";

        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", ZOHO_HOST);
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtps.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        String cadena = "";

        try {
            String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

            for (int x = 0; x < 8; x++) {
                int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
                char caracterAleatorio = banco.charAt(indiceAleatorio);
                cadena += caracterAleatorio;
            }

            final MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            msg.setSubject("Nueva Contraseña");
            msg.setText("Ésta es su nueva contraseña: \n" + cadena, "utf-8", "html");
            msg.setSentDate(new Date());

            Transport transport = session.getTransport("smtps");

            transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
        return cadena;
    }

    private boolean checkEmailFormat(String texto) {
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9._]{3,}+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");

        Matcher matcher = pattern1.matcher(texto);
        return matcher.find();
    }

    public static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

}
