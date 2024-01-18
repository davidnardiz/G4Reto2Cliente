/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Clase que representa a un administrador en MarketMakers. Hereda de la clase
 * base Usuario. Contiene información específica de los administradores, como el
 * número de eventos que han creado y administrado.
 *
 * @author inigo
 */
@XmlRootElement(name = "Administrador")
public class Administrador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private SimpleIntegerProperty numEventos;
    private SimpleObjectProperty<List<Evento>> eventosOrganizados;

    public Administrador(Integer numEventos, List<Evento> eventosOrganizados) {
        this.numEventos = new SimpleIntegerProperty(numEventos);
        this.eventosOrganizados = new SimpleObjectProperty<>(eventosOrganizados);
    }

    public Administrador() {
        this.numEventos = new SimpleIntegerProperty();
        this.eventosOrganizados = new SimpleObjectProperty<>();
    }

    public int getNumEventos() {
        return numEventos.get();
    }

    public void setNumEventos(int numEventos) {
        this.numEventos.set(numEventos);
    }

    public List<Evento> getEventosOrganizados() {
        return eventosOrganizados.get();
    }

    public void setEventosOrganizados(List<Evento> eventosOrganizados) {
        this.eventosOrganizados.set(eventosOrganizados);
    }

    @Override
    public String toString() {
        return super.toString() + "Administrador{" + "numEventos=" + numEventos + ", eventosOrganizados=" + eventosOrganizados + '}';
    }
}
