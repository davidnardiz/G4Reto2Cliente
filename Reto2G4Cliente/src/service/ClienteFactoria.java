/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 * Factoria del Cliente
 *
 * @author David
 */
public class ClienteFactoria {

    public static ClienteInterface getClienteInterface() {
        return new ClienteRestCliente();
    }
}
