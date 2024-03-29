/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * Clase base para representar a todos los usuarios de MarketMaker, ya sean
 * clientes o administradores. Las clases Cliente y Administrador heredan de
 * esta clase. Contiene atributos genéricos comunes.
 *
 * @author Iñigo
 */
@XmlRootElement(name = "usuario")
@XmlSeeAlso({Cliente.class, Administrador.class})
public class Usuario implements Serializable {

    //Variables.
    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty idUsuario;
    private SimpleStringProperty nombre;
    private SimpleStringProperty password;
    private SimpleStringProperty correo;
    private SimpleObjectProperty<Date> fechaNacimiento;

    //Constructores
    public Usuario(Integer idUsuario, String nombre, String password, String correo, Date fechaNacimiento) {
        this.idUsuario = new SimpleIntegerProperty(idUsuario);
        this.nombre = new SimpleStringProperty(nombre);
        this.password = new SimpleStringProperty(password);
        this.correo = new SimpleStringProperty(correo);
        this.fechaNacimiento = new SimpleObjectProperty<>(fechaNacimiento);
    }

    public Usuario() {
        this.idUsuario = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.correo = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty<>();
    }

    //Getters y setters
    public Integer getIdUsuario() {
        return idUsuario.get();
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario.set(idUsuario);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    //Métodos
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.idUsuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombre=" + nombre + ", password=" + password + ", correo=" + correo + ", fechaNacimiento=" + fechaNacimiento + '}';
    }

}
