/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iñigo
 */
@XmlRootElement(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty idEvento;
    private SimpleObjectProperty<Date> fecha;
    private SimpleFloatProperty totalRecaudado;
    private SimpleIntegerProperty numParticipantes;
    private SimpleObjectProperty<Cliente> cliente;
    private SimpleObjectProperty<Administrador> admin;
    private ArrayList<Object> administradores;
    private ArrayList<Object> listaTiendasEvento;
    

    public Evento(Integer idEvento, Float totalRecaudado, Integer numParticipantes, Date fechaCreacion, Administrador admin) {
        this.idEvento = new SimpleIntegerProperty(idEvento);
        this.fecha = new SimpleObjectProperty<>(fechaCreacion);
        this.totalRecaudado = new SimpleFloatProperty(totalRecaudado);
        this.numParticipantes = new SimpleIntegerProperty(numParticipantes);
        if (admin == null) {
        throw new IllegalArgumentException("El administrador no puede ser nulo.");
            }
        this.admin = new SimpleObjectProperty<>(admin);
    }

    public Evento() {
        this.idEvento = new SimpleIntegerProperty();
        this.fecha = new SimpleObjectProperty<>();
        this.totalRecaudado = new SimpleFloatProperty();
        this.numParticipantes = new SimpleIntegerProperty();
        this.cliente = new SimpleObjectProperty<>();
        this.administradores = new ArrayList<>();
        this.listaTiendasEvento = new ArrayList<>();
        
    }

    public Integer getIdEvento() {
        return idEvento.get();
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento.set(idEvento);
    }

    public Integer getNumParticipantes() {
        return numParticipantes.get();
    }

    public void setNumParticipantes(Integer numParticipantes) {
        this.numParticipantes.set(numParticipantes);
    }

    public Float getTotalRecaudado() {
        return totalRecaudado.get();
    }

    public void setTotalRecaudado(Float totalRecaudado) {
        this.totalRecaudado.set(totalRecaudado);
    }

    public Date getFecha() {
        return fecha.get();
    }

    public void setFecha(Date fecha) {
        this.fecha.set(fecha);
    }

    public Cliente getCliente() {
        return cliente.get();
    }

    public void setCliente(Cliente cliente) {
        this.cliente.set(cliente);
    }

   @XmlTransient
    public Administrador getAdministrador() {
        if (administradores != null && !administradores.isEmpty()) {
        return (Administrador) administradores.get(0);
        } else {
        return null;
    }
}

    public void setAdministrador(Administrador admin) {
        this.admin.set(admin);
    }
    
    public ArrayList<Object> getAdministradores() {
    if (administradores == null) {
        administradores = new ArrayList<>();
    }
    return administradores;
}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idEvento);
        return hash;
    }

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
    final Evento other = (Evento) obj;
    if (!Objects.equals(this.idEvento.get(), other.idEvento.get())) {
        return false;
    }
    return true;
}
    
    @Override
    public String toString() {
        return "Evento{" + "idEvento=" + idEvento + ", fecha=" + fecha + ", numParticipantes=" + numParticipantes + "admin=" + admin + '}';
    }

}
