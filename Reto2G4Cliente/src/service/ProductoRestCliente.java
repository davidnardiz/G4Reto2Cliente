/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ProductoFacadeREST
 * [entities.producto]<br>
 * USAGE:
 * <pre>
 *        ProductoRestCliente client = new ProductoRestCliente();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Gonzalo
 */
public class ProductoRestCliente implements ProductoInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("multimedia.url").getString("BASE_URI");

    public ProductoRestCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.producto");
    }

    /**
     * Encuentra un producto entre un precio minimo y maximo
     *
     * @param <T>
     * @param responseType
     * @param precio1 precio minimo
     * @param precio2 precio maximo
     * @param idTienda id de la tienda del producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findEntrePrecioProducto_XML(GenericType<T> responseType, String precio1, String precio2, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoEntrePrecio/{0}/{1}/{2}", new Object[]{precio1, precio2, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findEntrePrecioProducto_JSON(GenericType<T> responseType, String precio1, String precio2, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoEntrePrecio/{0}/{1}/{2}", new Object[]{precio1, precio2, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Edita un Producto
     *
     * @param requestEntity
     * @param id id del producto
     * @throws ClientErrorException
     */
    @Override
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Ecuentra un producto con un precio mayor a x
     *
     * @param <T>
     * @param responseType
     * @param precio precio maximo
     * @param idTienda id tienda del producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarProductoMayorPrecio_XML(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMayorPrecio/{0}/{1}", new Object[]{precio, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarProductoMayorPrecio_JSON(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMayorPrecio/{0}/{1}", new Object[]{precio, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra os productos de una tienda
     *
     * @param <T>
     * @param responseType
     * @param idTienda id de la tienda de los productos
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarProductoTodosTienda_XML(GenericType<T> responseType, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoTodosTienda/{0}", new Object[]{idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarProductoTodosTienda_JSON(GenericType<T> responseType, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoTodosTienda/{0}", new Object[]{idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca productos entre un peso minimo y maximo
     *
     * @param <T>
     * @param responseType
     * @param peso1 peso minimo
     * @param peso2 peso maximo
     * @param idTienda id de la tienda de los productos
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findEntrePesosProducto_XML(GenericType<T> responseType, String peso1, String peso2, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoEntrePesos/{0}/{1}/{2}", new Object[]{peso1, peso2, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findEntrePesosProducto_JSON(GenericType<T> responseType, String peso1, String peso2, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoEntrePesos/{0}/{1}/{2}", new Object[]{peso1, peso2, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca todos los productos de menos de x altura
     *
     * @param <T>
     * @param responseType
     * @param altura altura minima
     * @param idTienda id de la tienda de producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMinAlturaProducto_XML(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMenorAltura/{0}/{1}", new Object[]{altura, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findMinAlturaProducto_JSON(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMenorAltura/{0}/{1}", new Object[]{altura, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra productos de menos de x peso
     *
     * @param <T>
     * @param responseType
     * @param peso peso minimo
     * @param idTienda id de la tienda del producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMinPesoProducto_XML(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMenorPeso/{0}/{1}", new Object[]{peso, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findMinPesoProducto_JSON(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMenorPeso/{0}/{1}", new Object[]{peso, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra productos entre una altura maxima y una minima
     *
     * @param <T>
     * @param responseType
     * @param altura1 altura minima
     * @param altura2 altura maxima
     * @param idTienda id de la tienda del producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findEntreAlturaProducto_XML(GenericType<T> responseType, String altura1, String altura2, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoEntreAltura/{0}/{1}/{2}", new Object[]{altura1, altura2, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findEntreAlturaProducto_JSON(GenericType<T> responseType, String altura1, String altura2, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoEntreAltura/{0}/{1}/{2}", new Object[]{altura1, altura2, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentr todos los productos
     *
     * @param <T>
     * @param responseType
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina un producto mediante su id
     *
     * @param id id del producto
     * @throws ClientErrorException
     */
    @Override
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Busca productos con menos de x peso maximo
     *
     * @param <T>
     * @param responseType
     * @param peso el peso maximo
     * @param idTienda id de la tienda del producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMaxPesoProducto_XML(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMayorPeso/{0}/{1}", new Object[]{peso, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findMaxPesoProducto_JSON(GenericType<T> responseType, String peso, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMayorPeso/{0}/{1}", new Object[]{peso, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra productos de mas de un precio minimo
     *
     * @param <T>
     * @param responseType
     * @param precio el precio minimo
     * @param idTienda id de la tienda del producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarProductoMenorPrecio_XML(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMenorPrecio/{0}/{1}", new Object[]{precio, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarProductoMenorPrecio_JSON(GenericType<T> responseType, String precio, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMenorPrecio/{0}/{1}", new Object[]{precio, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra un producto segun su id
     *
     * @param <T>
     * @param responseType
     * @param id id por el que busca
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T find_XML(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T find_JSON(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Crea un producto
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    @Override
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Ecuentra un producto de maxmimo x altura y por el id de su tienda
     *
     * @param <T>
     * @param responseType
     * @param altura altura por la que busca
     * @param idTienda id de la tienda en la que esta el producto
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMaxAlturaProducto_XML(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMayorAltura/{0}/{1}", new Object[]{altura, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findMaxAlturaProducto_JSON(GenericType<T> responseType, String altura, String idTienda) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarProductoMayorAltura/{0}/{1}", new Object[]{altura, idTienda}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }

}
