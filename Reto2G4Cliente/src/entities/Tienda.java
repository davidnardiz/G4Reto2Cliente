/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author David
 */
public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idTienda;
    private String nombre;
    private String descripcion;
    private TipoPago tipoPago;
    private float espacio;
    private Date fechaCreacion;
    private Cliente cliente;
    private List<Producto> productos;
    private List<TiendaEvento> listaTiendasEvento;

    public Integer getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public float getEspacio() {
        return espacio;
    }

    public void setEspacio(float espacio) {
        this.espacio = espacio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<TiendaEvento> getListaTiendasEvento() {
        return listaTiendasEvento;
    }

    public void setListaTiendasEvento(List<TiendaEvento> listaTiendasEvento) {
        this.listaTiendasEvento = listaTiendasEvento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idTienda;
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

}
