/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 * Factoria del administrador
 *
 * @author David
 */
public class AdministradorFactoria {

    public static AdministradorInterface getAdministradorInterface() {
        return new AdministradorRestCliente();
    }
}
