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
 * Jersey REST client generated for REST resource:EventoFacadeREST
 * [entities.evento]<br>
 * USAGE:
 * <pre>
 *        EventoRestCliente client = new EventoRestCliente();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Gonzalo
 */
public class EventoRestCliente implements EventoInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("multimedia.url").getString("BASE_URI");

    public EventoRestCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.evento");
    }

    /**
     * Ecunentra un evento entre un minmo y maximo de participantes
     *
     * @param <T>
     * @param responseType
     * @param numParticipantesMin numero minimo de participantes
     * @param numParticipantesMax numero maximo de participantes
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarEventoEntreParticipantes_XML(GenericType<T> responseType, String numParticipantesMin, String numParticipantesMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoEntreParticipantes/{0}/{1}", new Object[]{numParticipantesMin, numParticipantesMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarEventoEntreParticipantes_JSON(GenericType<T> responseType, String numParticipantesMin, String numParticipantesMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoEntreParticipantes/{0}/{1}", new Object[]{numParticipantesMin, numParticipantesMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra un evento que haya recaudado mas de x Dinero
     *
     * @param <T>
     * @param responseType
     * @param totalRecaudado total de dinero recaudado
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarEventoMayorRecaudado_XML(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMayorRecaudado/{0}", new Object[]{totalRecaudado}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarEventoMayorRecaudado_JSON(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMayorRecaudado/{0}", new Object[]{totalRecaudado}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Edita un Evento que busca segun su id
     *
     * @param requestEntity
     * @param id id del evento a editar
     * @throws ClientErrorException
     */
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Econtrar un Evento mediante su id
     *
     * @param <T>
     * @param responseType
     * @param id id dle evento a encontrar
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
     * Crea un Evento
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
     * Ecuentra un evento con menos de x numeor de participantes
     *
     * @param <T>
     * @param responseType
     * @param numParticipantes numero de participantes
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarEventoMenorNumParticipantes_XML(GenericType<T> responseType, String numParticipantes) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMenorNumParticipantes/{0}", new Object[]{numParticipantes}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarEventoMenorNumParticipantes_JSON(GenericType<T> responseType, String numParticipantes) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMenorNumParticipantes/{0}", new Object[]{numParticipantes}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Eceuntra un evento que haya recaudado menos de x dineor
     *
     * @param <T>
     * @param responseType
     * @param totalRecaudado total recaudado minimo
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarEventoMenorRecaudado_XML(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMenorRecaudado/{0}", new Object[]{totalRecaudado}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarEventoMenorRecaudado_JSON(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMenorRecaudado/{0}", new Object[]{totalRecaudado}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Ecuentra todos los eventos
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
     * Elimina un evento
     *
     * @param id id del evento a eliminar
     * @throws ClientErrorException
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Ecuentra eventos que tengas mas de x numero de participantes
     *
     * @param <T>
     * @param responseType
     * @param numParticipantes numero de participantes
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarEventoMayorNumParticipantes_XML(GenericType<T> responseType, String numParticipantes) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMayorNumParticipantes/{0}", new Object[]{numParticipantes}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarEventoMayorNumParticipantes_JSON(GenericType<T> responseType, String numParticipantes) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoMayorNumParticipantes/{0}", new Object[]{numParticipantes}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca eventos que hayan recaudado entre un minimo y un maximo de dinero
     *
     * @param <T>
     * @param responseType
     * @param totalRecaudadoMin recadado maximo
     * @param totalRecaudadoMax recaudado minimo
     * @return
     * @throws ClientErrorException
     */
    public <T> T encontrarEventoEntreRecaudado_XML(GenericType<T> responseType, String totalRecaudadoMin, String totalRecaudadoMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoEntreRecaudado/{0}/{1}", new Object[]{totalRecaudadoMin, totalRecaudadoMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarEventoEntreRecaudado_JSON(GenericType<T> responseType, String totalRecaudadoMin, String totalRecaudadoMax) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("encontrarEventoEntreRecaudado/{0}/{1}", new Object[]{totalRecaudadoMin, totalRecaudadoMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }

}
