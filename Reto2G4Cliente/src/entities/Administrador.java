/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Clase que representa a un administrador del sistema. Un administrador puede
 * organizar eventos y tiene un contador de eventos organizados.
 *
 * @author Iñigo
 */
@XmlRootElement(name = "administrador")
public class Administrador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty numEventos;
    private SimpleObjectProperty<List<Evento>> listaEventos;

    public Administrador(int numEventos, List<Evento> listaEventos, int idUsuario, String nombre, String password, String correo, Date fechaNacimiento) {
        super(idUsuario, nombre, password, correo, fechaNacimiento);
        this.numEventos = new SimpleIntegerProperty(numEventos);
        this.listaEventos = new SimpleObjectProperty<>(listaEventos);
    }

    public Administrador() {
        super();
        this.numEventos = new SimpleIntegerProperty();
        this.listaEventos = new SimpleObjectProperty<>();
    }

    public Integer getNumEventos() {
        return numEventos.get();
    }

    public void setNumEventos(Integer numEventos) {
        this.numEventos.set(numEventos);
    }

    @XmlTransient
    public List<Evento> getListaEventosEvento() {
        return listaEventos.get();
    }

    public void setListaEventosEvento(List<Evento> listaEventosEvento) {
        this.listaEventos.set(listaEventosEvento);
    }

    @Override
    public String toString() {
        return "Administrador{" + "numEventos=" + numEventos + ", listaEventos=" + listaEventos + '}';
    }

}
