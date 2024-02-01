/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gonzalo
 */
@XmlRootElement(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty idProducto;
    private SimpleStringProperty nombre;
    private SimpleFloatProperty precio;
    private SimpleIntegerProperty altura;
    private SimpleStringProperty material;
    private SimpleFloatProperty peso;
    private SimpleObjectProperty<Date> fechacreacion;
    private SimpleObjectProperty<Cliente> cliente;
    private SimpleObjectProperty<Tienda> tienda;

    public Producto(Integer idProducto, String nombre, Float precio, Integer altura, String material, Float peso, Date fechacreacion, Cliente cliente, Tienda tienda) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleFloatProperty(precio);
        this.altura = new SimpleIntegerProperty(altura);
        this.material = new SimpleStringProperty(material);
        this.peso = new SimpleFloatProperty(peso);
        this.fechacreacion = new SimpleObjectProperty<>(fechacreacion);
        this.cliente = new SimpleObjectProperty<>(cliente);
        this.tienda = new SimpleObjectProperty<>(tienda);
    }

    public Producto() {
        this.idProducto = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.precio = new SimpleFloatProperty();
        this.altura = new SimpleIntegerProperty();
        this.material = new SimpleStringProperty();
        this.peso = new SimpleFloatProperty();
        this.fechacreacion = new SimpleObjectProperty<>();
        this.cliente = new SimpleObjectProperty<>();
        this.tienda = new SimpleObjectProperty<>();
    }

    public Integer getIdProducto() {
        return idProducto.get();
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto.set(idProducto);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public float getPrecio() {
        return precio.get();
    }

    public void setPrecio(float precio) {
        this.precio.set(precio);
    }

    public int getAltura() {
        return altura.get();
    }

    public void setAltura(int altura) {
        this.altura.set(altura);
    }

    public String getMaterial() {
        return material.get();
    }

    public void setMaterial(String material) {
        this.material.set(material);
    }

    public float getPeso() {
        return peso.get();
    }

    public void setPeso(float peso) {
        this.peso.set(peso);
    }

    public Date getFechacreacion() {
        return fechacreacion.get();
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion.set(fechacreacion);
    }

    public Cliente getCliente() {
        return cliente.get();
    }

    public void setCliente(Cliente cliente) {
        this.cliente.set(cliente);
    }

    public Tienda getTienda() {
        return tienda.get();
    }

    public void setTienda(Tienda tienda) {
        this.tienda.set(tienda);
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", precio=" + precio + ", altura=" + altura + ", material=" + material + ", peso=" + peso + ", fechacreacion=" + fechacreacion + ", cliente=" + cliente + ", tienda=" + tienda + '}';
    }

}
