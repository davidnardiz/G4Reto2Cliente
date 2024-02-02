/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 * Factoria del evento
 *
 * @author Iñigo
 */
public class EventoFactoria {

    public static EventoInterface getEventoInterface() {
        return new EventoRestCliente();
    }

}
