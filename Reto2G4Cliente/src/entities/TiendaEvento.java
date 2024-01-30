/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Gonzalo
 */
public class TiendaEvento {

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty idTienda;
    private SimpleIntegerProperty idEvento;
    private SimpleObjectProperty<Date> fechaInscripcion;

    public TiendaEvento(Integer idTienda, Integer idEvento, Date fechaCreacion) {
        this.idTienda = new SimpleIntegerProperty(idTienda);
        this.idEvento = new SimpleIntegerProperty(idEvento);
        this.fechaInscripcion = new SimpleObjectProperty<>(fechaCreacion);

    }

    public TiendaEvento() {
        this.idTienda = new SimpleIntegerProperty();
        this.idEvento = new SimpleIntegerProperty();
        this.fechaInscripcion = new SimpleObjectProperty<>();
    }

    public Integer getIdTienda() {
        return idTienda.get();
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda.set(idTienda);
    }

    public Integer getIdEvento() {
        return idEvento.get();
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento.set(idEvento);
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion.get();
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion.set(fechaInscripcion);
    }

}
