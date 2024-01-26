/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Clase que representa a un cliente en MarketMakers. Hereda de la clase base
 * Usuario. Contiene información específica de los clientes, como el tipo de
 * venta y la tienda asociada.
 *
 * @author David
 */
@XmlRootElement(name = "cliente")
public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private SimpleObjectProperty<TipoVenta> tipoVenta;
    private SimpleObjectProperty<Tienda> tienda;
    private SimpleObjectProperty<List<Producto>> productosCreados;

    public Cliente(TipoVenta tipoVenta, Tienda tienda, List<Producto> productosCreados, int idUsuario, String nombre, String password, String correo, Date fechaNacimiento) {
        super(idUsuario, nombre, password, correo, fechaNacimiento);
        this.tipoVenta = new SimpleObjectProperty<>(tipoVenta);
        this.tienda = new SimpleObjectProperty<>(tienda);
        this.productosCreados = new SimpleObjectProperty<>(productosCreados);
    }

    public Cliente() {
        super();
        this.tipoVenta = new SimpleObjectProperty<>();
        this.tienda = new SimpleObjectProperty<>();
        this.productosCreados = new SimpleObjectProperty<>();
    }

    public TipoVenta getTipoVenta() {
        return tipoVenta.get();
    }

    public void setTipoVenta(TipoVenta tipoVenta) {
        this.tipoVenta.set(tipoVenta);
    }

    public Tienda getTienda() {
        return tienda.get();
    }

    public void setTienda(Tienda tienda) {
        this.tienda.set(tienda);
    }

    public List<Producto> getProductosCreados() {
        return productosCreados.get();
    }

    public void setProductosCreados(List<Producto> productosCreados) {
        this.productosCreados.set(productosCreados);
    }

    @Override
    public String toString() {
        return super.toString() + "Cliente{" + "tipoVenta=" + tipoVenta + ", tienda=" + tienda + ", productosCreados=" + productosCreados + '}';
    }

}
