/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;

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
    private SimpleObjectProperty<List<Producto>> productos;
    private SimpleObjectProperty<List<Evento>> listaEventos;

    public Evento(Integer idEvento, Float totalRecaudado, Integer numParticipantes, Date fechaCreacion, Administrador admin, Cliente cliente, List<Producto> productos, List<Evento> listaEventos) {
        this.idEvento = new SimpleIntegerProperty(idEvento);
        this.fecha = new SimpleObjectProperty<>(fechaCreacion);
        this.totalRecaudado = new SimpleFloatProperty(totalRecaudado);
        this.numParticipantes = new SimpleIntegerProperty(numParticipantes);
        this.cliente = new SimpleObjectProperty<>(cliente);
        this.productos = new SimpleObjectProperty<>(productos);
        this.listaEventos = new SimpleObjectProperty<>(listaEventos);
    }

    public Evento() {
        this.idEvento = new SimpleIntegerProperty();
        this.fecha = new SimpleObjectProperty<>();
        this.totalRecaudado = new SimpleFloatProperty();
        this.numParticipantes = new SimpleIntegerProperty();
        this.cliente = new SimpleObjectProperty<>();
        this.productos = new SimpleObjectProperty<>();
        this.listaEventos = new SimpleObjectProperty<>();
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

    public Administrador getAdministrador() {
        return admin.get();
    }

    public void setAdministrador(Administrador admin) {
        this.admin.set(admin);
    }

    public List<Producto> getProductos() {
        return productos.get();
    }

    public void setProductos(List<Producto> productos) {
        this.productos.set(productos);
    }

    public List<Evento> getListaEventosEvento() {
        return listaEventos.get();
    }

    public void setListaEventosEvento(List<Evento> listaEventosEvento) {
        this.listaEventos.set(listaEventosEvento);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idEvento);
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
        final Evento other = (Evento) obj;
        if (this.idEvento != other.idEvento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evento{" + "idEvento=" + idEvento + ", fecha=" + fecha + ", numParticipantes=" + numParticipantes + "admin=" + admin + '}';
    }

}
