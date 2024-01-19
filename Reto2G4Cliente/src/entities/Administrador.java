/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 *
 * Clase que representa a un administrador del sistema. Un administrador puede
 * organizar eventos y tiene un contador de eventos organizados.
 *
 * @author Iñigo
 */
public class Administrador {

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty numEventos;
    private SimpleObjectProperty<List<Evento>> listaEventos;

    public Integer getNumEventos() {
        return numEventos.get();
    }

    public void setNumEventos(Integer numEventos) {
        this.numEventos.set(numEventos);
    }

    public List<Evento> getListaEventosEvento() {
        return listaEventos.get();
    }

    public void setListaEventosEvento(List<Evento> listaEventosEvento) {
        this.listaEventos.set(listaEventosEvento);
    }

    @Override
    public String toString() {
        return "Evento{" + "numEventos=" + numEventos + ", listaEventos=" + listaEventos + '}';
    }

}
