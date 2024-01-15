/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Clase que representa a un cliente en MarketMakers. Hereda de la clase base
 * Usuario. Contiene información específica de los clientes, como el tipo de
 * venta y la tienda asociada.
 *
 * @author David
 */
public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private TipoVenta tipoVenta;
    private Tienda tienda;
    private List<Producto> productosCreados;

    public TipoVenta getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(TipoVenta tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public List<Producto> getProductosCreados() {
        return productosCreados;
    }

    public void setProductosCreados(List<Producto> productosCreados) {
        this.productosCreados = productosCreados;
    }

}
