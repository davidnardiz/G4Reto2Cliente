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
 * Jersey REST client generated for REST resource:AdministradorFacadeREST
 * [entities.administrador]<br>
 * USAGE:
 * <pre>
 *        AdministradorRestCliente client = new AdministradorRestCliente();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Gonzalo
 */
public class AdministradorRestCliente implements AdministradorInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("multimedia.url").getString("BASE_URI");

    public AdministradorRestCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.administrador");
    }

    /**
     * Edita un administrador que busca mediante su id
     *
     * @param requestEntity
     * @param id es el id del Administrador
     * @throws ClientErrorException
     */
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra un Usuario que tenga menos de x numeros de eventos creados
     *
     * @param <T>
     * @param responseType
     * @param numEventos numero de eventos creados
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarUsuarioPorMinNumeroEventos_XML(GenericType<T> responseType, String numEventos) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorMinNumeroEventos/{0}", new Object[]{numEventos}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarUsuarioPorMinNumeroEventos_JSON(GenericType<T> responseType, String numEventos) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorMinNumeroEventos/{0}", new Object[]{numEventos}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca un administrador por su id
     *
     * @param <T>
     * @param responseType
     * @param id del administrador
     * @return
     * @throws ClientErrorException
     */
    public <T> T find_XML(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T find_JSON(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Crea un administrador
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encunetra un usuario que tenga mas de x Eventos creados
     *
     * @param <T>
     * @param responseType
     * @param numEventos numeoro de eventos por el que buscar
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarUsuarioPorMaxNumeroEventos_XML(GenericType<T> responseType, String numEventos) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorMaxNumeroEventos/{0}", new Object[]{numEventos}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarUsuarioPorMaxNumeroEventos_JSON(GenericType<T> responseType, String numEventos) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorMaxNumeroEventos/{0}", new Object[]{numEventos}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra todos los Administradores y los devuelve en un array
     *
     * @param <T>
     * @param responseType
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina un Administrador por id
     *
     * @param id el id del administrador
     * @throws ClientErrorException
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }

}
