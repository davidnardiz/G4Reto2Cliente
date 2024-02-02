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
 * Jersey REST client generated for REST resource:TiendaFacadeREST
 * [entities.tienda]<br>
 * USAGE:
 * <pre>
 *        TiendaRestCliente client = new TiendaRestCliente();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Gonzalo
 */
public class TiendaRestCliente implements TiendaInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("multimedia.url").getString("BASE_URI");

    public TiendaRestCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.tienda");
    }

    /**
     * Edita una tienda que busca por id
     *
     * @param requestEntity
     * @param id id de la tienda a editar
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
     * Busca una tienda por id
     *
     * @param <T>
     * @param responseType
     * @param id id de la tienda a
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
     * Eceuntra las tiendas de menos de x espacio
     *
     * @param <T>
     * @param responseType
     * @param espacio espacio maximo
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarTiendaMenorEspacio_XML(GenericType<T> responseType, String espacio) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaMenorEspacio/{0}", new Object[]{espacio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarTiendaMenorEspacio_JSON(GenericType<T> responseType, String espacio) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaMenorEspacio/{0}", new Object[]{espacio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra las tiendas que su espacio este entre un minimo y un maximo
     *
     * @param <T>
     * @param responseType
     * @param espacioMin espacio minimo
     * @param espacioMax espacio maximo
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarTiendaEntreEspacio_XML(GenericType<T> responseType, String espacioMin, String espacioMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaEntreEspacio/{0}/{1}", new Object[]{espacioMin, espacioMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarTiendaEntreEspacio_JSON(GenericType<T> responseType, String espacioMin, String espacioMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaEntreEspacio/{0}/{1}", new Object[]{espacioMin, espacioMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Crea una tienda
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
     * Ecuentra tienda en funcion de su tipo de pago
     *
     * @param <T>
     * @param responseType
     * @param tipoPago el tipo de pago de la tienda
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarTiendaTipoPago_XML(GenericType<T> responseType, String tipoPago) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaTipoPago/{0}", new Object[]{tipoPago}));
        System.out.println(resource.getUri());
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarTiendaTipoPago_JSON(GenericType<T> responseType, String tipoPago) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaTipoPago/{0}", new Object[]{tipoPago}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra todas las tiendas
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
     * Ecuentra las tiendas de mas de un epacio
     *
     * @param <T>
     * @param responseType
     * @param espacio espacio maximo por el que busca
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarTiendaMayorEspacio_XML(GenericType<T> responseType, String espacio) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaMayorEspacio/{0}", new Object[]{espacio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarTiendaMayorEspacio_JSON(GenericType<T> responseType, String espacio) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarTiendaMayorEspacio/{0}", new Object[]{espacio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina una tienda
     *
     * @param id id por el que busca la tienda
     * @throws ClientErrorException
     */
    @Override
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }

}
