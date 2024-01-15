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
 * @author David
 */
public interface TiendaInterface {

    public void edit_XML(Object requestEntity, String id) throws ClientErrorException;

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find_XML(GenericType<T> responseType, String id) throws ClientErrorException;

    public <T> T find_JSON(GenericType<T> responseType, String id) throws ClientErrorException;

    public <T> T encontrarTiendaMenorEspacio_XML(GenericType<T> responseType, String espacio) throws ClientErrorException;

    public <T> T encontrarTiendaMenorEspacio_JSON(GenericType<T> responseType, String espacio) throws ClientErrorException;

    public <T> T encontrarTiendaEntreEspacio_XML(GenericType<T> responseType, String espacioMin, String espacioMax) throws ClientErrorException;

    public <T> T encontrarTiendaEntreEspacio_JSON(GenericType<T> responseType, String espacioMin, String espacioMax) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void create_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T encontrarTiendaTipoPago_XML(GenericType<T> responseType, String tipoPago) throws ClientErrorException;

    public <T> T encontrarTiendaTipoPago_JSON(GenericType<T> responseType, String tipoPago) throws ClientErrorException;

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException;

    public <T> T encontrarTiendaMayorEspacio_XML(GenericType<T> responseType, String espacio) throws ClientErrorException;

    public <T> T encontrarTiendaMayorEspacio_JSON(GenericType<T> responseType, String espacio) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;
}
