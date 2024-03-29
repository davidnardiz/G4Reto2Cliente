/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ResourceBundle;
import javax.annotation.Resource;
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
    private static final String BASE_URI = ResourceBundle.getBundle("multimedia.url").getString("BASE_URI");

    public UsuarioRestCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.usuario");
    }

    /**
     * Ecuentra un usuario por su nombre.
     *
     * @param <T>
     * @param responseType
     * @param nombre nombre por el que se va a buscar.
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T encontrarUsuarioPorNombre_XML(GenericType<T> responseType, String nombre) throws ClientErrorException {

        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorNombre/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);

    }

    @Override
    public <T> T encontrarUsuarioPorNombre_JSON(GenericType<T> responseType, String nombre) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarUsuarioPorNombre/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Edita un usuario que busca por id.
     *
     * @param requestEntity
     * @param id id del usuario que se va a editar.
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
     * Ecuentra un Usuario por el id.
     *
     * @param <T>
     * @param responseType
     * @param id id del usuario que estamos buscando.
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
     * Ecuentra un usuario por us correo
     *
     * @param <T>
     * @param responseType
     * @param correo correo que se le pasa para econtrar al usuario
     * @return
     * @throws ClientErrorException
     */
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

    /**
     * Crea un usuario
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
     * Busca un usuario por su correo y su contraseña y si coincide devuelve el
     * usuario para iniciar sesion en la aplicacion
     *
     * @param <T>
     * @param responseType
     * @param correo correo del usuario que quiere inicia sesion
     * @param password contraseña dle usuario que quiere iniciar sesion
     * @return
     * @throws ClientErrorException
     */
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

    /**
     * Ecuentra todos los usuarios
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
     * Elimina un usuario mediante su id
     *
     * @param id id del usuario a eliminar
     * @throws ClientErrorException
     */
    @Override
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Busca si existe el correo enviado para mandarle una contraseña nueva
     * generada automaticamente
     *
     * @param <T>
     * @param responseType
     * @param correo correo para que envie la contraseña nueva
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T olvidarContrasenia(GenericType<T> responseType, String correo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("olvidarPasswd/{0}", new Object[]{correo}));
        return resource.request().get(responseType);
    }

    @Override
    public <T> T cambiarContrasenia(GenericType<T> responseType, String correo, String passwd) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("cambiarPasswd/{0}/{1}", new Object[]{correo, passwd}));
        return resource.request().get(responseType);
    }

    public void close() {
        client.close();
    }

}
