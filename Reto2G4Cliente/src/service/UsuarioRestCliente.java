/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import exceptions.LogicException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:UsuarioFacadeREST
 * [entities.usuario]<br>
 * USAGE:
 * <pre>
 *        UsuarioRestCliente client = new UsuarioRestCliente();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Gonzalo
 */
public class UsuarioRestCliente implements UsuarioInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto2G4Server/webresources";

    public UsuarioRestCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.usuario");
    }

    @Override
    public <T> T encontrarUsuarioPorNombre_XML(GenericType<T> responseType, String nombre) throws ClientErrorException {
        /*try {*/
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorNombre/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        /*} catch (LogicException ex) {
            throw new LogicException("Ha habido un error!!");
        }*/

    }

    @Override
    public <T> T encontrarUsuarioPorNombre_JSON(GenericType<T> responseType, String nombre) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorNombre/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

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

    @Override
    public <T> T findByCorreo_XML(GenericType<T> responseType, String correo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioCorreo/{0}", new Object[]{correo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findByCorreo_JSON(GenericType<T> responseType, String correo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioCorreo/{0}", new Object[]{correo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public <T> T iniciarSesion_XML(GenericType<T> responseType, String correo, String password) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("iniciarSesion/{0}/{1}", new Object[]{correo, password}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);

    }

    @Override
    public <T> T iniciarSesion_JSON(GenericType<T> responseType, String correo, String password) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("iniciarSesion/{0}/{1}", new Object[]{correo, password}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

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

    @Override
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    @Override
    public <T> T envioEmail(GenericType<T> responseType, String correo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("envioEmail/{0}", new Object[]{correo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }

}
