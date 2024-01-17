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
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@XmlRootElement(name = "tienda")
public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty idTienda;
    private SimpleStringProperty nombre;
    private SimpleStringProperty descripcion;
    private SimpleObjectProperty<TipoPago> tipoPago;
    private SimpleFloatProperty espacio;
    private SimpleObjectProperty<Date> fechaCreacion;
    private SimpleObjectProperty<Cliente> cliente;
    private SimpleObjectProperty<List<Producto>> productos;
    private SimpleObjectProperty<List<TiendaEvento>> listaTiendasEvento;

    public Tienda(Integer idTienda, String nombre, String descripcion, TipoPago tipoPago, Float espacio, Date fechaCreacion, Cliente cliente, List<Producto> productos, List<TiendaEvento> listaTiendasEvento) {
        this.idTienda = new SimpleIntegerProperty(idTienda);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.tipoPago = new SimpleObjectProperty<>(tipoPago);
        this.espacio = new SimpleFloatProperty(espacio);
        this.fechaCreacion = new SimpleObjectProperty<>(fechaCreacion);
        this.cliente = new SimpleObjectProperty<>(cliente);
        this.productos = new SimpleObjectProperty<>(productos);
        this.listaTiendasEvento = new SimpleObjectProperty<>(listaTiendasEvento);
    }

    public Tienda() {
        this.idTienda = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.tipoPago = new SimpleObjectProperty<>();
        this.espacio = new SimpleFloatProperty();
        this.fechaCreacion = new SimpleObjectProperty<>();
        this.cliente = new SimpleObjectProperty<>();
        this.productos = new SimpleObjectProperty<>();
        this.listaTiendasEvento = new SimpleObjectProperty<>();
    }

    public Integer getIdTienda() {
        return idTienda.get();
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda.set(idTienda);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);;
    }

    public TipoPago getTipoPago() {
        return tipoPago.get();
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago.set(tipoPago);
    }

    public float getEspacio() {
        return espacio.get();
    }

    public void setEspacio(float espacio) {
        this.espacio.set(espacio);
    }

    public Date getFechaCreacion() {
        return fechaCreacion.get();
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion.set(fechaCreacion);
    }

    public Cliente getCliente() {
        return cliente.get();
    }

    public void setCliente(Cliente cliente) {
        this.cliente.set(cliente);
    }

    public List<Producto> getProductos() {
        return productos.get();
    }

    public void setProductos(List<Producto> productos) {
        this.productos.set(productos);
    }

    public List<TiendaEvento> getListaTiendasEvento() {
        return listaTiendasEvento.get();
    }

    public void setListaTiendasEvento(List<TiendaEvento> listaTiendasEvento) {
        this.listaTiendasEvento.set(listaTiendasEvento);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idTienda);
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
        final Tienda other = (Tienda) obj;
        if (this.idTienda != other.idTienda) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tienda{" + "idTienda=" + idTienda + ", nombre=" + nombre + ", descripcion=" + descripcion + ", tipoPago=" + tipoPago + ", espacio=" + espacio + ", fechaCreacion=" + fechaCreacion + ", cliente=" + cliente + ", productos=" + productos + ", listaTiendasEvento=" + listaTiendasEvento + '}';
    }

}
