/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
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
    private int numEventos;
    private List<Evento> eventosOrganizados;

    public int getNumEventos() {
        return numEventos;
    }

    public void setNumEventos(int numEventos) {
        this.numEventos = numEventos;
    }

    public List<Evento> getEventosOrganizados() {
        return eventosOrganizados;
    }

    public void setEventosOrganizados(List<Evento> eventosOrganizados) {
        this.eventosOrganizados = eventosOrganizados;
    }

}
