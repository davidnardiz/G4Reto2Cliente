/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Iñigo
 */
public interface EventoInterface {

    public <T> T encontrarEventoEntreParticipantes_XML(GenericType<T> responseType, String numParticipantesMin, String numParticipantesMax) throws ClientErrorException;

    public <T> T encontrarEventoEntreParticipantes_JSON(GenericType<T> responseType, String numParticipantesMin, String numParticipantesMax) throws ClientErrorException;

    public <T> T encontrarEventoMayorRecaudado_XML(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException;

    public <T> T encontrarEventoMayorRecaudado_JSON(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException;

    public void edit_XML(Object requestEntity, String id) throws ClientErrorException;

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find_XML(GenericType<T> responseType, String id) throws ClientErrorException;

    public <T> T find_JSON(GenericType<T> responseType, String id) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void create_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T encontrarEventoMenorNumParticipantes_XML(GenericType<T> responseType, String numParticipantes) throws ClientErrorException;

    public <T> T encontrarEventoMenorNumParticipantes_JSON(GenericType<T> responseType, String numParticipantes) throws ClientErrorException;

    public <T> T encontrarEventoMenorRecaudado_XML(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException;

    public <T> T encontrarEventoMenorRecaudado_JSON(GenericType<T> responseType, String totalRecaudado) throws ClientErrorException;

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public <T> T encontrarEventoMayorNumParticipantes_XML(GenericType<T> responseType, String numParticipantes) throws ClientErrorException;

    public <T> T encontrarEventoMayorNumParticipantes_JSON(GenericType<T> responseType, String numParticipantes) throws ClientErrorException;

    public <T> T encontrarEventoEntreRecaudado_XML(GenericType<T> responseType, String totalRecaudadoMin, String totalRecaudadoMax) throws ClientErrorException;

    public <T> T encontrarEventoEntreRecaudado_JSON(GenericType<T> responseType, String totalRecaudadoMin, String totalRecaudadoMax) throws ClientErrorException;
}
