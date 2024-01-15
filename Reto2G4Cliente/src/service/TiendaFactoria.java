/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author David
 */
public class TiendaFactoria {

    public static TiendaInterface getTiendaInterface() {
        return new TiendaRestCliente();
    }
}
