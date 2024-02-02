/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Gonzalo
 */
public class TiendaEventoId implements Serializable {

    //Variables.
    private SimpleIntegerProperty idTienda;
    private SimpleIntegerProperty idEvento;

    //Constructores.
    public TiendaEventoId(Integer idTienda, Integer idEvento) {
        this.idTienda = new SimpleIntegerProperty(idTienda);
        this.idEvento = new SimpleIntegerProperty(idEvento);
    }

    public TiendaEventoId() {
        this.idTienda = new SimpleIntegerProperty();
        this.idEvento = new SimpleIntegerProperty();
    }

    //Getters y setters
    public int getIdTienda() {
        return this.idTienda.get();
    }

    public void setIdTienda(int idTienda) {
        this.idTienda.set(idTienda);
    }

    public int getIdEvento() {
        return this.idEvento.get();
    }

    public void setIdEvento(int idEvento) {
        this.idEvento.set(idEvento);
    }

    //To string para mostrar la información.
    @Override
    public String toString() {
        return "TiendaEventoId{" + "idTienda=" + idTienda + ", idEvento=" + idEvento + '}';
    }
}
