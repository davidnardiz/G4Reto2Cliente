/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Producto;
import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Gonzalo
 */
public interface ProductoInterface {
    
    public <T> T findEntrePrecioProducto_XML(GenericType<T> responseType, String precio1, String precio2, String idTienda) throws ClientErrorException;

    public <T> T findEntrePrecioProducto_JSON(GenericType<T> responseType, String precio1, String precio2, String idTienda) throws ClientErrorException;

    public void edit_XML(Object requestEntity, String id) throws ClientErrorException;

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException;

    public <T> T encontrarProductoMayorPrecio_XML(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException;

    public <T> T encontrarProductoMayorPrecio_JSON(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException;

    public <T> T encontrarProductoTodosTienda_XML(GenericType<T> responseType, String idTienda) throws ClientErrorException;

    public <T> T encontrarProductoTodosTienda_JSON(GenericType<T> responseType, String idTienda) throws ClientErrorException;

    public <T> T findEntrePesosProducto_XML(GenericType<T> responseType, String peso1, String peso2, String idTienda) throws ClientErrorException;

    public <T> T findEntrePesosProducto_JSON(GenericType<T> responseType, String peso1, String peso2, String idTienda) throws ClientErrorException;

    public <T> T findMinAlturaProducto_XML(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException;

    public <T> T findMinAlturaProducto_JSON(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException;

    public <T> T findMinPesoProducto_XML(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException;

    public <T> T findMinPesoProducto_JSON(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException;

    public <T> T findEntreAlturaProducto_XML(GenericType<T> responseType, String altura1, String altura2, String idTienda) throws ClientErrorException;

    public <T> T findEntreAlturaProducto_JSON(GenericType<T> responseType, String altura1, String altura2, String idTienda) throws ClientErrorException;

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public <T> T findMaxPesoProducto_XML(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException;

    public <T> T findMaxPesoProducto_JSON(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException;

    public <T> T encontrarProductoMenorPrecio_XML(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException;

    public <T> T encontrarProductoMenorPrecio_JSON(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException;

    public <T> T find_XML(GenericType<T> responseType, String id) throws ClientErrorException;

    public <T> T find_JSON(GenericType<T> responseType, String id) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void create_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T findMaxAlturaProducto_XML(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException;

    public <T> T findMaxAlturaProducto_JSON(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException;
}
